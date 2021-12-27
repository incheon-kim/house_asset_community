import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import House from "@/views/House.vue";
import Member from "@/views/Member.vue";
import MemberLogin from "@/components/member/MemberLogin.vue";
import MemberJoin from "@/components/member/MemberJoin.vue";
import MemberModify from "@/components/member/MemberModify.vue";
import MemberPwd from "@/components/member/MemberPwd.vue";

import Board from "@/views/Board.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";
import BoardView from "@/components/board/BoardView.vue";
import BoardUpdate from "@/components/board/BoardUpdate.vue";
import BoardDelete from "@/components/board/BoardDelete.vue";

import Notice from "@/views/Notice.vue";
import NoticeList from "@/components/notice/NoticeList.vue";
import NoticeWrite from "@/components/notice/NoticeWrite.vue";
import NoticeView from "@/components/notice/NoticeView.vue";
import NoticeUpdate from "@/components/notice/NoticeUpdate.vue";
import NoticeDelete from "@/components/notice/NoticeDelete.vue";

import store from "@/store";

Vue.use(VueRouter);

//const loginStatus = store.state.

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/house",
    name: "House",
    component: House,
  },
  {
    path: "/user",
    name: "Member",
    component: Member,
    children: [
      {
        path: "login",
        name: "MemberLogin",
        component: MemberLogin,
        beforeEnter: (to, from, next) => {
          console.log(store.state.user.isAuth);
          if (!store.state.user.isAuth) next();
          else next("Home");
        },
      },
      {
        path: "join",
        name: "MemberJoin",
        component: MemberJoin,
      },
      {
        path: "modify",
        name: "MemberModify",
        component: MemberModify,
      },
      {
        path: "findpwd",
        name: "MemberPwd",
        component: MemberPwd,
      },
    ],
  },
  {
    path: "/board",
    name: "Board",
    component: Board,
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "BoardList",
        component: BoardList,
      },
      {
        path: "", //"write",
        name: "BoardWrite",
        component: BoardWrite,
      },
      {
        path: "", //"detail/:articleno",
        name: "BoardView",
        component: BoardView,
      },
      {
        path: "", //"update/:articleno",
        name: "BoardUpdate",
        component: BoardUpdate,
      },
      {
        path: "", //"delete/:articleno",
        name: "BoardDelete",
        component: BoardDelete,
      },
    ],
  },
  {
    path: "/notice",
    name: "Notice",
    component: Notice,
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "NoticeList",
        component: NoticeList,
      },
      {
        path: "", //"write",
        name: "NoticeWrite",
        component: NoticeWrite,
      },
      {
        path: "", //"detail/:articleno",
        name: "NoticeView",
        component: NoticeView,
      },
      {
        path: "", //"update/:articleno",
        name: "NoticeUpdate",
        component: NoticeUpdate,
      },
      {
        path: "", //"delete/:articleno",
        name: "NoticeDelete",
        component: NoticeDelete,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
