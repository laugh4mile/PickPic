<template>
  <div>
    <!-- awal dropdown-->
    <div class="container" style="width:100%; ">
      <div
        class="well col-xs-8 col-sm-8 col-md-8 col-lg-8 col-xs-offset-2 col-sm-offset-2 col-md-offset-2 col-lg-offset-2"
      >
        <div class="row user-row" >
          <div class="col-xs-3 col-sm-2 col-md-1 col-lg-1">
            <img
              class="img img-responsive user-photo"
              :src="imgSrc"
              alt="User Pic"
            />
          </div>
          <div class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
            <strong>{{ user.name }}</strong><br />
            <span class="text-muted">User level: {{ user.role}}</span>
          </div>
          <div
            class="col-xs-1 col-sm-1 col-md-1 col-lg-1 dropdown-user"
            data-for="cyruxx"
          >
            <i class="glyphicon glyphicon-chevron-down text-muted" @click="isShow = !isShow"></i>
          </div>
        </div>
        <div class="row user-infos cyruxx" :class="{hidden: !isShow, 'botao-lista-baixo': !isShow, 'botao-lista-cima': !isShow}">
          <div
            class="col-xs-12 col-sm-12 col-md-10 col-lg-10 col-xs-offset-0 col-sm-offset-0 col-md-offset-1 col-lg-offset-1"
          >
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title">User information</h3>
                <div class="pull-right">
                  <button
                    class="btn btn-sm btn-danger"
                    type="button"
                    data-toggle="tooltip"
                    data-original-title="Remove this user"
                    @click="deleteUser"
                  >
                    회원삭제
                  </button>
                </div>
              </div>
              <div class="panel-body">
                <div class="row">
                  <div class="col-md-3 col-lg-3 hidden-xs hidden-sm aaa">
                    <img
                      class="img img-responsive user-photo"
                      :src="imgSrc"
                      alt="User Pic"
                    />
                  </div>
                  <!-- <div class="col-xs-2 col-sm-2 hidden-md hidden-lg">
                    <img
                      class="img img-responsive user-photo"
                      :src="imgSrc"
                      alt="User Pic"
                    />
                  </div> -->
                  <!-- <div class="col-xs-10 col-sm-10 hidden-md hidden-lg">
                    <strong>{{ user.name }}</strong><br />
                    <dl>
                      <dt>User level:</dt>
                      <dd>{{ user.role }}</dd>
                      <dt>Email:</dt>
                      <dd>{{ user.email }}</dd>
                      <dt>Registered since:</dt>
                      <dd>{{ user.joinDate }}</dd>
                      <dt>Introduce:</dt>
                      <dd>{{ user.introduce }}</dd>
                      <dt>PostCnt:</dt>
                      <dd>{{ user.postCnt }}</dd>
                      <dt>CommentCnt:</dt>
                      <dd>{{ user.commentCnt }}</dd>
                    </dl>
                  </div> -->
                  <div class=" col-md-9 col-lg-9 hidden-xs hidden-sm">
                    <strong>{{ user.name }}</strong><br />
                    <table class="table table-user-information">
                      <tbody>
                        <tr>
                          <td>User level:</td>
                          <td>{{ user.role }}</td>
                        </tr>
                        <tr>
                          <td>Email:</td>
                          <td>{{ user.email }}</td>
                        </tr>
                        <tr>
                          <td>Registered since:</td>
                          <td>{{ user.joinDate }}</td>
                        </tr>
                        <tr>
                          <td>Introduce:</td>
                          <td>{{ user.introduce }}</td>
                        </tr>
                        <tr>
                          <td>PostCnt:</td>
                          <td>{{ user.postCnt }}</td>
                        </tr>
                        <tr>
                          <td>CommentCnt:</td>
                          <td>{{ user.commentCnt }}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      isShow: false,
    };
  },
  methods: {
    deleteUser () {
      axios
        .delete(`${SERVER_URL}/member`, {
          data: {
            email: this.user.email,
          },
        })
        .then((response) => {
          alert('회원을 삭제했습니다.')
          this.$emit('deleted',this.user)
        })
        .catch((error) => {
          this.$router.push("/Error");
        });
    },
  },
  props: {
    user: {
      type: Object,
      default: () => ({}),
    },
  },
  computed: {
    imgSrc: function() {
      const src =
        'https://apfbucket.s3.ap-northeast-2.amazonaws.com/' +
        this.user.profileImg;
      return src;
    },
  },
};
</script>

<style>
#topo {
  z-index: 15;
  background-color: #fff;
  width: 100%;
  position: fixed;
  border-bottom: 2px solid #16618d;
  opacity: 0.9;
}
#logo-topo img {
  margin: 2.8% 0 4% 0;
}
#menu-topo {
  border-top: 1px solid #1c79ad;
  text-align: center;
  padding: 0;
}
#menu-topo li {
  width: 100%;
  list-style: none;
}
#menu-topo li a {
  display: block !important;
  width: 100%;
  font-size: 1.5em;
  padding: 1% 0;
  font-weight: bold;
  color: #2a6496;
}
#menu-topo li a:hover {
  background-color: #d6d6dc;
  text-decoration: none;
}
.btn-collapse {
  width: 30px;
  height: 30px;
  position: relative;
  padding: 0.5% 1%;
  float: right;
  margin-top: 3.5%;
}
.container {
	padding: 0;
}
.well {
	margin-bottom: 0;
}
.lista-collapse {
  width: 100%;
  height: 5px;
  background-color: #1c79ad;
  -ms-transition: all 0.5s;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  transition: all 0.5s;
  position: absolute;
}
.lista-collapse:nth-child(1) {
  top: 0;
}
.lista-collapse:nth-child(2) {
  top: 14px;
}
.lista-collapse:nth-child(1) {
  top: 25px;
}

.botao-lista-cima {
  -ms-transform: rotate(-50deg);
  -webkit-transform: rotate(-50deg);
  -moz-transform: rotate(-50deg);
  transform: rotate(-50deg);
}
.botao-lista-baixo {
  -ms-transform: rotate(50deg);
  -webkit-transform: rotate(50deg);
  -moz-transform: rotate(50deg);
  transform: rotate(50deg);
}
.hidden {
  visibility: hidden;
}
.lista-collapse.botao-lista-cima {
  top: 5px;
}
.lista-collapse.botao-lista-baixo {
  top: 5px;
}

<!-- awal dropdown-- > .user-row {
  /* margin-bottom: 14px; */
  padding-top: 100px;
}
.row {
	margin: 0;
}
.user-row:last-child {
  margin-bottom: 0;
}

.dropdown-user {
  margin: 13px 0;
  padding: 5px;
  height: 100%;
}

.dropdown-user:hover {
  cursor: pointer;
}

.table-user-information > tbody > tr {
  border-top: 1px solid rgb(221, 221, 221);
}

.table-user-information > tbody > tr:first-child {
  border-top: 0;
}

.table-user-information > tbody > tr > td {
  border-top: 0;
}
</style>
