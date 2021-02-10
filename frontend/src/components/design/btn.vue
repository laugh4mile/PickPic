<template>
    <v-btn color="secondary" outlined>
        {{title}}
    </v-btn>
</template>
<script>
export default {
    created(){

    },
    props:{
        title:{
            type:String,
        }
    },
}
</script>