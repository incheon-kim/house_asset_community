<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원가입</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left" @submit.prevent="confirm">
            <b-alert show variant="danger" v-if="isJoinError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-alert show variant="danger" v-if="isValid"
              >유효한 값을 입력하세요</b-alert
            >
            <b-form-group label="이름" label-for="userName">
              <b-form-input
                id="userName"
                v-model="user.userName"
                required
                placeholder="이름 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="아이디 (6자 이상)" label-for="userId">
              <b-form-input
                id="userid"
                v-model="user.userId"
                required
                placeholder="아이디 입력...."
                :state="validation.userId"
                @keyup="idCheck"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호" label-for="userPwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userPwd"
                required
                placeholder="비밀번호 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일" label-for="email">
              <b-form-input
                type="text"
                id="email"
                v-model="user.email"
                required
                placeholder="이메일 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="연락처" label-for="phone">
              <b-form-input
                type="tel"
                id="phone"
                v-model="user.phone"
                pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
                required
                placeholder="연락처 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="주소" label-for="address">
              <b-form-input
                type="text"
                id="address"
                v-model="user.address"
                required
                placeholder="주소 입력...."
              ></b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary" class="m-1"
              >회원가입</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "MemberJoin",
  data() {
    return {
      isJoinError: false,
      user: {
        userId: "",
        userPwd: "",
        userName: "",
        email: "",
        phone: "",
        address: "",
      },
      validation: {
        userId: false,
      },
      isValid: false,
    };
  },
  methods: {
    async idCheck() {
      if (this.user.userId.length >= 6) {
        const url = `/api/user/idcheck?userId=${this.user.userId}`;
        try {
          let response = await http.get(url);
          console.log(response.data);
          this.validation.userId = response.data == false;
        } catch (e) {
          console.log(e);
        }
      }
    },
    async confirm() {
      if (Object.values(this.validation).every(Boolean)) {
        this.isValid = true;
        const params = this.user;
        try {
          await http.post("/api/user/join", params);
          this.$router.push({ name: "MemberLogin" });
          alert("회원가입을 완료했습니다. 로그인 창으로 이동합니다.");
        } catch (e) {
          this.isJoinError = true;
        }
      } else {
        this.isValid = true;
      }
    },
  },
};
</script>

<style></style>
