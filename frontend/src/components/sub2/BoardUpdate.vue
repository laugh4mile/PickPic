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
    <input
      multiple="multiple"
      ref="file"
      type="file"
      id="file"
      name="file"
      @change="onChangeImages"
    />
    <div>
      <v-row align="center">
        <span>
          <v-col v-for="(item, idx) in imgUrl" :key="idx">
            <img :src="item.modPicName" :id="'img' + idx" width="300px" height="300px" />
            <!-- <input
              ref="file"
              type="file"
              id="file"
              name="file"
              @change="onChangeImages($event, item, index)"
            /> -->
            <v-btn color="red darken-1" @click="modiImg(item,idx)">
            Delete
          </v-btn>
          </v-col>
          <v-col cols="5" v-for="(imgUrl, index) in imageUrl" :key="index">
          <v-img max-width="800px" max-height="800px" :src="imgUrl"></v-img>
          <v-btn color="red darken-1" @click="deleteImg(index)">
            Delete
          </v-btn>
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
      myfile: [],
      imageUrl: [],
      deleted: [],
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
          this.imgUrl.push(response.data.fileList[i]);
        }
        console.log(this.imgUrl)
        this.board = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },

  methods: {
    modiImg(item, index) {
      this.deleted.push(item.picNo)
      this.imgUrl.splice(index, 1)
      console.log(item)
      console.log(index)
    },
    deleteImg(index) {
      console.log(index)
      this.imageUrl.splice(index, 1)
      this.myfile.splice(index, 1)
    },
    modifyComplete() {
      var frm = new FormData();
      console.log(this.myfile)
      for (var i = 0; i < this.myfile.length; i++) {
        let file = this.myfile[i];
        frm.append('files', file);
      }
    // for (var i = 0; i < this.$refs.file.length; i++) {
    //     if(this.$refs.file[i].files.length>0){
    //         frm.append("files", this.$refs.file[i].files[0]);
    //     }
    //   }
      console.log(frm);
      for (var i = 0; i < this.modified.length; i++) {
        const idx = this.temp.indexOf(this.modified[i]);
        this.temp.splice(idx,1);
      }
      // for (var i = 0; i < this.temp.length; i++){
      //   frm.append("unmodified", this.temp[i]);

      // }
      if (this.modified.length <1) {
        console.log('변경된거 없음')
        this.temp = []
      }
      console.log('모디파이드 배열은',this.modified)
      console.log('최종보내줄 배열은',this.temp)
      frm.append("unmodified",this.deleted)
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
    onChangeImages(e) {
      var file = e.target.files;
      for (var i = 0; i < file.length; i++) {
        this.imageUrl.push(URL.createObjectURL(file[i]));
        this.myfile.push(this.$refs.file.files[i])
      }
      console.log(this.myfile)
      console.log(this.imageUrl)
    },
    // onChangeImages(e) {
    //   this.myfile.push(event.target.files[0])
    //   document.getElementById("img" + index).src = URL.createObjectURL(
    //     event.target.files[0]
    //   );
    //   console.log(event);
    //   this.not[index] = event.target.files[0];
    //   this.modified = [];
    //   for (var n = 0; n < this.not.length; n++) {
    //     //   console.log(this.not[n].picNo)
    //     if (this.not[n].picNo) {
    //       this.modified.push(this.not[n].picNo);
    //     }
    //     //   console.log(this.not[n])
    //   }
    //   //   this.imgUrl[0] = URL.createObjectURL(event.target.files[0]);
    //   console.log(this.modified);
    // },
  },
  computed: {},
};
</script>
