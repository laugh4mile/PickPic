import Vue from 'vue';
import Router from 'vue-router';
import Home from '../views/Home.vue';
import SearchPwd from '../components/sub1/searchPwd.vue';
import regist from '../components/sub1/regist.vue';
import myPage from '../components/sub1/myPage.vue';
import notFound from '../components/sub1/notFound.vue';
import Error from '../components/sub1/Error.vue';
import Board from '../components/sub2/Board.vue';
import BoardDetail from '../components/sub2/BoardDetail.vue';
import BoardUpdate from '../components/sub2/BoardUpdate.vue';
import BoardUpload from '../components/sub2/BoardUpload.vue';
import temp from '../components/sub2/temp.vue';
import editor from '../components/sub3/Editor.vue';
import admin from '../components/sub3/admin.vue';
Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/tt',
      name: 'temp',
      component: temp,
    },
    {
      path: '/board/upload',
      name: 'boardUpload',
      component: BoardUpload,
    },
    {
      path: '/board/update',
      name: 'boardUpdate',
      component: BoardUpdate,
    },
    {
      path: '/board/:no',
      name: 'boardDetail',
      component: BoardDetail,
    },
    {
      path: '/board',
      name: 'board',
      component: Board,
    },
    {
      path: '/Error',
      name: 'Error',
      component: Error,
    },
    {
      path: '/404',
      name: 'notFound',
      component: notFound,
    },
    {
      path: '*',
      redirect: '/404',
      // 아래처럼 바로 NotFound 경로를 매칭해도 됨
      // component: NotFound
    },
    {
      path: '/searchPwd',
      name: 'searchPwd',
      component: SearchPwd,
    },
    {
      path: '/regist',
      name: 'regist',
      component: regist,
    },
    {
      path: '/myPage',
      name: 'myPage',
      component: myPage,
    },
    {
      path: '/admin',
      name: 'admin',
      component: admin,
    },
  ],
});
