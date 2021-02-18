<template>
  <v-form
    class="container font-ELAND_Choice_M"
    action="/"
    ref="form"
    v-model="valid"
    lazy-validation
  >
    <v-row>
      <v-col cols="12" md="10">
        <v-text-field
          v-model="user.name"
          :counter="10"
          :rules="nameRules"
          :append-icon="marker.nameMarker ? 'mdi-check' : ''"
          :error-messages="!arletName ? '중복검사를 해주세요' : ''"
          label="닉네임"
          required
        ></v-text-field>
      </v-col>
      <!-- <i v-show="marker" class="fas fa-check align-self-center" style="color:green"></i> -->
      <v-btn
        outlined
        class="btn-outline-info mt-10 rounded-pill"
        @click="checkNameDuplicate"
        >닉네임 중복검사</v-btn
      >
    </v-row>
    <v-row>
      <v-col cols="12" md="10">
        <v-text-field
          v-model="user.email"
          :rules="emailRules"
          :append-icon="marker.emailMarker ? 'mdi-check' : ''"
          :error-messages="!arletEmail ? '중복검사를 해주세요' : ''"
          label="이메일"
          required
        ></v-text-field>
      </v-col>
      <v-btn
        outlined
        class="btn-outline-info mt-10 rounded-pill"
        @click="checkEmailDuplicate"
        >이메일 중복검사</v-btn
      >
      <v-btn
        class="btn-outline-info rounded-pill"
        outlined
        @click="sendVerifyCode"
        >이메일 인증</v-btn
      >
    </v-row>
    <v-row>
      <v-col cols="12" md="10">
        <v-text-field
          v-model="verityCode"
          label="인증번호 입력"
          required
          :error-messages="!arletCode ? '코드를 확인해주세요' : ''"
        />
      </v-col>
      <v-btn
        outlined
        class="btn-outline-info mt-10 rounded-pill"
        @click="verifyBtn"
        >인증</v-btn
      >
    </v-row>
    <span v-if="verifys" class="valid">인증 완료</span>
    <v-row>
      <v-col cols="12" md="10">
        <v-text-field
          v-model="user.pwd"
          :rules="pwdRules"
          type="password"
          label="비밀번호"
          required
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="10">
        <v-text-field
          v-model="chkpwd"
          :rules="[user.pwd === chkpwd || '페스워드가 불일치합니다.']"
          type="password"
          label="비밀번호 확인"
          required
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="10">
        <v-text-field
          v-model="user.introduce"
          type="text"
          label="자기소개"
        ></v-text-field>
      </v-col>
    </v-row>
    <!-- <v-checkbox
      v-model="checkbox"
      :rules="[(v) => !!v || 'You must agree to continue!']"
      label="Do you agree?"
      @click="opneTerms"
      required
    ></v-checkbox> -->
    <v-checkbox v-model="checkbox">
      <template v-slot:label>
        <div class="mt-2">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <!-- <a
                target="_blank"
                href=""
                @click="opneTerms"
                v-on="on"
              >
                이용약관
              </a> -->
              <terms-modal @agree="agreeTerms" @disagree="disagreeTerms" :on="on"/>
            </template>
            약관확인
          </v-tooltip>
          에 동의합니다
        </div>
      </template>
    </v-checkbox>
    
    <v-btn
      color="success"
      outlined
      :disabled="!valid"
      class="mr-4 rounded-pill"
      type="submit"
      @click="onSubmit($event)"
    >
      회원가입
    </v-btn>

    <v-btn color="error" outlined class="mr-4 rounded-pill" @click="reset">
      초기화
    </v-btn>
  </v-form>
