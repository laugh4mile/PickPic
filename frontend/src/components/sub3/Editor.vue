<template>
  <div class="container">
    <v-text-field
      label="제목"
      @change="getTitle"
      v-model="contents.title"
      required
      @input="updateValue"
      counter="30"
    ></v-text-field>
    <v-row align="center" class="mb-3">
      <select
        class="border border-secondary rounded-lg mx-3"
        id="select_font"
        v-model="font"
        @change="cng"
      >
        <option :value="item" v-for="(item, index) in fonts" :key="index">
          {{ item }}
        </option>
      </select>
      <button class="mr-2" onclick="document.execCommand('Bold')">
        <i class="fas fa-bold fa-lg fa-border"></i>
      </button>
      <button class="mr-2" onclick="document.execCommand('Italic')">
        <i class="fas fa-italic fa-lg fa-border"></i>
      </button>
      <button class="mr-2" onclick="document.execCommand('Underline')">
        <i class="fas fa-underline fa-lg fa-border"></i>
      </button>
      <button class="mr-2" onclick="document.execCommand('StrikeThrough')">
        <i class="fas fa-strikethrough fa-lg fa-border"></i>
      </button>
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="mr-2" color="primary" dark v-bind="attrs" v-on="on">
            {{ font_size }}px
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
      <button class="mr-2" onclick="document.execCommand('justifyleft')">
        <i class="fas fa-align-left fa-lg fa-border"></i>
      </button>
      <button class="mr-2" onclick="document.execCommand('justifycenter')">
        <i class="fas fa-align-center fa-lg fa-border"></i>
      </button>
      <button class="mr-2" onclick="document.execCommand('justifyright')">
        <i class="fas fa-align-right fa-lg fa-border"></i>
      </button>
      <v-dialog v-model="dialog" width="500">
        <template v-slot:activator="{ on, attrs }">
          <v-btn dark v-bind="attrs" v-on="on"> 링크 </v-btn>
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
            <v-btn color="primary" text @click="dialogComp"> 완료 </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-btn class="ml-2" id="preview" dark @click="preview">Preview</v-btn>
      <v-btn class="ml-2 hidden" id="edit" dark @click="edit">Edit</v-btn>
      <PostImageEditor @edit-img="editImg"/>
    </v-row>

    <div
      style="
        border: 0.2px solid black;
        font-size: 24px;
        height: 400px;
        overflow: auto;
        padding: 10px;
      "
      id="editors"
      @blur="getText"
      @change="getText"
      contenteditable="true"
      label="본문"
      v-html="contents.content"
    >
      <br />
    </div>
    <div
      style="
        border: 0.2px solid black;
        font-size: 12px;
        height: 400px;
        overflow: auto;
      "
      id="previewEditor"
      class="hidden"
      label="본문"
      v-html="sendText"
    ></div>
  </div>
</template>

<script>
var font_size = 24;
var font = 'Arial';

var selection_range;

var clickHandler = function(event) {
  document
    .getElementById('editors')
    .removeEventListener('keypress', clickHandler);
  updateFontSizeForNewText(event);
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
  var key = '';
  if (isValidKeyPress(e)) {
    event.preventDefault();
    key = e.key;
    var span = document.createElement('span');
    span.id = timestamp;
    var txt = document.createTextNode(key);
    span.style.fontSize = font_size + 'px';
    span.style.fontFamily = font;
    span.appendChild(txt);
    var wrap = document.createElement('div');
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
      var el = document.createElement('div');
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
  } else if (document.selection && document.selection.type != 'Control') {
    // IE < 9
    document.selection.createRange().pasteHTML(html);
  }
};

