<template>
  <b-container fluid class="bv-example-row mt-3 text-center">
    <b-row>
      <b-col>
        <house-search-bar @searchFinish="scrollToMap"></house-search-bar>
      </b-col>
    </b-row>

    <b-row id="mapRow">
      <b-col>
        <kakao-map></kakao-map>
      </b-col>
    </b-row>

    <b-row>
      <b-col cols="7" align="left">
        <house-list />
      </b-col>
      <b-col cols="5" class="mt-3" align="center">
        <h2><b-badge variant="warning">상세 정보</b-badge></h2>
        <house-detail class="mt-3" align="left" />
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import KakaoMap from "@/components/house/KakaoMap.vue";

export default {
  name: "House",
  components: {
    HouseSearchBar,
    HouseList,
    HouseDetail,
    KakaoMap,
  },
  methods: {
    scrollToMap() {
      document.getElementById("mapRow").scrollIntoView({
        behavior: "smooth",
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