</template>
<script>
import axios from 'axios';
import customButton from '../design/btn.vue';
import TermsModal from './termsModal.vue';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    customButton,
    TermsModal,
  },
  data: () => ({
    valid: false,
    marker: {
      nameMarker: false,
      emailMarker: false,
      codeMarker: false,
      passwordMarker: false,
      passwordCheckMarker: false,
    },
    name: '',
    nameRules: [
      (v) => !!v || 'Name is required',
      (v) => (v && v.length <= 10) || 'Name must be less than 10 characters',
    ],
    email: '',
    emailRules: [
      (v) => !!v || 'E-mail is required',
      (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
    ],
    pwdRules: [
      (v) =>
        /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(v) ||
        '숫자 와 문자 1개 이상씩 사용해야하며 8자리 이상 작성해야합니다.',
    ],
    verifys: false,
    verityCode: '',
    select: null,

    checkbox: false,
    pwd: '',
    chkpwd: '',
    chkpwdRule: [(v) => v || 'password not equal'],
    user: {
      email: '',
      pwd: '',
      name: '',
      introduce: '',
    },
    chkname: false,
    chkemail: false,
    arletName: true,
    arletEmail: true,
    arletCode: true,
  }),

  methods: {
    disagreeTerms() {
      this.checkbox = false
      console.log('약관미동의')
    },
    agreeTerms() {
      this.checkbox = true
      console.log('약관동의')
    },
    checkNameDuplicate() {
      const params = new URLSearchParams();
      params.append('name', this.user.name);
      axios
        .get(`${SERVER_URL}/member/nameCheck`, { params })
        .then((response) => {
          if (this.user.name) {
            if (response.data) {
              this.$alert('중복된이름','','error');
              this.chkname = false;
              this.marker.nameMarker = false;
              this.arletName = false;
            } else {
              this.$alert('사용가능','','success');
              this.chkname = true;
              this.marker.nameMarker = true;
              this.arletName = true;
            }
          } else {
            this.$alert('이름을 입력해주세요','','warning');
            this.chkname = false;
            this.marker.nameMarker = false;
          }
        })
        .catch((err) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
    checkEmailDuplicate() {
      if (/.+@.+\..+/.test(this.user.email)) {
        const params = new URLSearchParams();
        params.append('email', this.user.email);
        axios
          .get(`${SERVER_URL}/member/emailCheck`, { params })
          .then((response) => {
            if (this.user.email) {
              if (response.data) {
                this.$alert('중복된 메일','','error');
                this.chkemail = false;
                this.marker.emailMarker = false;
                this.arletEmail = false;
              } else {
                this.$alert('사용가능','','success');
                this.chkemail = true;
                this.marker.emailMarker = true;
                this.arletEmail = true;
              }
            } else {
              this.$alert('메일을 입력해주세요','','warning');
              this.chkemail = true;
              this.marker.emailMarker = false;
            }
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        this.$alert('메일 형식에 맞게 입력해주세요','','warning');
        this.chkemail = false;
        this.marker.emailMarker = false;
        this.arletEmail = false;
      }
    },
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    sendVerifyCode() {
      const params = new URLSearchParams();
      params.append('email', this.user.email);
      axios
        .post(`${SERVER_URL}/service/mail`, params)
        .then((response) => {
          this.$alert('메일이 전송되었습니다','','success');
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
    verifyBtn() {
      const params = new URLSearchParams();
      params.append('code', this.verityCode);
      axios
        .post(`${SERVER_URL}/service/verifyCode`, params)
        .then((response) => {
          if (response.data == 1) {
            this.verifys = true;
            this.$alert('인증 성공','','success');
            this.arletCode = true;
          } else {
            this.$alert('인증 번호가 틀립니다.','','error');
            this.arletCode = false;
          }
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
    onSubmit(event) {
      this.$refs.form.validate();
      if (!this.verifys) {
        event.preventDefault();
        this.$alert('이메일 인증 미완료','','warning');
      } else if (!this.chkname || !this.chkemail) {
        this.$alert('중복검사를 해주세요','','warning');
      } else if (!this.checkbox) {
        this.$alert('약관에 동의해주세요','','warning');
      } else {
        axios
          .post(`${SERVER_URL}/member`, this.user)
          .then((response) => {
            this.$alert('회원가입 완료','','success');
            this.$router.push('/');
          })
          .catch((error) => {
            this.$router.push({
              path: '/Error',
              query: { status: error.response.status },
            });
          });
      }
    },
  },
};
</script>
<style scoped>
.valid {
  color: green;
}
</style>
