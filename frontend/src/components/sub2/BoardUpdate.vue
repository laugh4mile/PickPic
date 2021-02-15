<template>
  <div class="container">
    <editor :contents="this.board.postInfo" @text="emitedData"></editor>
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
    <br>
    <v-btn color="secondary" outlined @click="modifyComplete">수정 완료</v-btn>
  </div>
</template>
<script>
import axios from "axios";
import editor from "../sub3/Editor.vue"
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
      cont: '',
    };
  },
  components:{
    editor,
  },
  created() {
    this.board = this.$route.query.board;
    axios
      .get(`${SERVER_URL}/post`, {
        params: {
          email: this.$store.getters.getUserEmail,
          postNo: this.$route.query.board.postInfo.postNo,
        },
      })
      .then((response) => {
        this.not = response.data.fileList;
        for (var n = 0; n < this.not.length; n++) {
          this.temp.push(this.not[n].picNo);
        }
        this.cont = response.data.postInfo.content;
        for (var i = 0; i < response.data.fileList.length; i++) {
          this.imgUrl.push(response.data.fileList[i]);
        }
        this.board = response.data;
      })
      .catch((error) => {
        this.$router.push({path: '/Error', query: {'status' : error.response.status}});
      });
  },

  methods: {
  emitedData(event){
      this.board.postInfo = event;
    },
    modiImg(item, index) {
      this.deleted.push(item.picNo)
      this.imgUrl.splice(index, 1)
    },
    deleteImg(index) {
      this.imageUrl.splice(index, 1)
      this.myfile.splice(index, 1)
    },
    modifyComplete() {
      var frm = new FormData();
      
      for (var i = 0; i < this.myfile.length; i++) {
        let file = this.myfile[i];
        frm.append('files', file);
      }
    // for (var i = 0; i < this.$refs.file.length; i++) {
    //     if(this.$refs.file[i].files.length>0){
    //         frm.append("files", this.$refs.file[i].files[0]);
    //     }
    //   }
      for (var i = 0; i < this.modified.length; i++) {
        const idx = this.temp.indexOf(this.modified[i]);
        this.temp.splice(idx,1);
      }
      // for (var i = 0; i < this.temp.length; i++){
      //   frm.append("unmodified", this.temp[i]);

      // }
      if (this.modified.length <1) {
        this.temp = []
      }
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
          this.$router.push({path: '/Error', query: {'status' : error.response.status}});
        });
    },
    onChangeImages(e) {
      var file = e.target.files;
      for (var i = 0; i < file.length; i++) {
        this.imageUrl.push(URL.createObjectURL(file[i]));
        this.myfile.push(this.$refs.file.files[i])
      }
    },
  },
  computed: {},
};
</script>
