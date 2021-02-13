<template>
    <v-dialog v-model="dialog1" width="500">
          <template v-slot:activator="{ on, attrs }" @click="dialog1 = true">
            <v-btn class="mr-2" color="secondary" outlined v-bind="attrs" v-on="on">
              회원탈퇴
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="headline grey lighten-2">
              회원탈퇴
            </v-card-title>

            <v-col cols="12">
              <span>회원 탈퇴하려면 아래의 문장을 따라 입력하세요</span>
            </v-col>
            <v-col cols="12">
              <span>삭제하겠습니다.</span>
            </v-col>
            <v-col cols="12">
              <v-text-field
                label="따라 입력*"
                type="text"
                v-model="chk"
                required
              ></v-text-field>
            </v-col>

            <v-divider></v-divider>
            <v-card-actions>
              <v-spacer></v-spacer>
              <!-- <custom-button :title="'탈퇴'" @click="DeleteUser"/> -->
              <v-btn color="secondary" outlined @click="DeleteUser">
                탈퇴
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
  data() {
    return {
      dialog1: false,
      chk: "",
    };
  },
  components:{
      customButton,
  },
  computed: {
    ...mapGetters(["getAccessToken", "getUserEmail", "getUserName", "getRole"]),
  },
  methods: {
    DeleteUser: function() {
      const params = new URLSearchParams();
      params.append("email", this.getUserEmail);
      console.log(params);
      if (this.chk == "삭제하겠습니다.") {
        axios
          .delete(`${SERVER_URL}/member`, {
            data: {
              email: this.getUserEmail,
            },
          })
          .then((response) => {
            this.$store
              .dispatch("LOGOUT")
              .then(() => this.$router.replace("/").catch(() => {}));
          })
          .catch((error) => {
            this.$router.push("/Error");
          });
      } else {
        alert("문구가 일치하지 않습니다.");
      }
    },
  },
};
</script>