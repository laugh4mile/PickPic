<template>
  <div class="container font-ELAND_Choice_M">
    <div class="row mb-5">
        <label class="col-sm-2 col-md-2 newbtn selector" width="200">
          <img
            id="blah"
            :src="user.profileImg"
            onerror="this.src=`https://apfbucket.s3.ap-northeast-2.amazonaws.com/c8c25cb23bdd4aa9a5c4608b7fa243ef.png`"
            alt="프로필 이미지"
          />
          <input id="pic" class="pis" @change="addProfile" type="file" />
        </label>
        <div class="col-sm-10 col-md-10">
          <blockquote style="width:300px;">
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
        <div class="row">
          <v-btn
            color="secondary"
            class="mr-2"
            outlined
            v-show="!modify"
            @click="showmodifyForm"
            >자기소개수정</v-btn
          >
          <v-btn
            color="secondary"
            class="mr-2"
            outlined
            v-show="modify"
            @click="modifyIntro"
            >정보수정</v-btn
          >
          <div class="text-center">
            <delete-user-modal />
            <modify-pwd-modal />
          </div>
        </div>
    </div>
    <hr>
      <div>
        <div class="font-yg-jalnan">
        내가 작성한 글
        </div>
        <br>
        <div v-for="(post, index) in posts" :key="index" >
          <BoardDesign :value="post"/>
        </div>
      </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
import DeleteUserModal from "./deleteUserModal.vue";
import ModifyPwdModal from "./modifyPwdModal.vue";
import BoardDesign from "../sub2/BoardDesign.vue";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      user: {},
      modify: false,
      modify: false,
      imgSrc: "",
      posts:{},
    };
  },
  components: {
    NewestPosts: () => import("@/components/NewestPosts"),
    Instagram: () => import("@/components/Instagram"),
    Tags: () => import("@/components/Tags"),
    DeleteUserModal,
    ModifyPwdModal,
    BoardDesign,
  },
  computed: {
    ...mapGetters(["getAccessToken", "getUserEmail", "getUserName", "getRole"]),
  },
  created() {
    const params = new URLSearchParams();
    params.append("email", this.getUserEmail);
    axios
      .get(`${SERVER_URL}/member`, { params })
      .then((response) => {
        this.user = null;
        this.user = response.data.info;
        this.posts = response.data.postList;
        this.user.profileImg =
          "https://apfbucket.s3.ap-northeast-2.amazonaws.com/" +
          response.data.info.profileImg;
      })
      .catch(() => {
        this.$router.push({
          path: "/Error",
          query: { status: error.response.status },
        });
      });
  },
  methods: {
    addProfile: function(input) {
      if (input.target.files[0]) {
        if (this.user.profileImg) {
          const params = new URLSearchParams();
          params.append("email", this.user.email);
          axios
            .get(`${SERVER_URL}/member/delete`, { params })
            .then((response) => {})
            .catch((err) => {
              this.$router.push({
                path: "/Error",
                query: { status: error.response.status },
              });
            });
        }
        var frm = new FormData();
        var photoFile = input.target.files[0];
        frm.append("profileImg", photoFile);
        frm.append("email", this.user.email);
        axios
          .post(`${SERVER_URL}/member/upload`, frm, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((response) => {
            alert("프로필 업로드 완료");
            const params = new URLSearchParams();
            params.append("email", this.getUserEmail);
            axios
              .get(`${SERVER_URL}/member/delete`, { params })
              .then((response) => {
                console.log("기존이미지 삭제");
              })
              .catch(() => {
                this.$router.push({
                  path: "/Error",
                  query: { status: error.response.status },
                });
              });
          })
          .catch((error) => {
            this.$router.push({
              path: "/Error",
              query: { status: error.response.status },
            });
          });
      }
    },
    showmodifyForm: function() {
      this.modify = true;
    },
    modifyIntro() {
      axios
        .put(`${SERVER_URL}/member/intro`, {
          email: this.user.email,
          introduce: this.user.introduce,
        })
        .then((response) => {
          this.modify = false;
        })
        .catch((error) => {
          this.$router.push({
            path: "/Error",
            query: { status: error.response.status },
          });
        });
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

#pic {
  display: none;
}

.newbtn {
  cursor: pointer;
}
#blah {
  max-width: 100px;
  height: 100px;
  margin-top: 20px;
}
</style>
