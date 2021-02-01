<template>
  <v-form class="container" action="/" ref="form" v-model="valid" lazy-validation>
    <v-text-field
      v-model="user.name"
      :counter="10"
      :rules="nameRules"
      label="Name"
      required
    ></v-text-field>

    <v-row>
      <v-col cols="12" md="10">
        <v-text-field
          v-model="user.email"
          :rules="emailRules"
          label="E-mail"
          required
        ></v-text-field>
      </v-col>
      <v-btn class="mt-10" @click="sendVerifyCode">이메일 인증</v-btn>
    </v-row>
    <v-row>
      <v-col cols="12" md="10">
        <v-text-field v-model="verityCode" label="인증번호 입력" required />
      </v-col>
      <v-btn class="mt-10" @click="verifyBtn">인증</v-btn>
    </v-row>
    <span v-if="verifys">인증 완료</span>

    <v-text-field
      v-model="user.pwd"
      :rules="pwdRules"
      type="password"
      label="Password"
      required
    ></v-text-field>

    <v-text-field
      v-model="chkpwd"
      :rules="[(user.pwd === chkpwd) || '페스워드가 불일치합니다.']"
      type="password"
      label="Check Password"
      required
    ></v-text-field>

        <v-text-field
      v-model="user.introduce"
      type="text"
      label="자기소개"
    ></v-text-field>

    <v-checkbox
      v-model="checkbox"
      :rules="[v => !!v || 'You must agree to continue!']"
      label="Do you agree?"
      required
    ></v-checkbox>

    <v-btn :disabled="!valid" color="success" class="mr-4" type="submit" @click="onSubmit($event)">
      회원가입
    </v-btn>

    <v-btn color="error" class="mr-4" @click="reset">
      초기화
    </v-btn>
  </v-form>
</template>
<script>
import axios from "axios";

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  data: () => ({
    valid: false,
    name: "",
    nameRules: [
      v => !!v || "Name is required",
      v => (v && v.length <= 10) || "Name must be less than 10 characters"
    ],
    email: "",
    emailRules: [
      v => !!v || "E-mail is required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],
    pwdRules:[
      v => /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(v) || "숫자 와 문자 1개 이상씩 사용해야하며 8자리 이상 작성해야합니다."
    ],
    verifys: false,
    verityCode: "",
    select: null,

    checkbox: false,
    pwd: "",
    chkpwd: '',
    chkpwdRule:[
      v => v || 'password not equal',
    ],
    user:{
      email:'',
      pwd:'',
      name:'',
      introduce: '',
    }
  }),

  methods: {
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    sendVerifyCode() {
      const params = new URLSearchParams();
      params.append("email", this.user.email);
      axios
        .post("http://localhost:3000/sub/service/mail", params)
        .then(response => {
          console.log(response);
          alert('메일이 전송되었습니다/');
        })
        .catch(error => {
          console.log(error);
        });
    },
    verifyBtn() {
      const params = new URLSearchParams();
      params.append("code", this.verityCode);
      axios
        .post("http://localhost:3000/sub/service/verifyCode", params)
        .then(response => {
          if (response.data == 1) {
            this.verifys = true;
            alert('인증 성공');
          }else{
            alert('인증 번호가 틀립니다.');
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    onSubmit(event){
        this.$refs.form.validate();
      if(!this.verifys){
        event.preventDefault();
        alert('이메일 인증 미완료');
      }else{
           axios.post('http://localhost:3000/sub/member', this.user).then((response) => {
             alert('회원가입 완료');
             this.$router.push("/");
        console.log(response);
      })
      .catch(error => {
        this.$router.push("/Error");
      });
      }
    }
  }
};
</script>
