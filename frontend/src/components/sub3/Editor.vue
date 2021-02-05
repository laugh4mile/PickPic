<template>
  <div class="container">
    <head> </head>
    <!-- <v-row align="center"> -->

    <v-text-field label="제목" @change="getTitle" v-model="content.title"></v-text-field>

    <!-- <div class="text-center">
              <v-dialog
      v-model="dialog"
      scrollable
      max-width="290"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="primary"
          dark
          v-bind="attrs"
          v-on="on"
          @click="getTemp"
        >
          임시저장 목록
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline">
          임시저장 목록
        </v-card-title>
                <v-card-text style="height: 300px;">
          <v-radio-group
            v-model="dialogm1"
            column
          >
            <v-radio
            v-for="(item, index) in tempPost" :key="index"
              :label="item.title"
              :value="item.postNo"
            />
          </v-radio-group>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="dialog = false"
          >
            Disagree
          </v-btn>
          <v-btn
            color="green darken-1"
            text
            @click="getTempPost"
          >
            Agree
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
          </div>
      </v-row>
    <hr /> -->
    <v-row align="center" class="mb-3">
      <select id="select_font" v-model="font" @change="cng">
        <option value="Arial">Arial</option>
        <option value="Sans Serif" selected>Sans Serif</option>
        <option value="Comic Sans MS">Comic Sans MS</option>
        <option value="Times New Roman">Times New Roman</option>
        <option value="Courier New">Courier New</option>
        <option value="Verdana">Verdana</option>
        <option value="Trebuchet MS">Trebuchet MS</option>
        <option value="Arial Black">Arial Black</option>
        <option value="Impact">Impact</option>
        <option value="Bookman">Bookman</option>
        <option value="Garamond">Garamond</option>
        <option value="Palatino">Palatino</option>
        <option value="Georgia">Georgia</option>
      </select>
      <img
        width="20px"
        src="@/assets/icon/bold.svg"
        alt=""
        onclick="document.execCommand('bold')"
      />
      <img
        width="20px"
        src="@/assets/icon/italic.svg"
        alt=""
        onclick="document.execCommand('Italic')"
      />
      <!-- <img width="20px" src="@/assets/icon/c.png" alt="" onclick="document.execCommand('Code')"> -->
      <img
        width="20px"
        src="@/assets/icon/underline.png"
        onclick="document.execCommand('Underline')"
        alt=""
      />
      <!-- <img
        width="40px"
        src="@/assets/icon/strikethrough.svg"
        onclick="document.execCommand('StrikeThrough')"
        alt=""
      /> -->
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="primary" dark v-bind="attrs" v-on="on">
            12px
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(item, index) in size"
            :key="index"
            @click="ColorizeSelection(item)"
          >
            <v-list-item-title>{{ item }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <img width="30px" src="@/assets/icon/align-left.png" alt="" onclick="document.execCommand('justifyleft')">
      <img width="30px" src="@/assets/icon/align-center.png" alt="" onclick="document.execCommand('justifycenter')">
      <img width="30px" src="@/assets/icon/align-right.png" alt="" onclick="document.execCommand('justifyright')">
      <v-dialog
      v-model="dialog"
      width="500"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="red lighten-2"
          dark
          v-bind="attrs"
          v-on="on"
        >
          링크
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-2">
          Privacy Policy
        </v-card-title>

        <v-card-text>
          <v-text-field v-model="srcs" label="링크 입력"></v-text-field>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="dialogComp"
          >
            완료
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    </v-row>

    <div
      style="border: .2px solid black; font-size: 12px;"
      id="editors"
      @input="getText"
      @change="getText"
      contenteditable="true"
      label="본문"
    >
    <!-- <video src="https://tv.naver.com/v/18161441"></video> -->
    <!-- https://youtu.be/70ip9Ug8DHs -->
    <br>
      <!-- <iframe v-for="(item, index) in videosrcs" :key="index" id="video" :src="item" frameborder="0"></iframe> -->
    </div>
    <br />
  </div>
</template>

<script>
var font_size = 14;
var selection_range;


var clickHandler = function(event) {
  document
    .getElementById("editors")
    .removeEventListener("keypress", clickHandler);
  updateFontSizeForNewText(event);
  updateFontStyleForNewText(event);
};

var isValidKeyPress = function(e) {
  var keycode = e.keyCode;
  var valid =
    (keycode > 47 && keycode < 58) || // number keys
    (keycode > 64 && keycode < 91) || // letter keys
    (keycode > 95 && keycode < 112) || // numpad keys
    (keycode > 185 && keycode < 193) || // ;=,-./` (in order)
    (keycode > 218 && keycode < 223); // [\]' (in order)
  return valid;
};

var updateFontSizeForNewText = function(e) {
  var timestamp = new Date().getUTCMilliseconds();
  var key = "";
  if (isValidKeyPress(e)) {
    event.preventDefault();
    key = e.key;
    var span = document.createElement("span");
    span.id = timestamp;
    var txt = document.createTextNode(key);
    span.style.fontSize = font_size + "px";
    span.appendChild(txt);
    var wrap = document.createElement("div");
    wrap.appendChild(span.cloneNode(true));
    pasteHtmlAtCaret(wrap.innerHTML);
  }
};

var updateFontStyleForNewText = function(e) {
  var timestamp = new Date().getUTCMilliseconds();
  var key = "";
  if (isValidKeyPress(e)) {
    event.preventDefault();
    key = e.key;
    var span = document.createElement("span");
    span.id = timestamp;
    var txt = document.createTextNode(key);
    span.style.fontFamily = this.font;
    span.appendChild(txt);
    var wrap = document.createElement("div");
    wrap.appendChild(span.cloneNode(true));
    pasteHtmlAtCaret(wrap.innerHTML);
  }
};

var pasteHtmlAtCaret = function(html) {
  var sel, range;
  if (window.getSelection) {
    // IE9 and non-IE
    sel = window.getSelection();
    if (sel.getRangeAt && sel.rangeCount) {
      range = sel.getRangeAt(0);
      range.deleteContents();

      // Range.createContextualFragment() would be useful here but is
      // non-standard and not supported in all browsers (IE9, for one)
      var el = document.createElement("div");
      el.innerHTML = html;
      var frag = document.createDocumentFragment(),
        node,
        lastNode;
      while ((node = el.firstChild)) {
        lastNode = frag.appendChild(node);
      }
      range.insertNode(frag);

      // Preserve the selection
      if (lastNode) {
        range = range.cloneRange();
        range.setStartAfter(lastNode);
        range.collapse(lastNode);
        sel.removeAllRanges();
        sel.addRange(range);
      }
    }
  } else if (document.selection && document.selection.type != "Control") {
    // IE < 9
    document.selection.createRange().pasteHTML(html);
  }
};

export default {
  // created(){
  //   this.temp = this.contents;
  //   console.log("temp : " + this.temp);
  //   // this.content = this.contents;
  //   // console.log(this.content);
  //   $("#editor").innerHTML = this.contents;
  // },
  mounted(){
    console.log('mounted')
    if(this.contents){
      console.log(this.contents);
      this.content = this.contents;
      $("#editors").append(this.contents.content);
      console.log(this.temp);
    }
  },
  data() {
    return {
      H: ["H1", "H2", "H3", "H4", "H5", "H6"],
      size: [8, 10, 11, 13, 15, 16, 19, 24, 28, 30, 34, 38],
      videosrcs:[],
      srcs:'',
      temp:'',
      dialog:false,
      fonts: [
        "Arial",
        "Sans Serif",
        "Comic Sans MS",
        "Times New Roman",
        "Courier New",
        "Verdana",
        "Trebuchet MS",
        "Arial Black",
        "Impact",
        "Bookman",
        "Garamond",
        "Palatino",
        "Georgia",
      ],
      font: "Arial",
      content: {
        title:'',
        content:'',
      },
    };
  },
  props:{
    contents:{
      type:Object
    }
  },
  methods: {
    cng() {
      this.changeFont();
    },
    getTitle(){
      this.$emit('text', this.content);
    },
    getText(){
      this.content.content = document.getElementById('editors').innerHTML;
      this.$emit('text', this.content);
    },
    
    GetNextLeaf(node) {
      while (!node.nextSibling) {
        node = node.parentNode;
        if (!node) {
          return node;
        }
      }
      var leaf = node.nextSibling;
      while (leaf.firstChild) {
        leaf = leaf.firstChild;
      }
      return leaf;
    },

    GetPreviousLeaf(node) {
      while (!node.previousSibling) {
        node = node.parentNode;
        if (!node) {
          return node;
        }
      }
      var leaf = node.previousSibling;
      while (leaf.lastChild) {
        leaf = leaf.lastChild;
      }
      return leaf;
    },

    // If the text content of an element contains white-spaces only, then does not need to colorize
    IsTextVisible(text) {
      for (var i = 0; i < text.length; i++) {
        if (
          text[i] != " " &&
          text[i] != "\t" &&
          text[i] != "\r" &&
          text[i] != "\n"
        )
          return true;
      }
      return false;
    },

    ColorizeLeaf(node, size) {
      if (!this.IsTextVisible(node.textContent)) return;

      var parentNode = node.parentNode;
      // if the node does not have siblings and the parent is a span element, then modify its color
      if (!node.previousSibling && !node.nextSibling) {
        if (parentNode.tagName.toLowerCase() == "span") {
          //parentNode.style.color = color;
          parentNode.style.fontSize = size + "px";
          return;
        }
      }

      // Create a span element around the node
      var span = document.createElement("span");
      //span.style.color = color;
      span.style.fontSize = size + "px";
      var nextSibling = node.nextSibling;

      parentNode.removeChild(node);
      span.appendChild(node);
      parentNode.insertBefore(span, nextSibling);
    },

    ColorizeLeafFromTo(node, size, from, to) {
      var text = node.textContent;
      if (!this.IsTextVisible(text)) return;

      if (from < 0) from = 0;
      if (to < 0) to = text.length;

      if (from == 0 && to >= text.length) {
        // to avoid unnecessary span elements
        this.ColorizeLeaf(node, size);
        return;
      }

      var part1 = text.substring(0, from);
      var part2 = text.substring(from, to);
      var part3 = text.substring(to, text.length);

      var parentNode = node.parentNode;
      var nextSibling = node.nextSibling;

      parentNode.removeChild(node);
      if (part1.length > 0) {
        var textNode = document.createTextNode(part1);
        parentNode.insertBefore(textNode, nextSibling);
      }
      if (part2.length > 0) {
        var span = document.createElement("span");
        //span.style.color = color;
        span.style.fontSize = size + "px";
        var textNode = document.createTextNode(part2);
        span.appendChild(textNode);
        parentNode.insertBefore(span, nextSibling);
      }
      if (part3.length > 0) {
        var textNode = document.createTextNode(part3);
        parentNode.insertBefore(textNode, nextSibling);
      }
    },

    ColorizeNode(node, size) {
      var childNode = node.firstChild;
      if (!childNode) {
        this.ColorizeLeaf(node, size);
        return;
      }

      while (childNode) {
        // store the next sibling of the childNode, because colorizing modifies the DOM structure
        var nextSibling = childNode.nextSibling;
        this.ColorizeNode(childNode, size);
        childNode = nextSibling;
      }
    },

    ColorizeNodeFromTo(node, size, from, to) {
      var childNode = node.firstChild;
      if (!childNode) {
        this.ColorizeLeafFromTo(node, size, from, to);
        return;
      }

      for (var i = from; i < to; i++) {
        this.ColorizeNode(node.childNodes[i], size);
      }
    },

    ColorizeSelection(size) {
      if (window.getSelection) {
        // all browsers, except IE before version 9
        var selectionRange = window.getSelection();

        if (selectionRange.isCollapsed) {
          font_size = size;
          console.log("Collapsed");
          document
            .getElementById("editors")
            .addEventListener("keypress", clickHandler);
          selection_range = selectionRange;
        } else {
          var range = selectionRange.getRangeAt(0);
          // store the start and end points of the current selection, because the selection will be removed
          var startContainer = range.startContainer;
          var startOffset = range.startOffset;
          var endContainer = range.endContainer;
          var endOffset = range.endOffset;
          // because of Opera, we need to remove the selection before modifying the DOM hierarchy
          selectionRange.removeAllRanges();

          if (startContainer == endContainer) {
            this.ColorizeNodeFromTo(
              startContainer,
              size,
              startOffset,
              endOffset
            );
          } else {
            if (startContainer.firstChild) {
              var startLeaf = startContainer.childNodes[startOffset];
            } else {
              var startLeaf = this.GetNextLeaf(startContainer);
              this.ColorizeLeafFromTo(startContainer, size, startOffset, -1);
            }

            if (endContainer.firstChild) {
              if (endOffset > 0) {
                var endLeaf = endContainer.childNodes[endOffset - 1];
              } else {
                var endLeaf = this.GetPreviousLeaf(endContainer);
              }
            } else {
              var endLeaf = this.GetPreviousLeaf(endContainer);
              this.ColorizeLeafFromTo(endContainer, size, 0, endOffset);
            }

            while (startLeaf) {
              var nextLeaf = this.GetNextLeaf(startLeaf);
              this.ColorizeLeaf(startLeaf, size);
              if (startLeaf == endLeaf) {
                break;
              }
              startLeaf = nextLeaf;
            }
          }
        }
      } else {
        // Internet Explorer before version 9
        alert("Your browser does not support this example!");
      }
    },
    dialogComp(){
      this.dialog = false;
      if(/^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/|www\.)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/.test(this.srcs)){
        alert("valid url");
          if(this.validateYouTubeUrl(this.srcs)){
            console.log("들어옴");
            this.videosrcs.push(this.srcs);
            console.log("this.videosrcs[" + this.videosrcs.length + "]");
            // document.getElementById('editor').append('<span v-model="videosrcs[' + this.videosrcs.length-1 +']"></span>');

            var iframe = document.createElement("iframe");
            iframe.src = this.videosrcs[this.videosrcs.length-1]
            iframe.width =  560;
            iframe.height = 315;
            document.getElementById('editors').append(iframe);
            document.getElementById('editors').append(document.createElement('br'));
            this.content.content = document.getElementById('editors').innerHTML;
            console.log('url',this.content.content);
            this.$emit('text', this.content);
          }else{
            console.log('안들어옴');
            var a = document.createElement("a");
            a.href = this.srcs;
            a.innerText = this.srcs;
            console.log(a);
            document.getElementById('editors').append(a);
            this.content.content = document.getElementById('editors').innerHTML;
            console.log('url',this.content.content);
            this.$emit('text', this.content);
          }
      } else {
          alert("invalid url");
      }
    },
    linkbtn(){

      document.getElementById('editor').append('<span>asd</span>');
      document.getElementById('editor').append('<iframe v-for="(item, index) in videosrcs" :key="index" id="video" :src="item" frameborder="0"></iframe>');
    },
    execFontSize(size, unit) {
      // var spanString = $("<span/>", {
      //   text: document.getSelection(),
      // })

      var e = document.createElement("span");
      if (document.getSelection()) {
        e.innerHTML = document.getSelection().toString();
      } else {
        console.log("asd");
      }
      e.style = "font-size:" + size + unit + ";";

      var range = document.getSelection().getRangeAt(0);
      range.deleteContents(); // Deletes selected text…
      range.insertNode(e);

      // document.execCommand("insertHTML", false, spanString);
    },

    setSelected(item) {
      this.execFontSize(item, "px");
    },
    changeFont() {
      console.log(this.font);
      var sel = window.getSelection(); // Gets selection
      if (sel.rangeCount) {
        // Creates a new element, and insert the selected text with the chosen font inside
        var e = document.createElement("span");
        e.style = "font-family:" + this.font.toString() + ";";
        e.innerHTML = sel.toString();
        console.log(e);
        // https://developer.mozilla.org/en-US/docs/Web/API/Selection/getRangeAt
        var range = sel.getRangeAt(0);
        range.deleteContents(); // Deletes selected text…
        range.insertNode(e); // … and inserts the new element at its place
      }
    },
    // validateYouTubeUrl(url)
    // {
    //   var urls = document.getElementById('editor').innerText;
    //   // console.log(urls.split('\n'));
    //   var temp = urls.split('\n');
    //   console.log(temp);
    //   for(var i=0; i < temp.length; i++){
    //     var url = temp[i]
    //     // console.log(urls.split('\n')[i]);
    //         if (url != undefined || url != '') {
    //             var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=|\?v=)([^#\&\?]*).*/;
    //             var match = url.match(regExp);
    //             console.log(match);
    //             if (match && match[2].length > 10) {
    //                 // Do anything for being valid
    //                 // if need to change the url to embed url then use below line
                    
    //                 console.log($("#video").attr('src', 'https://www.youtube.com/embed/' + match[2]));
    //                 this.videosrcs[i] = 'https://www.youtube.com/embed/' + match[2];
    //                 console.log(this.videosrcs.length);
    //                 // this.videosrcs.push('https://www.youtube.com/embed/' + match[2]);
    //             }
    //             else {
    //                 // Do anything for not being valid
    //             }
    //         }
    //   }
    // },
    validateYouTubeUrl(url)
    {
      console.log("match");
            if (url != undefined || url != '') {
                var regExp = /^.*(youtube\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=|\?v=)([^#\&\?]*).*/;
                var match = url.match(regExp);
                if (match && match[2].length > 10) {
                    // Do anything for being valid
                    // if need to change the url to embed url then use below line
                    return true;
                    // console.log($("#video").attr('src', 'https://www.youtube.com/embed/' + match[2]));
                    // this.videosrcs.push('https://www.youtube.com/embed/' + match[2]);
                }
                else {
                    // Do anything for not being valid
                    return false;
                }
            }
    },
  },
  
};
</script>
