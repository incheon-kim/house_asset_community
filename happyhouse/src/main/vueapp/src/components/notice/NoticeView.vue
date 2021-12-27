<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글보기</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listNotice">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button
          variant="outline-info"
          size="sm"
          v-if="role"
          @click="moveModifyNotice"
          class="mr-2"
          >글수정</b-button
        >
        <b-button
          variant="outline-danger"
          size="sm"
          v-if="role"
          @click="deleteNotice"
          >글삭제</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${article.articleno}.
          ${article.subject} [${article.hit}]</h3><div><h6>${article.userid}</div><div>${article.regtime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      article: null,
    };
  },
  computed: {
    ...mapState(["boards", "user"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
    role() {
      return this.user.role == 1;
    },
  },
  beforeCreate: async function () {
    let article = this.$route.params.article;
    const url = "/notice/" + article.articleno;
    await http
      .get(url)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  },

  created() {
    this.article = this.$route.params.article;
    this.article.hit = this.article.hit + 1;
  },
  methods: {
    ...mapActions(["getArticle"]),
    listNotice() {
      this.$router.push({ name: "NoticeList" });
    },
    moveModifyNotice() {
      this.$router.replace({
        name: "NoticeUpdate",
        params: { articleno: this.article.articleno },
      });
    },
    deleteNotice() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "NoticeDelete",
          params: { articleno: this.article.articleno },
        });
      }
    },
  },
};
</script>

<style></style>
