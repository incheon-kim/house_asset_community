<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원정보 수정</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left" @submit.prevent="tryModify">
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
            <b-form-group label="아이디" label-for="userId">
              <b-form-input
                id="userid"
                :placeholder="user.userId"
                disabled
              ></b-form-input>
            </b-form-group>
            <b-form-group label="현재 비밀번호" label-for="userPwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userPwd"
                required
                placeholder="현재 비밀번호 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="새 비밀번호" label-for="newPwd">
              <b-form-input
                type="password"
                id="newpwd"
                v-model="newPwd"
                required
                placeholder="새 비밀번호 입력...."
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
              >정보 수정</b-button
            >
            <b-button
              variant="danger"
              class="m-1"
              align="right"
              @click="tryQuit"
              >회원 탈퇴</b-button
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
import { mapMutations } from "vuex";

export default {
  name: "MemberJoin",
  data() {
    return {
      isJoinError: false,
      user: {
        userNo: this.$store.state.user.userNo,
        userId: this.$store.state.user.userId,
        userName: "",
        userPwd: "",
        email: "",
        phone: "",
        address: "",
      },
      newPwd: "",
      isValid: false,
    };
  },
  created() {
    http
      .post("/api/user/info")
      .then((response) => {
        console.log(response.data);
        this.user.userName = response.data.userName;
        this.user.email = response.data.email;
        this.user.phone = response.data.phone;
        this.user.address = response.data.address;
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    ...mapMutations(["UPDATE_LOGIN_STATUS"]),
    async tryModify() {
      const params = {
        userNo: this.user.userNo,
        userId: this.user.userId,
        userPwd: this.user.userPwd,
        userName: this.user.userName,
        email: this.user.email,
        phone: this.user.phone,
        address: this.user.address,
        newPwd: this.newPwd,
      };
      try {
        await http.post("/api/user/modify", params);
        alert("회원정보 수정을 완료했습니다.");
        this.$router.push({ name: "Home" });
      } catch (e) {
        this.isJoinError = true;
        alert("비밀번호가 일치하지 않습니다.");
      }
      this.isValid = true;
    },
    async tryQuit() {
      if (confirm("정말로 탈퇴하시겠습니까?")) {
        try {
          let response = await http.post("/api/user/quit");
          console.log(response.data);
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
        } catch (err) {
          alert("탈퇴 시도 중 문제 발생!");
        }
      } else {
        alert("잘 생각하셨어요!");
      }
    },
  },
};
</script>

<style></style>
