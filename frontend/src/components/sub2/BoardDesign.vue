<template>
  <div class="card mr-5 mb-5" @click="goDetail">
    <div class="card-body">
      <v-col cols="12">
        <img :src="require('./logo.png')" height="100px" />
        <div class="text-center">
          <label>title : {{ value.title }}</label>
        </div>
        <div></div>
      </v-col>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    value: {
      type: Object,
      default: () => ({}),
    },
  },
  methods: {
    goDetail() {
      this.$router.push('/board/' + this.value.postNo);
    },
  },
};
</script>
