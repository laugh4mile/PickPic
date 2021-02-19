<template>
  <v-navigation-drawer v-model="drawer" app dark temporary>
    <v-list>
      <v-list-item
        v-for="(link, i) in links"
        :key="i"
        @click="onClick($event, link)"
      >
        <v-list-item-title v-text="link.text" />
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
// Utilities
import { mapGetters, mapMutations } from 'vuex';

export default {
  name: 'CoreDrawer',

  computed: {
    ...mapGetters(['links']),
    drawer: {
      get() {
        return this.$store.state.drawer;
      },
      set(val) {
        this.setDrawer(val);
      },
    },
  },

  methods: {
    ...mapMutations(['setDrawer']),
    onClick(e, item) {
      this.$router.push(item.href).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          // 같은 게시판 눌렀을때 새로고침되게
          // location.reload();
          // 그냥 사이드바만 닫게
          this.$store.state.drawer = false;
        }
      });
      // e.stopPropagation()

      // if (item.to === '/') {
      //   this.$vuetify.goTo(0)
      //   this.setDrawer(false)
      //   return
      // }

      // if (item.to || !item.href) return
      // // this.$vuetify.goTo(item.href)
      // this.setDrawer(false)
    },
  },
};
</script>
