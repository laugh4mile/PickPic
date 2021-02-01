<template>
  <div class="container">
    <div>
      <v-row>
        <v-col>
          <h3>{{ this.board.postInfo.title }}</h3>
          <span style="font-size:12px">{{ board.postInfo.name }}</span>
          <span style="font-size:12px">
            좋아요 {{ this.board.postInfo.likeCnt }}</span
          >
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
      <v-btn
        :disabled="this.board.postInfo.email != $store.getters.getUserEmail"
        @click="deleteForm"
        >삭제</v-btn
      >
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

export default {
  data() {
    return {
      board: {},
      no: '',
      heartIcon: '',
      like: false,
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
      .get('http://localhost:3000/sub/post', {
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
          .put('http://localhost:3000/sub/post/like', params)
          .then((response) => {
            axios
              .get('http://localhost:3000/sub/post', {
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
        .delete('http://localhost:3000/sub/post', {
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
