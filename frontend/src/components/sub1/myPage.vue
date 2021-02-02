<template>
  <div class="container">
    <div class="row">
      <label class="col-sm-2 col-md-2 newbtn selector" width="200">
        <img id="blah" :src=user.profileImg onerror="this.src=`https://apfbucket.s3.ap-northeast-2.amazonaws.com/c8c25cb23bdd4aa9a5c4608b7fa243ef.png`" alt="프로필 이미지"/>
        <input id="pic" class='pis' @change="addProfile" type="file" >
      </label>
      <div class="col-sm-10 col-md-10">
        <blockquote>
          <p>{{ user.name }}</p>
          <small
            ><cite title="Source Title">{{ user.email }}</cite></small
          >
          <small
            ><cite title="Source Title">{{ user.joinDate }}</cite></small
          >
          <span v-show="!modify">{{ user.introduce }}</span>
          <v-text-field
            color="black"
            v-show="modify"
            v-model="user.introduce"
            type="text"
            label="자기소개를 수정해주세요"
            :value="user.introduce"
          ></v-text-field>
        </blockquote>
      </div>
      <v-btn variant="primary" v-show="!modify" @click="showmodifyForm"
        >자기소개수정</v-btn
      >
      <v-btn variant="primary" v-show="modify" @click="modifyIntro"
        >정보수정</v-btn
      >
      <div class="text-center">
        <v-dialog v-model="dialog1" width="500">
          <template v-slot:activator="{ on, attrs }" @click="dialog1 = true">
            <v-btn variant="primary" v-bind="attrs" v-on="on">
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
              <v-btn color="primary" @click="DeleteUser">
                탈퇴
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialog2" width="500">
          <template v-slot:activator="{ on, attrs }" >
            <v-btn variant="primary" v-bind="attrs" v-on="on">
              비밀번호변경
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="headline grey lighten-2">
              비밀번호 변경
            </v-card-title>

            <v-col cols="12">
              <span
                >비밀번호 변경하려면 기존 비밀번호를 작성해주세요</span
              >
            </v-col>
            <v-col cols="12">
              <v-text-field
                label="기존비밀번호*"
                type="password"
                required
                v-model="pwd"
                :rules="[pwd === user.pwd || '페스워드가 불일치합니다.']"
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
              <v-btn color="primary" @click="modifyPwd">
                비밀번호 변경
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
    </div>
    <v-col cols="12" md="4">
      <instagram />
    </v-col>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      user: {},
      modify: false,
      dialog1: false,
      dialog2: false,
      chk: "",
      introduce: "",
      modify: false,
      pwd: "",
      pwd1: "",
      pwd2: "",
      imgSrc: "",
      pwdRules: [
        (v) =>
          /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(v) ||
          "숫자 와 문자 1개 이상씩 사용해야하며 8자리 이상 작성해야합니다.",
      ],
    };
  },
  components: {
    NewestPosts: () => import("@/components/NewestPosts"),
    Instagram: () => import("@/components/Instagram"),
    Tags: () => import("@/components/Tags"),
  },
  computed: {
    ...mapGetters(["getAccessToken", "getUserEmail", "getUserName", "getRole"]),
  },
  created() {
    console.log("created");
    console.log(this.$store.getters.getRole);
    console.log(this.$store.getters.getUserEmail);
    const params = new URLSearchParams();
    params.append("email", this.getUserEmail);
    axios
      .get("http://localhost:3000/sub/member", { params })
      .then((response) => {
        console.log('리스폰스',response);
        this.user = null;
        this.user = response.data.info;
        this.user.profileImg = 'https://apfbucket.s3.ap-northeast-2.amazonaws.com/'+response.data.info.profileImg
        console.log(this.user);
        console.log(this.user.email)
      })
      .catch(() => {
        // this.$router.push("/Error");
      });
    
  },
  methods: {
    
    addProfile: function(input) {
      console.log(input.target.files[0]);
      if (this.user.profileImg) {
        const params = new URLSearchParams();
        params.append("email", this.user.email);
        axios.get('http://localhost:3000/sub/member/delete', {params})
        .then((response) => {
          console.log('기존이미지 삭제')
        })
        .catch((err) => {
          console.log(err)
        });
      }
      var frm = new FormData();
      var photoFile = input.target.files[0]
      frm.append("profileImg", photoFile);
      frm.append("email", this.user.email);
      axios.post('http://localhost:3000/sub/member/upload', frm,{
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then((response) => {
        alert('프로필 업로드 완료');
        const params = new URLSearchParams();
        params.append("email", this.getUserEmail);
        axios
          .get("http://localhost:3000/sub/member", { params })
          .then((response) => {
            this.user = null;
            this.user = response.data.info;
            this.user.profileImg = 'https://apfbucket.s3.ap-northeast-2.amazonaws.com/'+response.data.info.profileImg
          })
          .catch(() => {
            // this.$router.push("/Error");
          });
      })
      .catch(error => {
        this.$router.push("/Error");
      });
    },
    showmodifyForm: function() {
      this.modify = true;
    },
    DeleteUser: function() {
      const params = new URLSearchParams();
      params.append("email", this.user.email);
      console.log(params);
      if (this.chk == "삭제하겠습니다.") {
        axios
          .delete("http://localhost:3000/sub/member", {
            data: {
              email: this.getUserEmail,
            },
          })
          .then((response) => {
            console.log(response);
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
    modifyIntro() {
      axios
        .put("http://localhost:3000/sub/member/intro", {
          email: this.user.email,
          introduce: this.user.introduce,
        })
        .then((response) => {
          console.log(response);
          this.modify = false;
        })
        .catch((error) => {
          this.$router.push("/Error");
        });
    },
    modifyPwd() {
      if (this.pwd == this.user.pwd && this.pwd1 == this.pwd2) {
        axios
          .put("http://localhost:3000/sub/member/pwd", {
            email: this.user.email,
            pwd: this.pwd2,
          })
          .then((response) => {
            const params = new URLSearchParams();
            params.append("email", this.getUserEmail);
            axios
              .get("http://localhost:3000/sub/member", { params })
              .then((response) => {
                this.user = null;
                this.user = response.data.info;
              })
              .catch(() => {
                // this.$router.push("/Error");
              });
            this.dialog2 = false
          })
          .catch((error) => {
            this.$router.push("/Error");
          });
      } else {
        alert("내용을 확인해주세요");
      }
    },
  },
};
</script>

<style>
body {
  padding-top: 30px;
}

.glyphicon {
  margin-bottom: 10px;
  margin-right: 10px;
}

small {
  display: block;
  line-height: 1.428571429;
  color: #999;
}
/* 이미지 꽉차게 */

.selector img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}


#pic{
display: none;
}

.newbtn{
  cursor: pointer;
}
#blah{
  max-width:100px;
  height:100px;
  margin-top:20px;
}
</style>
