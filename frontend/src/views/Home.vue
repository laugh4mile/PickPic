<template>
  <div id="home">
    <aaa />
    <articles>
    </articles>

    <!-- <about /> -->

    <!-- <subscribe /> -->

    <!-- <social /> -->
  </div>
</template>

<script>
  export default {
    name: 'Home',

    components: {
      About: () => import('@/components/home/About'),
      Articles: () => import('@/components/home/Articles'),
      aaa: () => import('@/components/home/aaa'),
      Social: () => import('@/components/home/Social'),
      Subscribe: () => import('@/components/home/Subscribe'),
    },
  }
</script>
