<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>비밀번호 찾기</h3></b-alert>
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
            <b-form-group label="아이디:" label-for="userId">
              <b-form-input
                id="userId"
                v-model="user.userId"
                required
                placeholder="아이디 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="useremail">
              <b-form-input
                type="text"
                id="email"
                v-model="user.email"
                required
                placeholder="이메일 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-button type="button" @click="checkEmail">{{
              isChecked
            }}</b-button>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              :disabled="disabled == 1"
              @click="findPwd"
              >임시 비밀번호 보내기</b-button
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
// import { mapMutations } from "vuex";

export default {
  name: "MemberLogin",
  data() {
    return {
      isChecked: "이메일 확인",
      disabled: 1,
      isLoginError: false,
      user: {
        userId: "",
        email: "",
      },
    };
  },
  methods: {
    checkEmail() {
      http
        .put("/api/user/checkEmail", this.user)
        .then((response) => {
          let flag = response.data;
          console.log(flag);
          if (flag) {
            this.isChecked = "확인완료";
            this.disabled = 0;
          } else {
            this.isChecked = "이메일 확인";
            this.disabled = 1;
            alert("아이디 혹은 이메일이 틀렸습니다.");
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    findPwd() {
      http
        .put("/api/user/getNewPwd", this.user)
        .then((response) => {
          console.log(response.data);
          prompt(
            "임시 비밀번호가 발급되었습니다. 복사하여 로그인하세요.",
            response.data
          );
          this.$router.push({ name: "MemberLogin" });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // ...mapMutations(["UPDATE_LOGIN_STATUS"]),
    // async tryLogin() {
    //   const params = {
    //     userId: this.user.userid,
    //     userPwd: this.user.userpwd,
    //   };
    //   console.log(params);
    //   try {
    //     let response = await http.post("/api/user/login", params);
    //     response.data.isAuth = true;
    //     console.log(response.data);
    //     this.UPDATE_LOGIN_STATUS(response.data);
    //     this.$router.push({ name: "Home" });
    //   } catch (e) {
    //     alert("로그인 실패");
    //   }
    // },
    // movePage() {
    //   this.$router.push({ name: "MemberJoin" });
    // },
  },
};
</script>

<style></style>
