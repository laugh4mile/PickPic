<template>
  <v-app oncontextmenu="return false" ondragstart="return false" onselectstart="return false">
    <core-app-bar />

    <core-drawer />

    <core-view />

    <core-footer />
  </v-app>
</template>

<script>
document.oncontextmenu = function (e) {
  return false;
}
document.ondragstart = new Function('return false');
document.onselectstart = new Function('return false');
export default {
  name: 'App',

  components: {
    CoreDrawer: () => import('@/components/core/Drawer'),
    CoreFooter: () => import('@/components/core/Footer'),
    CoreAppBar: () => import('@/components/core/AppBar'),
    CoreView: () => import('@/components/core/View'),
  },
};
</script>
<style>
@font-face {
  font-family: 'yg-jalnan';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_four@1.2/JalnanOTF00.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'ELAND_Choice_M';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/ELAND_Choice_M.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'ELAND_Choice_B';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/ELAND_Choice_B.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
.font-yg-jalnan {
  font-family: 'yg-jalnan';
}
.font-ELAND_Choice_M {
  font-family: 'ELAND_Choice_M';
}
.font-ELAND_Choice_B {
  font-family: 'ELAND_Choice_B';
}
</style>