export default {
  created() {
    // this.content = this.contents;
  },
  components: {
    PostImageEditor: () => import('@/components/sub3/ImageEditor.vue'),
  },
  mounted() {
    if (this.contents) {
      this.content = this.contents;
      // $('#editors').append(this.content.content);
      this.getTitle();
      this.sendText = marked($('#editors')[0].innerText + '', {
        sanitize: true,
      });
    }
  },
  data() {
    return {
      H: ['H1', 'H2', 'H3', 'H4', 'H5', 'H6'],
      size: [8, 10, 11, 12, 13, 15, 16, 19, 24, 28, 30, 34, 38],
      videosrcs: [],
      srcs: '',
      temp: '',
      sendText: '',
      font_size: '24',
      dialog: false,
      fonts: [
        'Arial',
        'Sans Serif',
        'Comic Sans MS',
        'Times New Roman',
        'Courier New',
        'Verdana',
        'Trebuchet MS',
        'Arial Black',
        'Impact',
        'Bookman',
        'Garamond',
        'Palatino',
        'Georgia',
      ],
      font: 'Arial',
      content: {
        title: '',
        content: '',
      },
    };
  },
  props: {
    contents: {
      type: Object,
    },
  },
  methods: {
    updateValue(event) {
      console.log(event);
      if(event.length > 30){
        this.contents.title = this.contents.title.substring(0,30);
      }
      // console.log(value, this.amount)
      // if (String(value).length <= 10) {
      //   this.amount = value
      // }
      this.$forceUpdate()
    },
    editImg(data) {
      // console.log(data);
      // console.log('에디터완료');
      this.$emit('edit-img2', data);
    },
    preview() {
      $('#preview').addClass('hidden');
      $('#edit').removeClass('hidden');
      $('#previewEditor').removeClass('hidden');
      $('#editors').addClass('hidden');
    },
    edit() {
      $('#edit').addClass('hidden');
      $('#preview').removeClass('hidden');
      $('#previewEditor').addClass('hidden');
      $('#editors').removeClass('hidden');
    },
    cng() {
      font = this.font;
      this.ColorizeSelection(font_size);
    },
    getTitle() {
      this.$emit('text', this.contents);
    },
    getText(event) {
      this.contents.content = document.getElementById('editors').innerHTML;
      this.$emit('text', this.contents);
      this.update(event);
    },
    update: _.debounce(function(e) {
      this.sendText = marked(e.target.innerText + '', { sanitize: true });
    }, 500),
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
          text[i] != ' ' &&
          text[i] != '\t' &&
          text[i] != '\r' &&
          text[i] != '\n'
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
        if (parentNode.tagName.toLowerCase() == 'span') {
          //parentNode.style.color = color;
          parentNode.style.fontSize = size + 'px';
          return;
        }
      }

      // Create a span element around the node
      var span = document.createElement('span');
      //span.style.color = color;
      span.style.fontSize = size + 'px';
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
        var span = document.createElement('span');
        //span.style.color = color;
        span.style.fontSize = size + 'px';
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
      this.font_size = size;
      if (window.getSelection) {
        // all browsers, except IE before version 9
        var selectionRange = window.getSelection();

        if (selectionRange.isCollapsed) {
          font_size = size;
          document
            .getElementById('editors')
            .addEventListener('keypress', clickHandler);
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
        this.$alert('인터넷 브라우저에서는 지원하지 않는 기능입니다.','','warning');
      }
    },
    dialogComp() {
      this.dialog = false;
      if (
        /^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/|www\.)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/.test(
          this.srcs
        )
      ) {
        this.$alert('적합한 링크입니다.','','success');
        if (this.validateYouTubeUrl(this.srcs)) {
          this.videosrcs.push(this.srcs);
          // document.getElementById('editor').append('<span v-model="videosrcs[' + this.videosrcs.length-1 +']"></span>');

          var iframe = document.createElement('iframe');
          iframe.src = this.videosrcs[this.videosrcs.length - 1];
          iframe.width = 560;
          iframe.height = 315;
          document.getElementById('editors').append(iframe);
          document
            .getElementById('editors')
            .append(document.createElement('br'));
          this.content.content = document.getElementById('editors').innerHTML;
          this.$emit('text', this.content);
        } else {
          var a = document.createElement('a');
          a.href = this.srcs;
          a.innerText = this.srcs;
          document.getElementById('editors').append(a);
          this.content.content = document.getElementById('editors').innerHTML;
          this.$emit('text', this.content);
        }
      } else {
        this.$alert('잘못된 링크입니다.','','warning');
      }
    },
    validateYouTubeUrl(url) {
      if (url != undefined || url != '') {
        var regExp = /^.*(youtube\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=|\?v=)([^#\&\?]*).*/;
        var match = url.match(regExp);
        if (match && match[2].length > 10) {
          // Do anything for being valid
          // if need to change the url to embed url then use below line
          return true;
          // console.log($("#video").attr('src', 'https://www.youtube.com/embed/' + match[2]));
          // this.videosrcs.push('https://www.youtube.com/embed/' + match[2]);
        } else {
          // Do anything for not being valid
          return false;
        }
      }
    },
  },
};
</script>
