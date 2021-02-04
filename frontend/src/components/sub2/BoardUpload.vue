<template>
  <div class="container">
    <v-row align="center">
      <v-text-field label="제목" v-model="title"></v-text-field>

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
    <v-textarea label="본문" v-model="content"></v-textarea>
    <input
      multiple="multiple"
      ref="file"
      type="file"
      id="file"
      name="file"
      @change="onChangeImages"
    />

    <v-btn @click="completeUpload">작성 완료</v-btn>
    <v-btn @click="tempUpload">임시저장</v-btn>
    <p>{{imageUrl}}</p>
    <div class="card">
      <v-row>
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
    };
  },
  methods: {
    deleteImg(index) {
      console.log(index)
      // this.imageUrl.splice(index, 1)
      // this.myfile.splice(index, 1)
      console.log('이미지',this.imageUrl)
      console.log('파일',this.myfile)
    },
    onChangeImages(e) {
      var file = e.target.files;
      for (var i = 0; i < file.length; i++) {
        this.imageUrl.push(URL.createObjectURL(file[i]));
        this.myfile.push(this.$refs.file.files[i])
      }
      // console.log(this.myfile)
      // console.log(this.imageUrl)
    },
    getTempPost() {
      this.dialog = false;
      const params = new URLSearchParams();
      params.append('email', this.$store.getters.getUserEmail);
      params.append('postNo', this.dialogm1);
      axios
        .get(`${SERVER_URL}/post`, { params })
        .then((response) => {
          console.log(response);
          this.postNo = response.data.postInfo.postNo;
          this.title = response.data.postInfo.title;
          this.content = response.data.postInfo.content;
          this.imageUrl = [];
          for (var i = 0; i < response.data.fileList.length; i++) {
            console.log(response.data.fileList[i].modPicName);
            this.imageUrl.push(response.data.fileList[i].modPicName);
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    tempUpload() {
      var frm = new FormData();
      for (var i = 0; i < this.$refs.file.files.length; i++) {
        let file = this.$refs.file.files[i];
        frm.append('files', file);
      }

      console.log(this.postNo);
      if (this.postNo != -1) {
        frm.append('postNo', this.postNo);
      } else {
        frm.append('postNo', -1);
      }
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
          //   this.$router.push("/board");
        })
        .catch((error) => {
          alert(error);
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
          alert(error);
        });
    },
    completeUpload() {
      var frm = new FormData();
      for (var i = 0; i < this.myfile.length; i++) {
        let file = this.myfile[i];
        frm.append('files', file);
      }
      frm.append('postNo', this.postNo);
      frm.append('email', this.$store.getters.getUserEmail);
      frm.append('content', this.content);
      frm.append('title', this.title);
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
          alert(error);
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
          console.log(error);
        });
    },
  },
};
</script>
