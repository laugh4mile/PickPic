<template>
  <div class="col-md-3 col-sm-6 ma-0.5" @click="goDetail">
    <div class="product-grid6 h-100 border rounded-xl">
      <div class="product-image6 h-70" style="position: relative;">
        <img
          class="pic-1"
          :src="imgSrc1"
          onerror="this.src=`https://apfbucket.s3.ap-northeast-2.amazonaws.com/c8c25cb23bdd4aa9a5c4608b7fa243ef.png`"
          alt="이미지"
        />
        <img
          class="pic-2"
          :src="imgSrc2"
          onerror="this.src=`https://apfbucket.s3.ap-northeast-2.amazonaws.com/c8c25cb23bdd4aa9a5c4608b7fa243ef.png`"
          alt="이미지"
        />
        <div
          style="top:10px; right:10px; font-size: 1.2em; font-weight: bold; position: absolute; sans-serif;"
        >
          <i class="fas fa-heart fa-1x" style="color:red;"></i>
          {{ value.likeCnt }}
          <br />
          <i class="fa fa-commenting-o" aria-hidden="true"></i>
          {{ value.commentCnt }}
        </div>
      </div>
      <div class="product-content">
        <span class="title-style font-yg-jalnan">
          {{ value.title }}
        </span>
        <div class="price">
          <span class="font-ELAND_Choice_B">{{ value.name }}</span>
        </div>
      </div>
      <ul class="social">
        <li @click="goDetail">
          <a href="" data-tip="투표하기"><i class="fa fa-search"></i></a>
        </li>
        <li>
          <a href="" data-tip="결과보기"><i class="fas fa-poll fa-1x"></i></a>
        </li>
        <!-- <li>
          <a href="" data-tip="Add to Cart"
            ><i class="fa fa-shopping-cart"></i
          ></a>
        </li> -->
      </ul>
    </div>
  </div>
  <!-- <div class="card mr-5 mb-5" @click="goDetail">
        <div class="card-body">

            <v-col cols="12">
                    <img
                        :src=imgSrc
                        height="100px"
                    />
                    <div class="text-center">
                        <label>title : {{value.title}}</label></div>
                    <div>
                </div>
                
            </v-col>
        </div>
    </div> -->
</template>
<script>
export default {
  data() {
    return {};
  },
  created() {
    // console.log(this.value);
  },
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
  computed: {
    imgSrc1: function() {
      const src =
        'https://apfbucket.s3.ap-northeast-2.amazonaws.com/' +
        this.value.thumbnail;
      return src;
    },
    imgSrc2: function() {
      const src =
        'https://apfbucket.s3.ap-northeast-2.amazonaws.com/' +
        this.value.thumbnailTwo;
      return src;
    },
  },
};
</script>
<style scoped>
h3.h3 {
  text-align: center;
  margin: 1em;
  text-transform: capitalize;
  font-size: 1.7em;
}
.product-grid6,
.product-grid6 .product-image6 {
  overflow: hidden;
}
.product-grid6 {
  font-family: 'Open Sans', sans-serif;
  text-align: center;
  position: relative;
  transition: all 0.5s ease 0s;
}
.product-image6 {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 250px;
}
.product-grid6:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}
.product-grid6 .product-image6 a {
  display: block;
}
.product-grid6 .product-image6 img {
  width: 100%;
  height: 100%;
  transition: all 0.5s ease 0s;
}
.product-grid6 .pic-1 {
  opacity: 1;
  transition: all 0.5s ease-out 0s;
}
.product-grid6 .pic-2 {
  opacity: 0;
  transform: rotateY(-90deg);
  position: absolute;
  top: 0;
  left: 0;
  transition: all 0.5s ease-out 0s;
}
.product-grid6:hover .pic-2 {
  opacity: 1;
  transform: rotateY(0);
}
.product-grid6 .product-content {
  padding: 12px 12px 15px;
  transition: all 0.5s ease 0s;
}
.product-grid6:hover .product-content {
  opacity: 0;
}
.product-grid6 .title {
  font-size: 18px;
  font-weight: 600;
  text-transform: capitalize;
  margin: 0 0 0rem;
  transition: all 0.3s ease 0s;
}
.product-grid6 .title a {
  color: #000;
}
.product-grid6 .title a:hover {
  color: #2e86de;
}
.product-grid6 .price {
  font-size: 18px;
  font-weight: 600;
  color: black;
}
.product-grid6 .price span {
  color: rgb(71, 69, 69);
  font-size: 13px;
  font-weight: 400;
  /* text-decoration: line-through; */
  /* margin-left: 7px; */
  display: inline-block;
}
.product-grid6 .social {
  background-color: #fff;
  width: 100%;
  padding: 0;
  margin: 0;
  list-style: none;
  opacity: 0;
  transform: translateX(-50%);
  position: absolute;
  bottom: -50%;
  left: 50%;
  z-index: 1;
  transition: all 0.5s ease 0s;
}
.product-grid6:hover .social {
  opacity: 1;
  bottom: 20px;
}
.product-grid6 .social li {
  display: inline-block;
}
.product-grid6 .social li a {
  color: #909090;
  font-size: 16px;
  line-height: 45px;
  text-align: center;
  height: 45px;
  width: 45px;
  margin: 0 7px;
  border: 1px solid #909090;
  border-radius: 50px;
  display: block;
  position: relative;
  transition: all 0.3s ease-in-out;
}
.product-grid6 .social li a:hover {
  color: #fff;
  background-color: #2e86de;
  width: 80px;
}
.product-grid6 .social li a:after,
.product-grid6 .social li a:before {
  content: attr(data-tip);
  color: #fff;
  background-color: #2e86de;
  font-size: 12px;
  letter-spacing: 1px;
  line-height: 20px;
  padding: 1px 5px;
  border-radius: 5px;
  white-space: nowrap;
  opacity: 0;
  transform: translateX(-50%);
  position: absolute;
  left: 50%;
  top: -30px;
}
.product-grid6 .social li a:after {
  content: '';
  height: 15px;
  width: 15px;
  border-radius: 0;
  transform: translateX(-50%) rotate(45deg);
  top: -20px;
  z-index: -1;
}
.product-grid6 .social li a:hover:after,
.product-grid6 .social li a:hover:before {
  opacity: 1;
}
.title-style {
  font-size: 18px;
  /* font-weight: 700; */
  color: #23232f;
}
</style>
