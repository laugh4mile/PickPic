<template>
  <v-dialog v-model="dialog2" width="500">
    <template v-slot:activator="{ on, attrs }">
      <v-btn color="secondary" outlined v-bind="attrs" v-on="on">
        비밀번호변경
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
          type="password"
          required
          v-model="pwd"
        ></v-text-field>
        <v-text-field
          label="새 비밀번호 입력*"
          type="password"
          required
          v-model="pwd1"
          :rules="pwdRules"
        ></v-text-field>
        <v-text-field
          label="새 비밀번호 확인*"
          type="password"
          required
          v-model="pwd2"
          :rules="[pwd1 === pwd2 || '페스워드가 불일치합니다.']"
        ></v-text-field>
      </v-col>

      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <!-- <custom-button :title="'비밀번호 변경'" @click="modifyPwd"/> -->
        <v-btn color="secondary" outlined @click="modifyPwd">
          비밀번호 변경
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import axios from "axios";
import { mapGetters } from "vuex";
import customButton from "../design/btn.vue"

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
    components:{
        customButton,
    },
  data() {
    return {
      dialog2: false,
      pwd: "",
      pwd1: "",
      pwd2: "",
      pwdRules: [
        (v) =>
          /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(v) ||
          "숫자 와 문자 1개 이상씩 사용해야하며 8자리 이상 작성해야합니다.",
      ],
    };
  },
  computed: {
    ...mapGetters(["getAccessToken", "getUserEmail", "getUserName", "getRole"]),
  },
  methods: {
    modifyPwd() {
      if (this.pwd1 == this.pwd2) {
        if (this.pwd == this.pwd1) {
          alert('기존비밀번호와 같습니다.')
        } else {
          
          axios
            .put(`${SERVER_URL}/member/pwd`, {
              email: this.getUserEmail,
              pwd: this.pwd2,
              prePwd: this.pwd
            })
            .then((response) => {
              if(response.data) {
                alert('비밀번호변경완료')
                const params = new URLSearchParams();
                params.append("email", this.getUserEmail);
                axios
                  .get(`${SERVER_URL}/member`, { params })
                  .then((response) => {
                    this.user = null;
                    this.user = response.data.info;
                    this.user.profileImg = 'https://apfbucket.s3.ap-northeast-2.amazonaws.com/'+response.data.info.profileImg
                  })
                  .catch(() => {
                    this.$router.push({path: '/Error', query: {'status' : error.response.status}});
                  });
                this.dialog2 = false
              } else {
                alert('비밀번호가 다릅니다.')
              }
              
            })
            .catch((error) => {
              this.$router.push({path: '/Error', query: {'status' : error.response.status}});
            });
        }
      } else {
        alert("내용을 확인해주세요");
      }
    },
  },
};
</script>