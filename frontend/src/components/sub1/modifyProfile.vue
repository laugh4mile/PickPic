<template>
  <v-form
    class="container"
    ref="form"
    v-model="valid"
    lazy-validation
  >
    <v-container class="mt-4">
      <v-row>
        <v-col cols="2"></v-col>
        <v-col cols="2" align-self="end">이메일</v-col
        ><v-col cols="5" align-self="start">{{ user.email }}</v-col>
        <v-col cols="2"></v-col>
      </v-row>
      <v-row>
        <v-col cols="2"></v-col>
        <v-col cols="2" align-self="end">이름</v-col
        ><v-col cols="5" align-self="start">{{ user.name }}</v-col>
        <v-col cols="2"></v-col>
      </v-row>
      <v-row>
        <v-col cols="2"></v-col>
        <v-col cols="2" align-self="end">가입일</v-col
        ><v-col cols="5" align-self="start">{{ user.joinDate }}</v-col>
        <v-col cols="2"></v-col>
      </v-row>
    </v-container>

    <v-row>
      <v-col cols="2"></v-col>
      <v-col cols="5" align-self="start">
        <v-text-field
          v-model="user.introduce"
          type="text"
          label="자기소개"
        ></v-text-field>
      </v-col>
    </v-row>
 

    <v-row>
      <v-col cols="2"></v-col>
      <v-col cols="5" align-self="start">
        <v-checkbox
          v-model="checkbox"
          :rules="[v => !!v || 'You must agree to continue!']"
          label="Do you agree?"
          required
        ></v-checkbox>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="2"></v-col>
      <v-col cols="5" align-self="start">
        <v-btn
          :disabled="!valid"
          color="success"
          class="mr-4"
          @click="onSubmit($event)"
        >
          회원가입
        </v-btn>

        <v-dialog v-model="dialog" width="500">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn variant="primary" v-bind="attrs" v-on="on">
                    회원탈퇴
                  </v-btn>
                </template>
                <v-card>
                  <v-card-title class="headline grey lighten-2">
                    비밀번호 변경
                  </v-card-title>

                  <v-col cols="12">
                    <span>비밀번호 변경하려면 기존 비밀번호를 작성해주세요</span>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      label="기존비밀번호*"
                      type="text"
                      required
                    ></v-text-field>
                    <v-text-field
                      label="새 비밀번호 입력*"
                      type="text"
                      required
                    ></v-text-field>
                    <v-text-field
                      label="새 비밀번호 확인*"
                      type="text"
                      required
                    ></v-text-field>
                  </v-col>

                  <v-divider></v-divider>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" @click="modifyPwd">
                      비밀번호 변경
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
        <v-btn color="error" class="mr-4" @click="onSubmit">
          자기소개 변경
        </v-btn>
        <v-btn color="error" class="mr-4" @click="reset">
          초기화
        </v-btn>
      </v-col>
    </v-row>
  </v-form>
</template>
<script>
import axios from "axios";
import { mapGetters } from "vuex";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

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
    dialog: false,
    verifys: false,
    verityCode: "",
    select: null,

    checkbox: false,
    beforepwd: "",
    pwd: "",
    chkpwd: "",
    chkpwdRule: [v => v || "password not equal"],
    user: {
      email: "",
      pwd: "",
      name: ""
    }
  }),
  computed: {
    ...mapGetters(["getAccessToken", "getUserEmail", "getUserName", "getRole"])
  },
  created() {
    console.log("created");
    console.log(this.$store.getters.getRole);
    const params = new URLSearchParams();
    params.append("email", this.getUserEmail);
    axios
      .get('http://localhost:3000/sub/member', {params})
      .then(response => {
        console.log(response);
        this.user = null;
        this.user = response.data.info;
        console.log(this.user);
      })
      .catch(() => {
        // this.$router.push("/Error");
      });
  },
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
          alert("메일이 전송되었습니다/");
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
            alert("인증 성공");
          } else {
            alert("인증 번호가 틀립니다.");
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    onSubmit() {
      this.$refs.form.validate();
          axios.put("http://localhost:3000/sub/member", {
              email: this.user.email,
              introduce : this.user.introduce,
              pwd : 'awww',
          })
          .then(response => {
            console.log(response);
            this.$router.push("/myPage");
          })
          .catch(error => {
            this.$router.push("/Error");
      })
    },
    modifyPwd() {
      this.$refs.form.validate();
          axios.put("http://localhost:3000/sub/member", {
              email: this.user.email,
              introduce : this.user.introduce,
              pwd : 'awww',
          })
          .then(response => {
            console.log(response);
            this.$router.push("/myPage");
          })
          .catch(error => {
            this.$router.push("/Error");
      })
    }
  }
};
</script>
