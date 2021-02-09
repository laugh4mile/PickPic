<template>
  <div class="container">
    <div>
      <v-row align="center">
        <v-btn @click="uploadForm" class="mr-2">작성</v-btn>
        <v-text-field label="제목 검색" v-model="search"></v-text-field>
        <v-btn class="ml-2" @click="searchBoard">검색</v-btn>
      </v-row>
    </div>
    <div>
      <v-row>
        <Board-design v-for="(item, i) in board" :key="i" :value="item" />
      </v-row>
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;"
      >
        목록의 끝입니다 :)
      </div>
    </infinite-loading>
  </div>
</template>
<script>
import BoardDesign from "./BoardDesign.vue";
import axios from "axios";
import InfiniteLoading from "vue-infinite-loading";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  components: {
    BoardDesign,
    InfiniteLoading,
  },
  created() {
    console.log(process.env.VUE_APP_SERVER_URL);
    axios
      .get(`${SERVER_URL}/post/list`)
      .then((response) => {
        console.log(response);
        this.boards = response.data;
        this.board = this.boards;
      })
      .catch((error) => {
        alert(error);
        this.$router.push("/Error");
      });
  },
  methods: {
    infiniteHandler($state) {
      const params = new URLSearchParams();
      params.append("pg", this.limit);
      axios
        .get(`${SERVER_URL}/post/list`, { params })
        .then((response) => {
          setTimeout(() => {
            if (response.data.length) {
              this.board = this.board.concat(response.data);
              $state.loaded();
              this.limit += 1;
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면
              if (!response.data) {
                $state.complete();
              }
            } else {
              // 끝 지정(No more data)
              $state.complete();
            }
          }, 1000);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    searchBoard() {
      this.board = [];
      for (let i = 0; i < this.boards.length; i++) {
        if (this.boards[i].title.includes(this.search)) {
          this.board.push(this.boards[i]);
        }
      }
    },
    uploadForm() {
      this.$router.push("/board/upload");
    },
  },
  data() {
    return {
      boards: [],
      board: [],
      search: "",
      limit: 2,
    };
  },
};
</script>
