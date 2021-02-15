<template>
  <div class="container">
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="3000"
      controls
      indicators
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
            class="d-block myStyle mx-auto"
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
            class="d-block myStyle mx-auto"
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
            class="d-block myStyle mx-auto"
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
            class="d-block myStyle mx-auto"
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
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  name: 'aaa',
  data() {
    return {
      slide: 0,
      sliding: null,
      imgurl: [],
    };
  },
  created() {
    for (var i = 1; i <= 5; i++) {
      axios
        .get(`${SERVER_URL}/post/list/` + i)
        .then((response) => {
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
