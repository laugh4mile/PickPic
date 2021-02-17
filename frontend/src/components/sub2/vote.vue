<template>
  <div>
    <login-modal :btnView="true" :dialog="loginPlz" @loginSuccess="loginPlz=false"/>
    <v-radio-group>
      <v-row>
        <label v-for="(item, index) in imgUrl" :key="index">
          <div class="image-container" :class="{ redd: index == selected }">
            <img :src="item.modPicName" @click="selectImg(item, index)" />
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
        alert('로그인이 필요한 서비스입니다.');
        this.loginPlz = true;
      }else{

        if (this.selected == -1) {
          alert('사진을 선택해 주세요!');
        return;
      }
      alert(this.selected + 1 + '번째 사진에 투표하였습니다.');
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
  width: 200px;
  height: 200px;
  margin-right: 10px;
}
img {
  width: 100%;
  height: 100%;
}

/* .v-radio {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
} */

/* IMAGE STYLES */
img + div.v-radio {
  cursor: pointer;
}

/* CHECKED STYLES */
.redd {
  /* outline: 2px solid #f00; */
  left: 50%;
  transform: translate(-50%, -50%) scale(1);
  transition: all 0.3s ease 0s;
  transform: rotateY(0) scale(1);
  box-shadow: 0 0 0 5px rgba(0, 0, 0, 0.5);
  background: #fff;
  color: #ff4f4f;
}
.box2 {
  position: relative;
}
.box2 img {
  width: 100%;
  height: auto;
}
.box2 .box-content {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: 2;
  transform: translate(-50%, -50%);
}
.box2 .box-content:after,
.box2 .box-content:before,
.box2:after,
.box2:before {
  content: '';
  width: 80%;
  height: 80%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(45deg);
  transition: all 0.5s ease 0s;
}
.box2:before {
  background: linear-gradient(45deg, rgba(0, 0, 0, 0.2) 49%, transparent 50%);
  left: -100%;
}
.box2:after {
  background: linear-gradient(45deg, transparent 49%, rgba(0, 0, 0, 0.2) 50%);
  left: 160%;
}
.box2 .box-content:after,
.box2 .box-content:before {
  width: 65%;
  height: 65%;
  background: linear-gradient(45deg, rgba(0, 0, 0, 0.3) 49%, transparent 50%);
  left: -100%;
  transition-delay: 0.1s;
}
.box1 .box-content:after {
  background: linear-gradient(45deg, transparent 49%, rgba(0, 0, 0, 0.3) 50%);
  left: 160%;
}
.box2:hover .box-content:after,
.box2:hover .box-content:before,
.box2:hover:after,
.box2:hover:before {
  left: 50%;
}
.box2 .inner-content {
  width: 50%;
  height: 50%;
  color: #fff;
  padding: 40px 0;
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: 2;
  transform: translate(-50%, -50%) scale(0);
  transition: all 0.3s ease 0.2s;
}
.box2 .inner-content:after {
  width: 100%;
  height: 100%;
  background: rgba(255, 79, 79, 0.8);
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: -1;
  transform: translate(-50%, -50%) rotate(45deg);
}
.box2:hover .inner-content {
  transform: translate(-50%, -50%) scale(1);
  transition: all 0.3s ease 0s;
}
.box2 .title {
  font-size: 18px;
  font-weight: 700;
  text-transform: uppercase;
  margin-bottom: 5px;
}
.box2 .post {
  display: block;
  font-size: 14px;
  text-transform: capitalize;
  margin-bottom: 7px;
}
.box10 .title,
.box11 .title,
.box4 .title,
.box5 .title,
.box6 .box-content,
.box7 .title {
  text-transform: uppercase;
}
.box2 .icon {
  padding: 0;
  margin: 0;
  list-style: none;
  transform: rotateY(360deg) scale(0);
  transition: all 0.3s ease 0s;
}
.box2:hover .icon {
  transform: rotateY(0) scale(1);
}
.box2 .icon li {
  display: inline-block;
  margin: 0 3px;
}
.box2 .icon li a {
  display: block;
  width: 35px;
  height: 35px;
  line-height: 35px;
  background: #fff;
  font-size: 16px;
  color: #505050;
  transition: all 0.3s ease 0s;
}
.box2 .icon li a:hover {
  box-shadow: 0 0 0 5px rgba(0, 0, 0, 0.5);
  background: #fff;
  color: #ff4f4f;
}
@media only screen and (max-width: 990px) {
  .box2 {
    margin-bottom: 30px;
  }
}
@media only screen and (max-width: 320px) {
  .box2 .inner-content {
    padding: 25px 0;
  }
  .box2 .title {
    font-size: 16px;
  }
}
</style>
