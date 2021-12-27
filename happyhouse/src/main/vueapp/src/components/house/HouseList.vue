<template>
  <b-container
    fluid
    v-if="houses && houses.length > 0"
    class="bv-example-row mt-3"
  >
    <!-- <house-list-row
      v-for="(house, index) in houses"
      :key="index"
      :house="house"
    /> -->
    <b-table
      id="houseTable"
      fluid
      :items="houses"
      :fields="columns"
      hover
      :per-page="perPage"
      @row-clicked="rowClicked"
    >
    </b-table>
  </b-container>
  <b-container v-else class="bv-example-row mt-3">
    <b-row>
      <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
//import HouseListRow from "@/components/house/HouseListRow.vue";
import { mapMutations, mapState } from "vuex";

export default {
  name: "HouseList",
  components: {
    //HouseListRow,
  },
  data() {
    return {
      columns: [
        {
          key: "aptCode",
          sortable: false,
          label: "일련번호",
        },
        {
          key: "apt",
          sortable: true,
          label: "이름",
        },
        {
          key: "price",
          sortable: true,
          label: "거래가격",
          formatter: "priceFormatter",
        },
        {
          key: "tradeDate",
          sortable: true,
          label: "거래 일자",
        },
        {
          key: "floor",
          sortable: true,
          label: "층",
        },
        {
          key: "constructYear",
          sortable: true,
          label: "시공년도",
          formatter: "yearFormatter",
        },
        {
          key: "area",
          sortable: true,
          label: "전용면적",
          formatter: "areaFormatter",
        },
      ],
      perPage: 25,
    };
  },
  created() {
    this.CLEAR_HOUSE_LIST();
  },
  computed: {
    ...mapState(["houses"]),
  },
  methods: {
    ...mapMutations(["CLEAR_HOUSE_LIST", "SET_DETAIL_HOUSE"]),
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
    rowClicked(row) {
      this.SET_DETAIL_HOUSE(row);
    },
  },
};
</script>

<style scoped>
#houseTable::-webkit-scrollbar {
  width: 3px;
}
#houseTable::-webkit-scrollbar-thumb {
}
#houseTable::-webkit-scrollbar-track {
}
</style>
