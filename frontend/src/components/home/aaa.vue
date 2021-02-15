<template>
  <div class="container">
    <div class="row">
      <div
        class="col-9 rounded-xl mx-4"
        style="border-style: solid; border-width : 3px; border-color: lightgrey"
      >
        <b-carousel
          id="carousel-1"
          v-model="slide"
          :interval="3000"
          controls
          indicators
          class="rounded-xl"
          background="#ababab"
          img-width="1024px"
          img-height="480px"
          style="text-shadow: 1px 1px 2px #333;"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <b-carousel-slide>
            <template #img>
              <img
                class="d-block myStyle mx-auto rounded-xl"
                width="1024"
                height="480"
                :src="imgurl[0]"
                alt="image slot"
              />
            </template>
          </b-carousel-slide>
          <b-carousel-slide>
            <template #img>
              <img
                class="d-block myStyle mx-auto rounded-xl"
                width="1024"
                height="480"
                :src="imgurl[1]"
                alt="image slot"
              />
            </template>
          </b-carousel-slide>
          <b-carousel-slide>
            <template #img>
              <img
                class="d-block myStyle mx-auto rounded-xl"
                width="1024"
                height="480"
                :src="imgurl[2]"
                alt="image slot"
              />
            </template>
          </b-carousel-slide>
          <b-carousel-slide>
            <template #img>
              <img
                class="d-block myStyle mx-auto rounded-xl"
                width="1024"
                height="480"
                :src="imgurl[3]"
                alt="image slot"
              />
            </template>
          </b-carousel-slide>
          <b-carousel-slide>
            <template #img>
              <img
                class="d-block myStyle mx-auto"
                width="1024"
                height="480"
                :src="imgurl[4]"
                alt="image slot"
              />
            </template>
          </b-carousel-slide>
        </b-carousel>

        <!-- <p class="mt-4">
      Slide #: {{ slide }}<br />
      Sliding: {{ sliding }}
    </p> -->
      </div>
      <div
        class="col-2 rounded-xl ml-4 "
        style="border-style: solid; border-width : 3px; border-color: lightgrey"
      >
        <div>
          <v-row align="center">
            <ol class="ma-5">
              <span class="font-ELAND_Choice_B ma-3" style="font-size:22px;"
                >Best 10</span
              >
              <br />
              <li
                class="my-4 font-ELAND_Choice_B"
                style="font-size:16px; max-width: 50px"
                v-for="(item, i) in board"
                :key="i"
                :value="item"
              >
                {{ item.title }}
              </li>
            </ol>
            <!-- <Board-design v-for="(item, i) in board" :key="i" :value="item" /> -->
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
    };
  },
  created() {
    axios
      .get(`${SERVER_URL}/post/list`)
      .then((response) => {
        // console.log(response);
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
          console.log(response);
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
