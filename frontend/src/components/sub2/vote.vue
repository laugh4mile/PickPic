<template>
  <div>
    <v-radio-group>
      <v-row>
        <span v-for="(item, index) in imgUrl" :key="index">
          <img :src="item.modPicName" width="300px" height="300px" />
          <v-radio @click="selectImg(item)" style="margin-left:150px" />
        </span>
      </v-row>
    </v-radio-group>
    <v-btn @click="vote">투표하기</v-btn>
    <hr />
  </div>
</template>
<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      imgUrl: [],
      item: [],
    };
  },
  props: {
    no: {
      type: Number,
    },
  },
  created() {
    const params = new URLSearchParams();
    params.append('email', this.$store.getters.getUserEmail);
    params.append('postNo', this.no);

    axios
      .get(`${SERVER_URL}/post`, { params })
      .then((response) => {
        for (var i = 0; i < response.data.fileList.length; i++) {
          // this.imgUrl.push(`${SERVER_URL}/post/imgs/download?fileName=` + response.data.fileList[i].modPicName + '&postNo=' + this.no);
          this.imgUrl.push(response.data.fileList[i]);
        }
        console.log(this.imgUrl);
      })
      .catch((error) => {
        alert(error);
      });
  },
  methods: {
    vote() {
      console.log('투표', this.item);
      const params = new URLSearchParams();
      params.append('email', this.$store.getters.getUserEmail);
      params.append('postNo', this.item.postNo);
      params.append('picNo', this.item.picNo);
      axios
        .post(`${SERVER_URL}/post/vote`, params)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    selectImg(item) {
      console.log(item);
      this.item = item;
    },
  },
};
</script>
