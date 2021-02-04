<template>
  <div class="container">
    <div>
      <v-row>
        <v-col>
          <h2>{{ this.board.postInfo.title }}</h2>
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
        </v-col>
      </v-row>
    </div>
    <hr />
    <div>{{ this.board.postInfo.content }}</div>
    <vote :no="board.postInfo.postNo" />
    <div>
      <v-btn
        :disabled="this.board.postInfo.email != $store.getters.getUserEmail"
        @click="modifyForm"
        >수정</v-btn
      >
      <v-dialog v-model="dialog1" width="230">
        <template v-slot:activator="{ on, attrs }" @click="dialog1 = true">
          <v-btn :disabled="board.postInfo.email != $store.getters.getUserEmail" variant="primary" v-bind="attrs" v-on="on">
            게시글삭제
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
              삭제하시겠습니까
            </v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="red" @click="deleteForm">
              삭제
            </v-btn>
            <v-btn color="primary" @click="dialog1=false">
              취소
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <!-- <v-menu transition="offset-y" bottom >
        <template v-slot:activator="{ on, attrs }">
          <v-btn :disabled="board.postInfo.email != $store.getters.getUserEmail" v-bind="attrs" v-on="on">
            삭제
          </v-btn>
        </template>
        <v-list>
          <v-list-item v-for="(item, i) in items" :key="i" @click="menuclick($event)">
            {{ item }}
          </v-list-item>
        </v-list>
      </v-menu> -->
      <!-- <v-btn
        :disabled="this.board.postInfo.email != $store.getters.getUserEmail"
        @click="deleteForm"
        >삭제</v-btn
      > -->
      <v-btn @click="toBoard">홈으로</v-btn>
    </div>
    <comment />
  </div>
</template>
<script>
import Vote from './vote.vue';
import Comment from './Comment.vue';
import axios from 'axios';
import { mapGetters } from 'vuex';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      board: {},
      no: '',
      heartIcon: '',
      like: false,
      dialog1: false,
      items: ["취소", "삭제"],
      
    };
  },
  computed: {
    ...mapGetters(['getAccessToken', 'getUserEmail', 'getUserName', 'getRole']),
  },
  components: {
    Vote,
    Comment,
  },
  created() {
    axios
      .get(`${SERVER_URL}/post`, {
        params: {
          email: this.$store.getters.getUserEmail,
          postNo: this.$route.params.no,
        },
      })
      .then((response) => {
        console.log(response.data);
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
        console.log(error);
      });
  },
  methods: {
    menuclick(event, cmt) {
      if (event.target.innerText == "삭제") {
        this.deleteForm()
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
        alert('로그인이 필요한 서비스입니다.');
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
                console.log(response.data);
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
                console.log(error);
              });
          })
          .catch((error) => {
            alert(error);
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
          console.log(error);
        });
    },
  },
};
</script>
