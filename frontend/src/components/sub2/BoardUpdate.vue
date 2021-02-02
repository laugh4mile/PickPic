<template>
  <div class="container">
    <v-text-field
      label="제목"
      v-model="board.postInfo.title"
      :value="board.postInfo.title"
    ></v-text-field>
    <v-textarea
      label="본문"
      rows="5"
      v-model="board.postInfo.content"
      :value="board.postInfo.content"
    ></v-textarea>

    <div>
      <v-row align="center">
        <span v-for="(item, index) in imgUrl" :key="index">
          <v-col>
            <img :src="item" :id="'img' + index" width="300px" height="300px" />
            <input
              ref="file"
              type="file"
              id="file"
              name="file"
              @change="onChangeImages($event, item, index)"
            />
          </v-col>
        </span>
      </v-row>
    </div>

    <v-btn dark @click="modifyComplete">수정 완료</v-btn>
  </div>
</template>
<script>
import axios from "axios";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      board: {},
      imgUrl: [],
      not: [],
      modified: [],
      temp: [],
    };
  },
  created() {
    // this.board = this.$route.query.board;
    axios
      .get(`${SERVER_URL}/post`, {
        params: {
          email: this.$store.getters.getUserEmail,
          postNo: this.$route.query.board.postInfo.postNo,
        },
      })
      .then((response) => {
        console.log(response);
        this.not = response.data.fileList;
        console.log(this.not);
        for (var n = 0; n < this.not.length; n++) {
          this.temp.push(this.not[n].picNo);
        }
        console.log('템프는',this.temp)
        for (var i = 0; i < response.data.fileList.length; i++) {
          this.imgUrl.push(
            `${SERVER_URL}/post/imgs/download?fileName=` +
              response.data.fileList[i].modPicName +
              "&postNo=" +
              this.$route.query.board.postInfo.postNo
          );
        }
        this.board = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },

  methods: {
   modifyComplete() {
      var frm = new FormData();
      console.log('모디파ㄴ이는',this.modified)
    for (var i = 0; i < this.$refs.file.length; i++) {
        if(this.$refs.file[i].files.length>0){
            frm.append("files", this.$refs.file[i].files[0]);
        }
      }
      console.log(frm);
      for (var i = 0; i < this.modified.length; i++) {
        const idx = this.temp.indexOf(this.modified[i]);
        this.temp.splice(idx,1);
      }
      for (var i = 0; i < this.temp.length; i++){
        frm.append("unmodified", this.temp[i]);

      }
      if (this.modified.length <1) {
        console.log('변경된거 없음')
        this.temp = []
      }
      console.log('모디파이드 배열은',this.modified)
      console.log('최종보내줄 배열은',this.temp)

      frm.append("postNo", this.board.postInfo.postNo);
      frm.append("content", this.board.postInfo.content);
      frm.append("title", this.board.postInfo.title);
      axios
        .put(`${SERVER_URL}/post`, frm)
        .then((response) => {
          alert("수정 완료");
          this.$router.push("/board/" + this.board.postInfo.postNo);
        })
        .catch((error) => {
          alert("수정 실패");
        });
    },
    onChangeImages(event, item, index) {
      document.getElementById("img" + index).src = URL.createObjectURL(
        event.target.files[0]
      );
      console.log(event);
      this.not[index] = event.target.files[0];
      this.modified = [];
      for (var n = 0; n < this.not.length; n++) {
        //   console.log(this.not[n].picNo)
        if (this.not[n].picNo) {
          this.modified.push(this.not[n].picNo);
        }
        //   console.log(this.not[n])
      }
      //   this.imgUrl[0] = URL.createObjectURL(event.target.files[0]);
      console.log(this.modified);
    },
  },
  computed: {},
};
</script>
