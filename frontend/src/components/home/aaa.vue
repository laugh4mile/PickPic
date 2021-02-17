<template>
  <div class="container">
    <div class="row">
      <div
        class="col-xs-8 col-sm-8 col-md-8 rounded-xl mx-4 my-3"
        style="border-style: solid; border-width : 3px; border-color: lightgrey"
      >
        <b-carousel
          id="carousel-1"
          v-model="slide"
          :interval="3000"
          controls
          indicators
          class="rounded-xl"
          img-width="1024px"
          img-height="480px"
          style="text-shadow: 1px 1px 2px #333; background: rgba(0, 0, 0, 0);"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <b-carousel-slide v-for="(n, i) in num" :key="i" :value="n">
            <template #img>
              <img
                class="d-block myStyle mx-auto rounded-xl"
                max-width="100%"
                height="480"
                :src="imgurl[n]"
                style="cursor:pointer;"
                alt="image slot"
                @click="go(postno[n])"
              />
            </template>
          </b-carousel-slide>
        </b-carousel>
      </div>
      <div
        class="col-xs-8 col-sm-8 col-md-3 rounded-xl mx-4 my-3 "
        style="border-style: solid; border-width : 3px; border-color: lightgrey"
      >
        <div>
          <v-row align="center">
            <ol class="ma-5">
              <span
                class="font-ELAND_Choice_B ma-3"
                style="font-size:22px; align:center;"
                >Best 10</span
              >
              <br />
              <li
                class="my-4 font-ELAND_Choice_B"
                style="font-size:16px; max-width: 100%; cursor:pointer"
                v-for="(item, i) in board"
                :key="i"
                :value="item"
                @click="go(item.postNo)"
              >
                {{ item.title }}
              </li>
            </ol>
          </v-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import BoardDesign from '../sub2/BoardDesign.vue';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  components: {
    BoardDesign,
  },
  name: 'aaa',
  data() {
    return {
      slide: 0,
      sliding: null,
      imgurl: [],
      boards: [],
      board: [],
      num: [0, 1, 2, 3, 4],
      postno: [],
    };
  },
  created() {
    const params = new URLSearchParams();
    params.append('sortBy', 'like');
    axios
      .get(`${SERVER_URL}/post/list`, { params })
      .then((response) => {
        console.log(response);
        this.boards = response.data;
        this.board = this.boards;
      })
      .catch((error) => {
        alert(error);
        this.$router.push('/Error');
      });
    for (var i = 1; i <= 5; i++) {
      axios
        .get(`${SERVER_URL}/post/list/` + i)
        .then((response) => {
          this.postno.push(response.data.postNo);
          this.imgurl.push(
            'https://apfbucket.s3.ap-northeast-2.amazonaws.com/' +
              response.data.thumbnail
          );
        })
        .catch((error) => {
          alert(error);
        });
    }
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
    go(postNo) {
      this.$router.push('/board/' + postNo);
    }
  },
};
</script>

<style>
.blog .carousel-indicators {
  left: 0;
  top: auto;
  bottom: -40px;
}

/* The colour of the indicators */
.blog .carousel-indicators li {
  background: #a3a3a3;
  border-radius: 50%;
  width: 8px;
  height: 8px;
}

.blog .carousel-indicators .active {
  background: #707070;
}

.myStyle {
  height: 480px;
  width: auto;
}
</style>
