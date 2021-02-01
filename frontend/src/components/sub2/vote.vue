<template>
  <div>
        <v-radio-group>
            <v-row>
                <span v-for="(item, index) in imgUrl" :key="index">
                        <img :src="item" width="300px" height="300px"/>
                            <v-radio
                            style="margin-left:150px"
                            />
                </span>
            </v-row>
        </v-radio-group>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  data() {
    return {
      imgUrl : [],
    };
  },
  props:{
    no:{
      type:Number 
    }
  },
  created(){
    const params = new URLSearchParams()
    params.append('email', this.$store.getters.getUserEmail)
    params.append('postNo', this.no);

     axios.get('http://localhost:3000/sub/post', {params})
    .then(response => {
      for(var i=0; i<response.data.fileList.length;i++){
        this.imgUrl.push('http://localhost:3000/sub/post/imgs/download?fileName=' + response.data.fileList[i].modPicName + '&postNo=' + this.no);
      }
      console.log(this.imgUrl);
    })
    .catch(error => {
      alert(error);
    })
  },
};
</script>
