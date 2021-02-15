<template>
  <div class="container font-ELAND_Choice_B">
    <div>
      <v-row align="center">
        <VoteSearch
          class="mt-10"
          @filter-vote="filterVote"
          @no-searching="noSearching"
          :boards="boards"
        />

        <!-- <v-text-field label="제목 검색" v-model="search"></v-text-field> -->
        <!-- <v-btn class="ml-2" @click="searchBoard">검색</v-btn> -->
        <div class="col-sm-12">
          <v-btn
            color="secondary"
            outlined
            @click="uploadForm"
            class="btn-outline-info rounded-pill mb-10 pull-right"
            >글쓰기</v-btn
          >
        </div>
      </v-row>
    </div>
    <div>
      <v-row>
        <Board-design v-for="(item, i) in board" :key="i" :value="item" />
      </v-row>
    </div>
    <infinite-loading
      v-if="!searching"
      @infinite="infiniteHandler"
      spinner="waveDots"
    >
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
import BoardDesign from './BoardDesign.vue';
import axios from 'axios';
import InfiniteLoading from 'vue-infinite-loading';
import VoteSearch from '@/components/sub2/VoteSearch.vue';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  components: {
    BoardDesign,
    InfiniteLoading,
    VoteSearch,
  },
  created() {

    axios
      .get(`${SERVER_URL}/post/list`)
      .then((response) => {
        this.boards = response.data;
        this.board = this.boards;
      })
      .catch((error) => {
        this.$router.push({path: '/Error', query: {'status' : error.response.status}});
      });
  },
  methods: {
    noSearching: function() {
      this.searching = false;
      this.board = this.boards;
    },
    filterVote: function(res) {
      this.board = res;
      this.searching = true;
    },
    infiniteHandler($state) {
      const params = new URLSearchParams();
      params.append('pg', this.limit);
      axios
        .get(`${SERVER_URL}/post/list`, { params })
        .then((response) => {
          setTimeout(() => {
            if (response.data.length) {
              this.board = this.board.concat(response.data);
              this.boards = this.boards.concat(response.data);
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
          this.$router.push({path: '/Error', query: {'status' : error.response.status}});
        });
    },
    searchBoard() {
      if (this.search) {
        this.searching = true;
      }
      this.board = [];
      for (let i = 0; i < this.boards.length; i++) {
        if (this.boards[i].title.includes(this.search)) {
          this.board.push(this.boards[i]);
        }
      }
    },
    uploadForm() {
      this.$router.push('/board/upload');
    },
  },
  data() {
    return {
      boards: [],
      board: [],
      search: '',
      limit: 2,
      searching: false,
    };
  },
};
</script>
