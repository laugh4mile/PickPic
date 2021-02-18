<template>
  <div class="container font-ELAND_Choice_B" v-if="!loading">
    <div id="wr">
    <login-modal v-model="loginPlz" :btnView="true" :dialog="loginPlz" @loginSuccess="loginPlz=false"/>
      <v-row>
        <v-col>
          <h2>{{ this.board.postInfo.title }}</h2>
          <br />
          <span style="font-size:15px">작성자 {{ board.postInfo.name }}</span>
          <br />
          <span style="font-size:12px">{{
            this.board.postInfo.uploadDate
          }}</span>
        </v-col>
        <v-col>
          <div class="d-flex justify-content-end">
            <i
              class="fas fa-heart fa-2x"
              style="color:red"
              v-show="this.like"
              @click="heartClick"
            ></i>
            <i
              class="far fa-heart fa-2x"
              style="color:red"
              v-show="!this.like"
              @click="heartClick"
            ></i>
          </div>
          <span class="d-flex justify-content-end" style="font-size:15px">
            좋아요 {{ this.board.postInfo.likeCnt }}
          </span>
          <div v-if="!((board.postInfo.email != $store.getters.getUserEmail) && ($store.getters.getRole != 'admin'))" class="d-flex justify-content-end mt-4">
            <v-btn
              :disabled="
                this.board.postInfo.email != $store.getters.getUserEmail
              "
              color="secondary"
              outlined
              @click="modifyForm"
              class="btn-outline-info mr-2 rounded-pill"
              :contents="this.cont"
              >수정</v-btn
            >
            <v-dialog v-model="dialog1" width="230">
              <template
                v-slot:activator="{ on, attrs }"
                @click="dialog1 = true"
              >
                <v-btn
                  :disabled="
                    (board.postInfo.email != $store.getters.getUserEmail) && ($store.getters.getRole != 'admin')
                  "

                  color="secondary"
                  outlined
                  class="btn-outline-info mr-2 rounded-pill"
                  v-bind="attrs"
                  v-on="on"
                >
                  삭제
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span style="">삭제하시겠습니까?</span>
                </v-card-title>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="red"
                    outlined
                    @click="deleteForm"
                    class="btn-outline-warn mr-2 rounded-pill"
                  >
                    삭제
                  </v-btn>
                  <v-btn
                    color="primary"
                    outlined
                    @click="dialog1 = false"
                    class="btn-outline mr-2 rounded-pill"
                  >
                    취소
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </div>
        </v-col>
      </v-row>
    </div>
    <hr />
    <div class="contents" v-html="cont"></div>
    <Vote v-if="isPic" :no="board.postInfo.postNo" @no-pic="isPic = false" />
    <div>
      <v-btn
        color="secondary"
        outlined
        @click="toBoard"
        class="btn-outline mr-2 rounded-pill mb-5"
        >게시판</v-btn
      >
    </div>
    <comment />
  </div>
</template>
<script>
import Vote from './vote.vue';
import Comment from './Comment.vue';
import axios from 'axios';
import { mapGetters } from 'vuex';
import LoginModal from "../core/LoginModal.vue";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      board: {},
      no: '',
      heartIcon: '',
      like: false,
      dialog1: false,
      items: ['취소', '삭제'],
      cont: '',
      loading: false,
      isPic: true,
      loginPlz: false,
    };
  },
  computed: {
    ...mapGetters(['getAccessToken', 'getUserEmail', 'getUserName', 'getRole']),
  },
  components: {
    Vote,
    Comment,
    LoginModal,
  },
  created() {
    this.loading = true;
    this.loginPlz = false;

    axios
      .get(`${SERVER_URL}/post`, {
        params: {
          email: this.$store.getters.getUserEmail,
          postNo: this.$route.params.no,
        },
      })
      .then((response) => {
        this.board = response.data;
        this.cont = this.board.postInfo.content;
        this.loading = false;
        if (this.board.likeCheck == 'N') {
          this.heartIcon = require('@/assets/blank heart.png');
          this.like = false;
        } else {
          this.heartIcon = require('@/assets/heart.jpg');
          this.like = true;
        }
      })
      .catch((error) => {
        this.$router.push({
          path: '/Error',
          query: { status: error.response.status },
        });
      });
  },
  methods: {
    menuclick(event, cmt) {
      if (event.target.innerText == '삭제') {
        this.deleteForm();
      }
    },
    toBoard() {
      this.$router.push('/board');
    },
    modifyForm() {
      this.$router.push({
        path: '/board/update',
        query: {
          board: this.board,
        },
      });
    },
    heartClick() {
      if (!this.getUserEmail) {
        this.$alert('로그인이 필요한 서비스입니다.','','warning');
        this.loginPlz = true;
      } else {
        const params = new URLSearchParams();
        params.append('email', this.$store.getters.getUserEmail);
        params.append('postNo', this.board.postInfo.postNo);

        axios
          .put(`${SERVER_URL}/post/like`, params)
          .then((response) => {
            axios
              .get(`${SERVER_URL}/post`, {
                params: {
                  email: this.$store.getters.getUserEmail,
                  postNo: this.$route.params.no,
                },
              })
              .then((response) => {
                this.board = response.data;
                if (this.board.likeCheck == 'N') {
                  this.heartIcon = require('@/assets/blank heart.png');
                  this.like = false;
                } else {
                  this.heartIcon = require('@/assets/heart.jpg');
                  this.like = true;
                }
              })
              .catch((error) => {
                this.$router.push({
                  path: '/Error',
                  query: { status: error.response.status },
                });
              });
          })
          .catch((error) => {
            this.$router.push({
              path: '/Error',
              query: { status: error.response.status },
            });
          });
      }
    },
    deleteForm() {
      axios
        .delete(`${SERVER_URL}/post`, {
          params: {
            postNo: this.$route.params.no,
          },
        })
        .then((response) => {
          this.$router.push('/board');
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
  },
};
</script>
