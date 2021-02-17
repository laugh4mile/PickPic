<template>
  <v-dialog v-model="showEditor" transition="dialog-bottom-transition">
    <template v-slot:activator="{ on, attrs }">
      <v-btn class="ml-2" dark v-bind="attrs" v-on="on">
        사진 에디터
      </v-btn>
      <!-- <img :src="imgSrc" alt=""> -->
    </template>
    <v-card>
      <!-- <v-toolbar class="ml-auto">
        <v-btn icon @click="showEditor = false" >
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <v-btn @click="getImg">가져와</v-btn>
        <v-spacer></v-spacer>
      </v-toolbar> -->
      <div class="imageEditorApp">
        <btn @click="getImg" class="btnstyle ">Apply</btn>
        <tui-image-editor ref="editor" :include-ui="useDefaultUI" :options="options"></tui-image-editor>
      </div>
    </v-card>
  </v-dialog>
</template>
<script>

import { ImageEditor } from "@toast-ui/vue-image-editor";
import "tui-image-editor/dist/tui-image-editor.css";
import 'file-saver';
const icona = require("tui-image-editor/dist/svg/icon-a.svg");
const iconb = require("tui-image-editor/dist/svg/icon-b.svg");
const iconc = require("tui-image-editor/dist/svg/icon-c.svg");
const icond = require("tui-image-editor/dist/svg/icon-d.svg");
const logo = require('@/assets/logo.png');
const blackTheme = {
  "menu.normalIcon.path": icond,
  "menu.activeIcon.path": iconb,
  "menu.disabledIcon.path": icona,
  "menu.hoverIcon.path": iconc,
  'submenu.normalIcon.path': icond,
  'submenu.activeIcon.path': iconb,
  'common.bi.image': logo,
  'common.bisize.width': '150px',
  'Common.bisize.height': '40px',
};
var whiteTheme = {
  'common.bi.image': logo,
  // 'common.bi.image': 'https://uicdn.toast.com/toastui/img/tui-image-editor-bi.png',
  'common.bi.image.display': 'none',
  'common.bisize.width': '150px',
  'common.bisize.height': '40px',
  'common.backgroundImage': './img/bg.png',
  'common.backgroundColor': '#fff',
  // 'common.border': '1px solid #c1c1c1',

  // header
  'header.backgroundImage': 'none',
  'header.backgroundColor': 'transparent',
  'header.border': '0px',

  // load button
  'loadButton.backgroundColor': '#fff',
  'loadButton.border': '1px solid #ddd',
  'loadButton.color': '#222',
  'loadButton.fontFamily': "'Noto Sans', sans-serif",
  'loadButton.fontSize': '12px',

  // download button
  'downloadButton.backgroundColor': '#8ecae6',
  'downloadButton.border': '1px solid #8ecae6',
  'downloadButton.color': '#fff',
  'downloadButton.fontFamily': "'Noto Sans', sans-serif",
  'downloadButton.fontSize': '12px',
  // 'downloadButton.position': 'absolute',

  // main icons
  'menu.normalIcon.color': '#8a8a8a',
  'menu.activeIcon.color': '#555555',
  'menu.disabledIcon.color': '#434343',
  'menu.hoverIcon.color': '#e9e9e9',
  'menu.iconSize.width': '24px',
  'menu.iconSize.height': '24px',

  // submenu icons
  'submenu.normalIcon.color': '#8a8a8a',
  'submenu.activeIcon.color': '#555555',
  'submenu.iconSize.width': '32px',
  'submenu.iconSize.height': '32px',

  // submenu primary color
  // 'submenu.backgroundColor': 'transparent',
  'submenu.partition.color': '#e5e5e5',

  // submenu labels
  'submenu.normalLabel.color': '#858585',
  'submenu.normalLabel.fontWeight': 'normal',
  'submenu.activeLabel.color': '#000',
  'submenu.activeLabel.fontWeight': 'normal',

  // checkbox style
  'checkbox.border': '1px solid #ccc',
  'checkbox.backgroundColor': '#fff',

  // rango style
  'range.pointer.color': '#333',
  'range.bar.color': '#ccc',
  'range.subbar.color': '#606060',

  'range.disabledPointer.color': '#d3d3d3',
  'range.disabledBar.color': 'rgba(85,85,85,0.06)',
  'range.disabledSubbar.color': 'rgba(51,51,51,0.2)',

  'range.value.color': '#000',
  'range.value.fontWeight': 'normal',
  'range.value.fontSize': '11px',
  'range.value.border': '0',
  'range.value.backgroundColor': '#f5f5f5',
  'range.title.color': '#000',
  'range.title.fontWeight': 'lighter',

  // colorpicker style
  'colorpicker.button.border': '0px',
  'colorpicker.title.color': '#000',
};
export default {
  name: "PostImageEditor",
  components: {
    "tui-image-editor": ImageEditor
  },
  data() {
    return {
      fileImg:'',
      imgSrc:'',
      blobImg: '',
      temp: '',
      tempname: '',
      myfile: [],
      showEditor: false,
      useDefaultUI: true,
      options: {
        cssMaxWidth: window.innerWidth,
        cssHeight: 800,
        selectionStyle: {
          cornerSize: 20,
          rotatingPointOffset: 70,
        },
        includeUI: {
          theme: whiteTheme
        },
        initMenu: "filter",
      }
    };
  },
  methods: {
    blobToFile(theBlob, fileName){
    //A Blob() is almost a File() - it's just missing the two properties below which we will add
    theBlob.lastModifiedDate = new Date();
    theBlob.name = fileName;
    return new File([theBlob], fileName);
    },
    dataURItoBlob() {
    // convert base64 to raw binary data held in a string
    // doesn't handle URLEncoded DataURIs - see SO answer #6850276 for code that does this
    var byteString = atob(this.temp.split(',')[1]);

    // separate out the mime component
    var mimeString = this.temp.split(',')[0].split(':')[1].split(';')[0];

    // write the bytes of the string to an ArrayBuffer
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    //Old Code
    //write the ArrayBuffer to a blob, and you're done
    //var bb = new BlobBuilder();
    //bb.append(ab);
    //return bb.getBlob(mimeString);

    //New Code
    this.blobImg = new Blob([ab], {type: mimeString});
    console.log(this.blobImg)
    this.imgSrc = URL.createObjectURL(this.blobImg);
    this.showEditor = false
    console.log(this.imgSrc,this.tempname)

    this.fileImg = this.blobToFile(this.blobImg,this.tempname)
    const editImg = {
        file: this.fileImg,
        imgsrc: this.imgSrc,
        // name: this.tempname
      }
    console.log(editImg)
      // editImg.file['name'] = this.tempname
      this.$emit('edit-img', editImg);


},
getImg() {
      // console.log(this.$refs.editor.invoke('toDataURL'))
      this.temp=this.$refs.editor.invoke('toDataURL')
      this.tempname=this.$refs.editor.invoke('getImageName')
      this.dataURItoBlob()
    },
  },
  watch: {

},
// computed : {
//     dataUrl(){
//       return 'data:image/jpeg;base64m' + btoa(
//         new Uint8Array(this.blobImg)
//         .reduce((data, byte) => data + String.fromCharCode(byte), '')
//       );
//     }

// }
};
</script>
<style scoped>
.imageEditorApp {
  width: 100%;
  height: calc(100vh - 150px);
}
.btnstyle {
  position: absolute;
  z-index: 9;
  right: 257px;
  top: 8px;
  background-color: #8ecae6;
  color: #fff;
  font-family: 'Noto Sans', sans-serif;
  font-size: 12px;

  width: 120px;
  height: 40px;
  padding: 0;
  line-height: 40px;
  outline: none;
  border-radius: 20px;
  font-family: 'Noto Sans', sans-serif;
  font-size: 12px;
  font-weight: bold;
  cursor: pointer;
  vertical-align: middle;
  letter-spacing: 0.3px;
  text-align: center;
}
</style>