<template>
  <div>
    <b-navbar id="navBar" toggleable="lg" type="info" variant="warning">
      <b-navbar-brand href="#">
        <router-link to="/">
          <img
            src="@/assets/ssafy_logo.png"
            class="d-inline-block align-middle"
            width="90px"
            alt="Kitten"
          />
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Home' }" class="link"
              ><b-icon icon="house" font-scale="1"></b-icon> HOME</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Board' }" class="link"
              ><b-icon icon="journal" font-scale="1"></b-icon>
              게시판</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Notice' }" class="link"
              ><b-icon icon="instagram" font-scale="1"></b-icon>
              공지사항</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'House' }" class="link"
              ><b-icon icon="house-fill" font-scale="1"></b-icon>
              아파트정보</router-link
            ></b-nav-item
          >
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown right v-if="!user.isAuth">
            <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'MemberJoin' }" class="link"
                ><b-icon icon="person-circle"></b-icon> 회원가입</router-link
              ></b-dropdown-item
            >
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'MemberLogin' }" class="link"
                ><b-icon icon="key"></b-icon> 로그인</router-link
              ></b-dropdown-item
            >
          </b-nav-item-dropdown>
          <b-nav-item-dropdown right v-if="user.isAuth">
            <template #button-content>
              <b>{{ user.userName }} 님 ({{ user.userId }})</b>
            </template>
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'MemberModify' }" class="link"
                ><b-icon icon="person-circle"></b-icon> 회원 정보 수정
              </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#" @click="logout"
              ><b-icon icon="key"></b-icon> 로그아웃</b-dropdown-item
            >
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapMutations, mapState } from "vuex";
export default {
  name: "NaviBar",
  computed: {
    ...mapState(["user"]),
  },
  methods: {
    ...mapMutations(["UPDATE_LOGIN_STATUS"]),
    async logout() {
      console.log("logout");
      try {
        await http.post("/api/user/logout");
      } catch (e) {
        alert("로그아웃 실패!");
      } finally {
        this.UPDATE_LOGIN_STATUS({
          userId: "",
          userName: "",
          userNo: 0,
          isAuth: false,
          role: 0,
        });

        if (this.$router.history.current.name != "Home") {
          this.$router.push({ name: "Home" });
        }
      }
    },
  },
};
</script>

<style scoped></style>
