<template>
  <b-tr>
    <b-td>{{ article.articleno }}</b-td>
    <b-th class="text-left">
      <router-link :to="{ name: 'NoticeView', params: { article } }">{{
        article.subject
      }}</router-link>
    </b-th>
    <b-td>{{ article.hit }}</b-td>
    <b-td>{{ article.userid }}</b-td>
    <b-td>{{ article.regtime }}</b-td>
  </b-tr>
</template>

<script>
// import moment from "moment";

export default {
  name: "NoticeListRow",
  props: {
    article: Object,
  },
  computed: {
    // changeDateFormat() {
    //   return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    // },
  },
};
</script>

<style></style>
