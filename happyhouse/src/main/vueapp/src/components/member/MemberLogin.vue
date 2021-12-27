<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>로그인</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userid"
                required
                placeholder="아이디 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="tryLogin"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="tryLogin"
              >로그인</b-button
            >
            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="movePage"
              >회원가입</b-button
            >
          </b-form>
          <router-link :to="{ name: 'MemberPwd' }"
            ><div class="text-left">비밀번호 찾기</div></router-link
          >
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
import { mapMutations } from "vuex";

export default {
  name: "MemberLogin",
  data() {
    return {
      isLoginError: false,
      user: {
        userid: "",
        userpwd: "",
      },
    };
  },
  methods: {
    ...mapMutations(["UPDATE_LOGIN_STATUS"]),
    async tryLogin() {
      const params = {
        userId: this.user.userid,
        userPwd: this.user.userpwd,
      };
      console.log(params);
      try {
        let response = await http.post("/api/user/login", params);
        response.data.isAuth = true;
        console.log(response.data);
        this.UPDATE_LOGIN_STATUS(response.data);
        this.$router.push({ name: "Home" });
      } catch (e) {
        alert("로그인 실패");
      }
    },
    movePage() {
      this.$router.push({ name: "MemberJoin" });
    },
  },
};
</script>

<style></style>
