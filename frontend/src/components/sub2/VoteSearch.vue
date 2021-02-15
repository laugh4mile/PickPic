<template>
  <div class="wrap">
    <div id="app" 
      v-on:keyup.down="selectValue('down')"
      v-on:keyup.up="selectValue('up')">
      <div class="search">
        <v-text-field class="s" label="투표를 검색하세요" v-model="searchQuery" autocomplete="on"></v-text-field>
        <ul class="r" tabindex="0" 
          v-bind:class="{ show: isActive }">
          <li tabindex="-1" 
            v-for="(el, index) in filterList" 
            :key="index"
            v-on:click="changeValue(el.title)"
            v-on:keyup.enter="selectValue('enter', el.title)">
            <span>{{ el.title }}</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VoteSearch',
  data: function () {
    return {
      isActive: false,
      searchQuery: '',
      filterList: [],
      noserching: false,
    }
  },
  props: {
    boards: Array, 
  },
  methods: {
    changeValue(str) {
      this.isActive = false;
      this.searchQuery = str;
    },
    selectValue(keycode, str) {
      if (this.isActive === true) {
        const hasClass = document.querySelector('.r').classList.contains('key');
        if (keycode === 'down') {
          if (!hasClass) {
            const thisEl = document.querySelectorAll('.r li')[0];
            document.querySelector('.r').classList.add('key');
            thisEl.classList.add('sel');
            thisEl.focus();
          } else {
            const lastEl = document.querySelector('.r li:last-child');
            const thisEl = document.querySelector('.r li.sel');
            const nextEl = thisEl.nextElementSibling;
            if (!lastEl.classList.contains('sel')) {
              thisEl.classList.remove('sel');
              nextEl.classList.add('sel');
              nextEl.focus();
            }
          }
        }
        if (keycode === 'up' && hasClass) {
          const firstEl = document.querySelectorAll('.r li')[0];
          const thisEl = document.querySelector('.r li.sel');
          const prevEl = thisEl.previousElementSibling;
          if (!firstEl.classList.contains('sel')) {
            thisEl.classList.remove('sel');
            prevEl.classList.add('sel');
            prevEl.focus();
          } else {
            document.querySelector('.s').focus();
          }
        }
        if (keycode === 'enter' && hasClass) {
          this.changeValue(str);
        }
      }
    },
    removeValue() {
      if (document.querySelector('.r').classList.contains('key')) {
        document.querySelector('.r').classList.remove('key');
        document.querySelector('.r li.sel').classList.remove('sel');
      }
    },
  },
  watch: {
    searchQuery: function () {
      if (this.searchQuery == ''){
        this.filterList = this.boards
      }
      const str = this.searchQuery;
      const reg = /[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9|\s]/.test(str);
      if (reg === false && str !== '' && str !== ' ') {
        this.isActive = true;
        this.filterList =  this.boards.filter((el) => {
          return el.title.match(str);
        });
      } else {
        this.isActive = false;
      }
    },
    filterList: function () {
      if (this.searchQuery == ''){
        this.$emit('no-searching')
      } else {
        this.$emit('filter-vote',this.filterList)
      }
    }
  },
}
</script>

<style scoped>
html, body {
  height: 100%;
}
body {
  background-color: #ddd;
  font-size: 14px;
  color: #333;
}
.strong {
  font-weight: bold;
}
.wrap {
  display: table;
  padding: 0px 20px;
  padding-bottom: 100px;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
#app {
  display: table-cell;
  vertical-align: middle;
  text-align: center;
}
.search {
  position: relative;
  margin: 0 auto;
  width: 100%;
  max-width: 600px;
}
.s {
  padding: 10px 20px;
  width: 100%;
  max-width: 600px;
  height: 60px;
  box-sizing: border-box;
  box-shadow: 0 0 3px rgba(#000, 0.2);
  /* border: 1px solid #888; */
  font-size: 16px;
}
.r {
  padding: 0px;
  display: none;
  position: absolute;
  left: 0;
  top: 60px;
  width: 100%;
  height: 99px;
  overflow-y: auto;
}
.show {
  display: block;
}
li {
  margin-top: -1px;
  padding: 0 20px;
  width: 100%;
  height: 40px;
  background-color: #fff;
  /* box-sizing: border-box; */
  /* border: 1px solid #888; */
  outline: none;
  font-size: 16px;
  line-height: 40px;
  cursor: pointer;
  list-style: none;
  display: flex;
  justify-content: left;
}
.sel {
  background-color: #dad8d8;
}
p {
  padding: 10px 0;
  text-align: right;
  font-size: 12px;
}
</style>