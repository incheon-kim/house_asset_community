<template>
  <div id="kakaoMap"></div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      markers: [],
      infowindow: null,
      unsubscribe: null,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const apikey = "bf649eb957132670b11de5a25fc775a0";
      const script = document.createElement("script");
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${apikey}`;
      script.onload = () => kakao.maps.load(this.initMap);
      document.head.appendChild(script);
    }

    this.unsubscribe = this.$store.subscribe((mutation) => {
      if (mutation.type == "SET_HOUSE_LIST") {
        this.displayMarkers(mutation.payload);
      }
      if (mutation.type == "CLEAR_HOUSE_LIST") {
        this.displayMarkers([]);
      }
      if (mutation.type == "SET_DETAIL_HOUSE" && mutation.payload) {
        const lng = mutation.payload.rng;
        const lat = mutation.payload.lat;
        this.map.setCenter(new kakao.maps.LatLng(lat, lng));
      }
    });
  },
  created() {},
  destroyed() {
    this.unsubscribe();
  },
  computed: {
    ...mapState(["houses", "house"]),
  },
  methods: {
    initMap() {
      const mapContainer = document.getElementById("kakaoMap");
      const options = {
        center: new kakao.maps.LatLng(37.564343, 126.947613),
        level: 5,
      };
      this.map = new kakao.maps.Map(mapContainer, options);
    },
    displayMarkers(payload) {
      console.log(payload);
      let positions = payload.map((house) => {
        //return { lat: house.lat, lng: house.rng };
        return new kakao.maps.LatLng(house.lat, house.rng);
      });

      if (positions.length > 0) {
        this.markers = positions.map((position) => {
          return new kakao.maps.Marker({ map: this.map, position });
        });
      }

      const mapBound = positions.reduce(
        (mapBound, latlng) => mapBound.extend(latlng),
        new kakao.maps.LatLngBounds()
      );

      this.map.setBounds(mapBound);
    },
  },
};
</script>

<style scoped>
#kakaoMap {
  width: 100%;
  height: 500px;
}
</style>
