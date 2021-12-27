<template>
  <b-overlay :show="show" spinner-variant="warning" spinner-type="grow">
    <b-row class="mt-4 mb-4 text-center">
      <b-col class="sm-3">
        <b-card>
          <h3><b-badge>주소 검색</b-badge></h3>
          <b-form-group
            label="시/도"
            label-for="sido"
            label-text-align="left"
            description="시/도를 선택해주세요"
          >
            <b-form-select
              id="sido"
              v-model="sidoCode"
              :options="sidos"
              @change="gugunList"
            ></b-form-select>
          </b-form-group>
          <b-form-group
            label="시/군/구"
            label-for="gugun"
            label-text-align="left"
            description="시/군/구를 선택해주세요"
          >
            <b-form-select
              id="gugun"
              v-model="gugunCode"
              :options="guguns"
              @change="umdList"
            ></b-form-select>
          </b-form-group>
          <b-form-group
            label="읍/면/동 (선택)"
            label-for="umd"
            label-text-align="left"
            description="읍/면/동을 선택해주세요"
          >
            <b-form-select
              id="umd"
              v-model="umdCode"
              :options="umds"
              @change="searchApt"
            ></b-form-select>
          </b-form-group>
        </b-card>
      </b-col>
      <b-col class="sm-3">
        <b-card>
          <h3><b-badge>아파트 검색</b-badge></h3>
          <b-form @submit.prevent="searchAptName">
            <b-form-group
              label="아파트 이름"
              label-for="aptName"
              label-text-align="left"
              description="아파트 이름을 입력해주세요"
            >
              <b-form-input id="aptName" v-model="aptName"> </b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary" class="m-1">
              검색
            </b-button>
          </b-form>
        </b-card>
        <b-card class="mt-5">
          <h5><b-badge variant="warning">날짜 선택</b-badge></h5>
          <b-form-datepicker
            id="date"
            size="sm"
            today-button
            reset-button
            close-button
            boundary="this"
            dropleft
            v-model="date"
            :date-format-options="{ year: 'numeric', month: 'numeric' }"
            :value-as-date="true"
            class="mb-2"
          ></b-form-datepicker>
        </b-card>
      </b-col>
    </b-row>
  </b-overlay>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
export default {
  name: "HouseSearchBar",
  data() {
    const currentdate = new Date();
    return {
      sidoCode: null,
      gugunCode: null,
      umdCode: null,
      aptName: "",
      date: currentdate,
      show: true,
    };
  },
  computed: {
    ...mapState(["sidos", "guguns", "houses", "umds"]),
    year() {
      return this.date.getFullYear();
    },
    month() {
      return this.date.getMonth() + 1;
    },
  },
  async created() {
    this.CLEAR_SIDO_LIST();
    this.SET_DETAIL_HOUSE(null);
    await this.getSido();
    console.log(this.sidos);
    if (this.sidos.length > 1) {
      this.show = false;
    }
  },
  methods: {
    ...mapActions([
      "getSido",
      "getGugun",
      "getUmd",
      "getHouseList",
      "getHouseListByName",
    ]),
    ...mapMutations([
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_UMD_LIST",
      "CLEAR_HOUSE_LIST",
      "SET_DETAIL_HOUSE",
    ]),
    gugunList() {
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_UMD_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    umdList() {
      console.log(this.sidoCode, this.gugunCode);
      this.CLEAR_UMD_LIST();
      this.umdCode = null;
      if (this.sidoCode && this.gugunCode)
        this.getUmd({ sido: this.sidoCode, gugun: this.gugunCode });
    },
    async searchApt() {
      if (this.gugunCode) {
        this.show = true;
        this.CLEAR_HOUSE_LIST();
        await this.getHouseList({
          sido: this.sidoCode,
          gugun: this.gugunCode,
          umd: this.umdCode,
          year: this.year,
          month: this.month,
        });
        this.show = false;
        this.SET_DETAIL_HOUSE(null);
        this.$emit("searchFinish");
      }
    },
    async searchAptName() {
      if (this.aptName.length > 1) {
        this.show = true;
        this.CLEAR_HOUSE_LIST();
        await this.getHouseListByName({
          name: this.aptName,
          year: this.year,
          month: this.month,
        });
        this.show = false;
        this.SET_DETAIL_HOUSE(null);
        this.$emit("searchFinish");
      }
    },
  },
};
</script>

<style>
.loading {
  position: absolute;
  left: 40%;
  top: 35%;
  background: transparent;
  z-index: 1000;
}
</style>
