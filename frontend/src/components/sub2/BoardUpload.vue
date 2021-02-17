<template>
  <div class="container font-ELAND_Choice_B">
    <v-row align="center">
      <!-- <v-text-field label="제목" v-model="title"></v-text-field> -->

      <div class="text-center">
        <v-dialog v-model="dialog" scrollable max-width="290">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="primary"
              dark
              v-bind="attrs"
              v-on="on"
              @click="getTemp"
            >
              임시저장 목록
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="headline">
              임시저장 목록
            </v-card-title>
            <v-card-text style="height: 300px;">
              <v-radio-group v-model="dialogm1" column>
                <v-radio
                  v-for="(item, index) in tempPost"
                  :key="index"
                  :label="item.title"
                  :value="item.postNo"
                />
              </v-radio-group>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="red darken-1" text @click="deleteForm">
                Delete
              </v-btn>
              <v-btn color="green darken-1" text @click="dialog = false">
                Disagree
              </v-btn>
              <v-btn color="green darken-1" text @click="getTempPost">
                Agree
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
    </v-row>
    <hr />
    <editor
      v-if="!temp"
      id="editor"
      @text="emitedData"
      @edit-img2="emitedImg"
      :contents="{ title: '', content: '' }"
    ></editor>
    <editor
      v-else
      id="editor"
      @text="emitedData"
      @edit-img2="emitedImg"
      :contents="tempBoard"
    ></editor>
    <!-- <v-textarea label="본문" v-model="content"></v-textarea> -->
    <input
      multiple="multiple"
      ref="file"
      type="file"
      id="file"
      name="file"
      @change="onChangeImages"
    />

    <v-btn @click="completeUpload">작성 완료</v-btn>
    <p>{{$refs}}</p>
    <v-btn @click="tempUpload">임시저장</v-btn>
    <div class="card">
      <v-row>
        <v-col v-for="(item, idx) in imgUrl" :key="idx">
          <img
            :src="item.modPicName"
            :id="'img' + idx"
            width="300px"
            height="300px"
          />
          <!-- <input
            ref="file"
            type="file"
            id="file"
            name="file"
            @change="onChangeImages($event, item, index)"
          /> -->
          <v-btn color="red darken-1" @click="modiImg(item, idx)">
            Delete
          </v-btn>
        </v-col>
        <v-col cols="5" v-for="(imgUrl, index) in imageUrl" :key="index">
          <v-img max-width="800px" max-height="800px" :src="imgUrl"></v-img>
          <v-btn color="red darken-1" @click="deleteImg(index)">
            Delete
          </v-btn>
        </v-col>
      </v-row>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
import Editor from '../sub3/Editor.vue';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      imageUrl: [],
      content: '',
      title: '',
      tempPost: [],
      dialogm1: '',
      dialog: false,
      postNo: -1,
      myfile: [],
      imgUrl: [],
      deleted: [],
      board: [],
      temp: false,
      tempBoard: {},
    };
  },
  components: {
    Editor,
  },
  methods: {
    emitedImg(data) {
      console.log(data)
      this.imageUrl.push(data.imgsrc);
      this.myfile.push(data.file);

    },
    emitedData(event) {
      this.content = event.content;
      this.title = event.title;
    },
    modiImg(item, index) {
      this.deleted.push(item.picNo);
      this.imgUrl.splice(index, 1);
    },
    deleteImg(index) {
      this.imageUrl.splice(index, 1);
      this.myfile.splice(index, 1);
    },
    getText(event) {
      this.content = event;
    },
    onChangeImages(e) {
      console.log(e)
      var file = e.target.files;
      for (var i = 0; i < file.length; i++) {
        this.imageUrl.push(URL.createObjectURL(file[i]));
        this.myfile.push(this.$refs.file.files[i]);
      }
    },
    getTempPost() {
      this.dialog = false;
      const params = new URLSearchParams();
      params.append('email', this.$store.getters.getUserEmail);
      params.append('postNo', this.dialogm1);
      axios
        .get(`${SERVER_URL}/post`, { params })
        .then((response) => {
          this.tempBoard.postNo = response.data.postInfo.postNo;
          this.tempBoard.title = response.data.postInfo.title;
          this.tempBoard.content = response.data.postInfo.content;
          this.content = response.data.postInfo.content;
          this.title = response.data.postInfo.title;
          this.postNo = response.data.postInfo.postNo;
          // this.postNo = response.data.postInfo.postNo;
          // this.title = response.data.postInfo.title;
          // this.content = response.data.postInfo.content;
          this.imgUrl = [];
          for (var i = 0; i < response.data.fileList.length; i++) {
            this.imgUrl.push(response.data.fileList[i]);
          }
          this.temp = true;
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
    tempUpload() {
      var frm = new FormData();
      for (var i = 0; i < this.$refs.file.files.length; i++) {
        let file = this.$refs.file.files[i];
        frm.append('files', file);
      }

      frm.append('postNo', this.postNo);
      frm.append('email', this.$store.getters.getUserEmail);
      frm.append('content', this.content);
      frm.append('title', this.title);

      axios
        .post(`${SERVER_URL}/temp`, frm, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then((response) => {
          alert('등록 완료');
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
    getTemp() {
      const params = new URLSearchParams();
      params.append('email', this.$store.getters.getUserEmail);
      axios
        .get(`${SERVER_URL}/temp/list`, { params })
        .then((response) => {
          this.tempPost = response.data;
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
    completeUpload() {
      if (this.myfile.length + this.imgUrl.length < 2) {
        alert('사진을 두 장 이상 업로드해 주세요!');
        return;
      }
      var frm = new FormData();
      for (var i = 0; i < this.myfile.length; i++) {
        let file = this.myfile[i];
        frm.append('files', file);
      }
      frm.append('postNo', this.postNo);
      frm.append('email', this.$store.getters.getUserEmail);
      frm.append('content', this.content);
      frm.append('title', this.title);
      frm.append('unmodified', this.deleted);
      console.log(this.myfile)
      axios
        .post(`${SERVER_URL}/post`, frm, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then((response) => {
          alert('등록 완료');
          this.$router.push('/board');
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
    deleteForm() {
      axios
        .delete(`${SERVER_URL}/post`, {
          params: {
            // postNo: this.$route.params.no,
            postNo: this.dialogm1,
          },
        })
        .then((response) => {
          this.$router.push('/board');
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
  },
};
</script>
