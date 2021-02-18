<template>
  <div>
    <login-modal :btnView="true" :dialog="loginPlz" @loginSuccess="loginPlz=false"/>
    <v-radio-group>
      <v-row>
        <label v-for="(item, index) in imgUrl" :key="index">
          <div class="image-container">
            <img :src="item.modPicName" :class="{ redd: index == selected }" @click="selectImg(item, index)" />
          </div>
          <!-- <v-radio @click="selectImg(item, index)" style="margin-left:94px" /> -->
        </label>
      </v-row>
    </v-radio-group>
    <v-btn color="secondary" outlined @click="vote">투표하기</v-btn>
    <hr />
    <Poll v-bind="options" />
  </div>
</template>
<script>
import axios from 'axios';
import Poll from '@/components/sub3/VoteResult.vue';
import { mapGetters } from 'vuex';
import LoginModal from "../core/LoginModal.vue";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      imgUrl: [],
      item: [],
      selected: -1,
      loginPlz: false,
      options: {
        question: "What's your favourite?",
        answers: [
          // { value: 1, text: "Vue", votes: 53 },
        ],
        showResults: false,
      },
    };
  },
  computed: {
    ...mapGetters(['getAccessToken', 'getUserEmail', 'getUserName', 'getRole']),
  },
  props: {
    no: {
      type: Number,
    },
  },
  components: {
    Poll,
    LoginModal,
  },
  created() {
    const params = new URLSearchParams();
    params.append('email', this.$store.getters.getUserEmail);
    params.append('postNo', this.no);

    axios
      .get(`${SERVER_URL}/post`, { params })
      .then((response) => {
        if (response.data.fileList.length == 0) {
          this.$emit('no-pic');
        }
        for (var i = 0; i < response.data.fileList.length; i++) {
          // this.imgUrl.push(`${SERVER_URL}/post/imgs/download?fileName=` + response.data.fileList[i].modPicName + '&postNo=' + this.no);
          let file = response.data.fileList[i];
          this.imgUrl.push(file);
          if (
            response.data.votedPicNo !== undefined &&
            response.data.votedPicNo == file.picNo
          ) {
            this.options.answers.push({
              value: i + 1,
              text: i + 1 + ' 번째',
              votes: file.voteCnt,
              selected: true,
            });
          } else {
            this.options.answers.push({
              value: i + 1,
              text: i + 1 + ' 번째',
              votes: file.voteCnt,
            });
          }
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
    vote() {
      if (!this.getUserEmail) {
        this.$alert('로그인이 필요한 서비스입니다.','','warning');
        this.loginPlz = true;
      }else{

        if (this.selected == -1) {
          this.$alert('사진을 선택해 주세요!','','warning');
        return;
      }
      this.$alert(this.selected + 1 + '번째 사진에 투표하였습니다.','','success');
      // this.selected = -1
      const params = new URLSearchParams();
      params.append('email', this.$store.getters.getUserEmail);
      params.append('postNo', this.item.postNo);
      params.append('picNo', this.item.picNo);
      axios
        .post(`${SERVER_URL}/post/vote`, params)
        .then((response) => {
          this.options.showResults = true;
          this.options.finalResults = true;
          this.options.answers = [];
          for (var i = 0; i < response.data.fileList.length; i++) {
            let file = response.data.fileList[i];
            if (this.selected == i) {
              this.options.answers.push({
                value: i + 1,
                text: i + 1 + ' 번째',
                votes: file.voteCnt,
                selected: true,
              });
            } else {
              this.options.answers.push({
                value: i + 1,
                text: i + 1 + ' 번째',
                votes: file.voteCnt,
              });
            }
          }
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
      }
    },
    selectImg(item, idx) {
      this.item = item;
      this.selected = idx;
    },
  },
};
</script>
<style scoped>
.image-container {
  overflow: hidden;
  display: relative;
  align-items: center;
  justify-content: center;
  width: 300px;
  height: 300px;
  margin-right: 10px;
}
.image-container:hover {
  transform:scale(1.2);
}
img {
  width: 250px;
  height: 250px;
  object-fit: scale-down;
  cursor: pointer;
}

/* CHECKED STYLES */
.redd {
  /* outline: 2px solid #f00; */
  left: 50%;
  transform: translate(-50%, -50%) scale(1);
  transition: all 0.3s ease 0s;
  transform: rotateY(0) scale(1);
  /* box-shadow: 0 0 0 5px rgba(0, 0, 0, 0.5); */
  border-bottom: 5px solid rgba(0, 0, 0, 0.5);
  background: #fff;
  color: #ff4f4f;
}
</style>
