<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>공지사항</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-right">
        <b-button v-if="role" variant="outline-primary" @click="moveWrite()"
          >글쓰기</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="notices.length">
        <b-table-simple hover responsive>
          <b-thead head-variant="dark">
            <b-tr>
              <b-th>글번호</b-th>
              <b-th>제목</b-th>
              <b-th>조회수</b-th>
              <b-th>작성자</b-th>
              <b-th>작성일</b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component인 ListRow에 데이터 전달(props) -->
            <notice-list-row
              v-for="(notice, index) in notices"
              :key="index"
              v-bind="notice"
            />
          </tbody>
        </b-table-simple>
      </b-col>
      <!-- <b-col v-else class="text-center">도서 목록이 없습니다.</b-col> -->
    </b-row>
  </b-container>
</template>

<script>
// import http from "@/util/http-common";
import NoticeListRow from "@/components/notice/child/NoticeListRow";
import { mapActions, mapMutations, mapState } from "vuex";

export default {
  name: "NoticeList",
  components: {
    NoticeListRow,
  },
  data() {
    return {
      article: "",
      // articles: [],
    };
  },
  async created() {
    this.CLEAR_NOTICE_LIST();
    this.getNoticeLists();
    await this.getRoleStatus();
  },
  computed: {
    ...mapState(["notices", "user"]),
    role() {
      return this.user.role == 1;
    },
  },
  methods: {
    ...mapActions(["getNoticeLists", "getRoleStatus"]),
    ...mapMutations(["CLEAR_NOTICE_LIST"]),
    moveWrite() {
      let flag = this.$store.state.user.isAuth;
      console.log(flag);
      if (flag) {
        this.$router.push({ name: "NoticeWrite" });
      } else {
        alert("로그인 후 이용하세요");
      }
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>
