<template>
  <div>
    <User
      v-for="(user, i) in users"
      :key="i"
      :user="user"
      @deleted="deleteUser(user)"
    />
    <br />
  </div>
</template>

<script>
import axios from 'axios';
import User from '@/components/sub3/user.vue';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  data() {
    return {
      users: [],
    };
  },
  components: {
    User,
  },
  created() {
    const params = new URLSearchParams();
    params.append('email', 'admin');
    axios
      .get(`${SERVER_URL}/admin`, { params })
      .then((response) => {
        this.users = response.data.members;
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    deleteUser(user) {
      var index = this.users.indexOf(user);
      this.users.splice(index, 1);
    },
  },
};
</script>

<style></style>
