<template>
  <v-dialog v-model="showEditor" persistent transition="dialog-bottom-transition">
    <template v-slot:activator="{ on, attrs }">
      <v-btn class="ml-2" dark v-bind="attrs" v-on="on">
        사진 에디터
      </v-btn>
    </template>
    <v-card>
      <v-toolbar dark>
        <v-btn icon dark @click="showEditor = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <v-spacer></v-spacer>
      </v-toolbar>
      <div class="imageEditorApp">
        <tui-image-editor ref="editor" :include-ui="useDefaultUI" :options="options"></tui-image-editor>
      </div>
    </v-card>
  </v-dialog>
</template>
<script>
import { ImageEditor } from "@toast-ui/vue-image-editor";
var FileSaver = require('file-saver');
const icona = require("tui-image-editor/dist/svg/icon-a.svg");
const iconb = require("tui-image-editor/dist/svg/icon-b.svg");
const iconc = require("tui-image-editor/dist/svg/icon-c.svg");
const icond = require("tui-image-editor/dist/svg/icon-d.svg");
const blackTheme = {
  "menu.normalIcon.path": icond,
  "menu.activeIcon.path": iconb,
  "menu.disabledIcon.path": icona,
  "menu.hoverIcon.path": iconc
};
export default {
  name: "PostImageEditor",
  components: {
    "tui-image-editor": ImageEditor
  },
  data() {
    return {
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
          theme: blackTheme
        },
        initMenu: "filter",
        FileSaver
      }
    };
  },
  methods: {
  }
};
</script>
<style scoped>
.imageEditorApp {
  width: 100%;
  height: calc(100vh - 150px);
}
</style>