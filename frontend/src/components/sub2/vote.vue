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
const SERVER_URL = process.env.VUE_APP_SERVER_URL
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

     axios.get(`${SERVER_URL}/post`, {params})
    .then(response => {
      for(var i=0; i<response.data.fileList.length;i++){
        // this.imgUrl.push(`${SERVER_URL}/post/imgs/download?fileName=` + response.data.fileList[i].modPicName + '&postNo=' + this.no);
        this.imgUrl.push(response.data.fileList[i].modPicName);
      }
      console.log(this.imgUrl);
    })
    .catch(error => {
      alert(error);
    })
  },
};
</script>
