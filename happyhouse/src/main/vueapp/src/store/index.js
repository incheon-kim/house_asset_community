import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http-common.js";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    //////////////////////// board
    articles: [],
    //////////////////////// board
    notices: [],
    //////////////////////// house
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    umds: [{ value: null, text: "선택하세요" }],
    houses: [],
    simple_houses: [],
    house: null,

    //////////////////////// member
    user: {
      userId: "",
      userName: "",
      userNo: 0,
      isAuth: false,
      role: 0,
    },
  },
  mutations: {
    //////////////////////// board
    SET_BOARD_LIST(state, articles) {
      articles.forEach((article) => {
        state.articles.push({ article });
      });
    },
    CLEAR_BOARD_LIST(state) {
      state.articles = [];
    },
    REGIST_BOARD_LIST(state, article) {
      state.articles.push(article);
    },
    GET_ARTICLE_DATA(state, article) {
      state.article = article;
    },
    SET_ARTICLE_DATA(state, article) {
      state.article = article;
    },
    //////////////////////// notice
    UPDATE_ROLE_STATUS(state, role) {
      state.user.role = role;
    },
    SET_NOTICE_LIST(state, articles) {
      articles.forEach((article) => {
        state.notices.push({ article });
      });
    },
    CLEAR_NOTICE_LIST(state) {
      state.notices = [];
    },
    REGIST_NOTICE_LIST(state, article) {
      state.notices.push(article);
    },
    //////////////////////// house
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_UMD_LIST(state, umds) {
      umds.forEach((umd) => {
        state.umds.push({ value: umd.umdCode, text: umd.umdName });
      });
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_UMD_LIST(state) {
      state.umds = [{ value: null, text: "선택하세요" }];
    },
    SET_HOUSE_LIST(state, houses) {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE(state, house) {
      state.house = house;
    },
    CLEAR_HOUSE_LIST(state) {
      state.houses = [];
    },

    //////////////////////// member
    UPDATE_LOGIN_STATUS(state, user) {
      state.user.userId = user.userId;
      state.user.userName = user.userName;
      state.user.userNo = user.userNo;
      state.user.isAuth = user.isAuth;
      state.user.role = user.role;
    },
  },
  actions: {
    //////////////////////// board
    getBoardLists({ commit }) {
      http
        .get("/board/list")
        .then((response) => {
          console.log(response.data);
          commit("SET_BOARD_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async registArticle({ commit }, article) {
      const params = {
        subject: article.subject,
        userid: article.userid,
        content: article.content,
      };
      console.log(params);
      try {
        let response = await http.post("/board/write", params);
        console.log(response);
        commit("REGIST_BOARD_LIST", article);
      } catch (err) {
        console.log(err);
      }
    },
    getArticle({ commit }, articleno) {
      console.log("here getArticle");
      const url = "/board/" + articleno;
      http
        .get(url)
        .then((response) => {
          console.log(response.data);
          commit("SET_CURRENT_ARTICLE", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
      console.log(this.state.article);
    },
    /////////////////////// notice
    async getNoticeLists({ commit }) {
      try {
        let response = await http.get("/notice/list");
        console.log("noticeList", response);
        commit("SET_NOTICE_LIST", response.data);
      } catch (err) {
        console.log(err);
      }
    },
    async registNotice({ commit }, article) {
      const params = {
        subject: article.subject,
        userid: article.userid,
        content: article.content,
      };
      try {
        let response = await http.post("/notice/write", params);
        console.log(response);
        commit("REGIST_NOTICE_LIST", article);
      } catch (err) {
        console.log(err);
      }
    },
    getNotice({ commit }, articleno) {
      console.log("here getNotice");
      const url = "/notice/" + articleno;
      http
        .get(url)
        .then((response) => {
          console.log(response.data);
          commit("SET_CURRENT_ARTICLE", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
      console.log(this.state.article);
    },
    /////////////////////// util
    async getRoleStatus({ commit }) {
      try {
        let response = await http.get("/api/user/role");
        console.log("role update", response);
        commit("UPDATE_ROLE_STATUS", response.data);
      } catch (err) {
        console.log(err);
      }
    },
    /////////////////////// house
    async getSido({ commit }) {
      try {
        let response = await http.get("/map/sido");
        commit("SET_SIDO_LIST", response.data);
      } catch (err) {
        console.log(err);
      }
    },
    getGugun({ commit }, sidoCode) {
      const params = { sido: sidoCode };
      http
        .get(`/map/gugun`, { params })
        .then((response) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getUmd({ commit }, params) {
      console.log(params);
      http
        .get(`/map/umd`, { params })
        .then((response) => {
          commit("SET_UMD_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async getHouseList({ commit }, params) {
      try {
        let response = await http.get("/map/find/address", { params });
        console.log(response);
        commit("SET_HOUSE_LIST", response.data);
      } catch (err) {
        console.log(err);
      }
    },
    async getHouseListByName({ commit }, params) {
      try {
        let response = await http.get("/map/find/name", { params });
        console.log(response);
        commit("SET_HOUSE_LIST", response.data);
      } catch (err) {
        console.log(err);
      }
    },
    detailHouse({ commit }, house) {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
  },
  getters: {
    async userRole() {
      let response = await http.get("/user/role");
      return response.data;
    },
  },
  modules: {},
  plugins: [createPersistedState()],
});
