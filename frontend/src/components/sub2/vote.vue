<template>
  <div>
    <v-radio-group>
      <v-row>
        <label v-for="(item, index) in imgUrl" :key="index">
          <div class="image-container" :class="{ 'redd' : index == selected }">
            <img :src="item.modPicName"/>
          </div>
            <v-radio @click="selectImg(item,index)"
            style="margin-left:94px"
            />
        </label>
      </v-row>
    </v-radio-group>
    <v-btn @click="vote">투표하기</v-btn>
    <hr />
    <poll v-bind="options" />
  </div>
</template>
<script>
import axios from "axios";
import Poll from "@/components/sub3/VoteResult.vue";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      imgUrl: [],
      item: [],
      selected: "",
      options: {
        question: "What's your favourite?",
        answers: [
          // { value: 1, text: "Vue", votes: 53 },
        ],
      },
    };
  },
  props: {
    no: {
      type: Number,
    },
  },
  components: {
    Poll,
  },
  created() {
    const params = new URLSearchParams();
    params.append("email", this.$store.getters.getUserEmail);
    params.append("postNo", this.no);

    axios
      .get(`${SERVER_URL}/post`, { params })
      .then((response) => {
        // console.log(response.data);
        for (var i = 0; i < response.data.fileList.length; i++) {
          // this.imgUrl.push(`${SERVER_URL}/post/imgs/download?fileName=` + response.data.fileList[i].modPicName + '&postNo=' + this.no);
          let file = response.data.fileList[i];
          this.imgUrl.push(file);
          this.options.answers.push({
            value: i + 1,
              text: i + 1 + " 번째",
            votes: file.voteCnt,
          });
        }
        console.log(this.imgUrl);
        console.log(this.options);
      })
      .catch((error) => {
        alert(error);
      });
  },
  methods: {
    vote() {
      console.log("투표", this.item);
      const params = new URLSearchParams();
      params.append("email", this.$store.getters.getUserEmail);
      params.append("postNo", this.item.postNo);
      params.append("picNo", this.item.picNo);
      axios
        .post(`${SERVER_URL}/post/vote`, params)
        .then((response) => {
          console.log(response.data);
          this.options.answers = [];
          for (var i = 0; i < response.data.fileList.length; i++) {
            let file = response.data.fileList[i];
            this.options.answers.push({
              value: i + 1,
              text: i + 1 + " 번째",
              votes: file.voteCnt,
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    selectImg(item,idx){
      this.item = item
      this.selected = idx
      console.log(this.selected)
    }
  }
};
</script>
<style scoped>
.image-container{
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 200px;
  height: 200px;
  margin-right: 10px;
}
img {
  width: 100%;
  height: 100%; 
}

/* .v-radio {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
} */

/* IMAGE STYLES */
img + div.v-radio {
  cursor: pointer;
}

/* CHECKED STYLES */
.redd {
  outline: 2px solid #f00;
}
</style>
