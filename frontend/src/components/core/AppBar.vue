<template>
  <v-app-bar app flat color="white">
    <v-app-bar-nav-icon class="hidden-md-and-up" @click="toggleDrawer" />
    <v-container class="mx-auto py-0">
      <v-row align="center">
        <v-img
          :src="require('@/assets/logo.png')"
          class="mr-5"
          contain
          height="100%"
          width="80"
          max-width="110"
          @click="toHome"
          style="cursor:pointer"
        />

        <v-btn
          v-for="(link, i) in links"
          :key="i"
          class="hidden-sm-and-down "
          text
          @click="onClick($event, link)"
        >
          <span class="font-yg-jalnan mx-2">{{ link.text }}</span>
        </v-btn>
        <template v-if="!getAccessToken">
          <v-spacer />

          <v-btn
            text
            @click="registForm"
            class="btn btn-outline-secondary rounded-pill font-yg-jalnan hidden-xs"
            style="border-width : 3px; font-size : 12px;"
          >
            회원가입
          </v-btn>
          <login-modal/>
        </template>
        <template v-else>
          <v-spacer />
          <span class="font-yg-jalnan hidden-xs">
            <span style="color: green">{{ getUserName }}</span
            >님 환영합니다.</span
          >
          <v-btn
            v-if="getRole == 'admin'"
            text
            @click="adminPageForm"
            class="btn btn-outline-secondary rounded-pill ml-2 font-yg-jalnan"
            style="border-width : 3px;"
            >관리자페이지</v-btn
          >

          <v-btn
            text
            @click="myPageForm"
            class="btn btn-outline-secondary rounded-pill ma-6 font-yg-jalnan col-xs-2"
            style="border-width : 3px; font-size : 12 px;"
            >마이페이지</v-btn
          >
          <v-btn
            text
            @click="logout"
            class="btn btn-outline-secondary rounded-pill font-yg-jalnan col-xs-2"
            style="border-width : 3px; font-size : 12 px;"
            >로그아웃</v-btn
          >
        </template>
      </v-row>
    </v-container>
  </v-app-bar>
</template>

<script>
// Utilities
import { mapGetters, mapMutations } from 'vuex';
import axios from 'axios';
import LoginModal from './LoginModal.vue';
export default {
  name: 'CoreAppBar',

  computed: {
    ...mapGetters([
      'links',
      'getAccessToken',
      'getUserEmail',
      'getUserName',
      'getRole',
    ]),
  },
  components:{
    LoginModal,
  },
  methods: {
    validEmail(email) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
    checkDuplicate() {
      //이메일 유효성을 검사한다.
      if (!this.validEmail(this.user.email)) {
        //유효성이 틀리다면 data 값을 false로 한다.
        this.availableEmailForm = false;
      } else {
        this.availableEmailForm = true;
      }
    },
    ...mapMutations(['toggleDrawer']),
    onClick(e, item) {
      this.$router.push(item.href).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          // 같은 경로 클릭시 새로고침 되게
          location.reload();
        }
      });
    },
    searchPwd() {
      this.dialog = false;
      this.$router.push('/searchPwd');
    },
    login: function() {
      event.preventDefault();
      this.dialog = false;
      // LOGIN 액션 실행
      // 서버와 통신(axios)을 해 토큰값을 얻어야 하므로 Actions를 호출.
      this.$store.dispatch('LOGIN', this.user);
      console.log(this.$store.getters.getAccessToken);
      this.user.email = '';
      this.user.pwd = '';
    },
    logout() {
      this.$store
        .dispatch('LOGOUT')
        .then(() => this.$router.replace('/').catch(() => {}));
      console.log(localStorage);
      localStorage.clear;
      console.log(localStorage);
    },
    registForm() {
      this.$router.push('/regist');
    },
    myPageForm() {
      this.$router.push('/myPage');
    },
    adminPageForm() {
      this.$router.push('/admin');
    },
    toHome() {
      // 같은 경로로 이동시에 충돌 안나게
      this.$router.push('/').catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
      // this.$router.push("/");
    },
    boardForm() {
      // 같은 경로로 이동시에 충돌 안나게
      this.$router.push('/board').catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
      // this.$router.push("/board");
    },
  },
  data() {
    return {
      dialog: false,
      user: {
        email: '',
        pwd: '',
      },
      availableEmailForm: true,
    };
  },
};
</script>
<style scoped>
@import '../../assets/style.css';
/* 모달창 스타일 */
.card {
  height: 350px;
  margin-top: auto;
  margin-bottom: auto;
  width: 400px;
  background-color: rgba(0, 0, 0, 0.726);
  /* border-radius: 5px; */
}
.social_icon span {
  font-size: 45px;
  margin-left: 10px;
  color: #ffc312;
}

.social_icon span:hover {
  color: white;
  cursor: pointer;
}

.card-header h3 {
  color: white;
}

.social_icon {
  position: absolute;
  right: 20px;
  top: -6px;
}

.input-group-prepend span {
  width: 50px;
  background-color: #ffc312;
  color: black;
  border: 0 !important;
}

input:focus {
  outline: 0 0 0 0 !important;
  box-shadow: 0 0 0 0 !important;
}

.remember {
  color: white;
}

.remember input {
  width: 20px;
  height: 20px;
  margin-left: 15px;
  margin-right: 5px;
}

.login_btn {
  color: black;
  background-color: #ffc312;
  width: 100px;
}

.login_btn:hover {
  color: black;
  background-color: white;
}

.links {
  color: white;
}

.links a {
  margin-left: 4px;
}
.test {
  box-shadow: none;
}
/* .v-dialog {
  box-shadow: none;
} */
/* .v-dialog--active {
  box-shadow: none;
} */
/* 모달창 스타일 끝 */
</style>
