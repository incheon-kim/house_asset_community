<template>
  <b-card class="mt-3" v-if="house">
    <b-card-title
      ><h2>{{ house.apt }}</h2></b-card-title
    >
    <b-card-sub-title
      ><h5>
        {{ house.dong }} {{ house.bonbun | bonbunFormatter
        }}{{ house.bubun | bubunFormatter }} {{ house.floor }}층
      </h5></b-card-sub-title
    >
    <b-card-body>
      <b-card-text>
        <b>거래 가격 - </b>{{ house.price | priceFormatter }} <br />
        <b>거래 일자 - </b>{{ house.tradeDate }} <br />
        <b>전용 면적 - </b>{{ house.area | areaFormatter }} <br />
        <b>시공 년도 - </b>{{ house.constructYear }} 년<br
      /></b-card-text>
      <b-card-text align="right">
        <b-button
          :href="house.deepLink"
          target="_blank"
          variant="info"
          class="mt-5"
          >네이버 부동산에서 보기</b-button
        >
      </b-card-text>
    </b-card-body>
  </b-card>
  <b-card class="mt-3" align="center" v-else>
    <b-card-title><h4>상세 정보가 여기에 표시됩니다.</h4></b-card-title>
  </b-card>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "VueappHousedetail",
  data() {
    return {};
  },

  mounted() {},
  computed: {
    ...mapState(["house"]),
  },
  methods: {},
  filters: {
    priceFormatter(value) {
      return `${(value + "0000").replace(
        /(\d)(?=(\d\d\d)+(?!\d))/g,
        "$1,"
      )} 원`;
    },
    areaFormatter(value) {
      return `${value} ㎡`;
    },
    yearFormatter(value) {
      return `${value}년`;
    },
    bonbunFormatter(value) {
      return parseInt(value);
    },
    bubunFormatter(value) {
      value = parseInt(value);
      return value == 0 ? "" : "-" + value;
    },
  },
};
</script>

<style lang="scss" scoped></style>
