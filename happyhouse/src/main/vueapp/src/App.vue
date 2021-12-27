<template>
  <div id="app">
    <navi-bar />
    <router-view />
  </div>
</template>

<script>
import NaviBar from "./components/layout/NaviBar.vue";

export default {
  name: "App",
  components: {
    NaviBar,
  },
};
</script>

<style>
* {
  font-family: "GmarketSansMedium";
}

a:hover {
  text-decoration: none;
  font-weight: bold;
}

a.router-link-exact-active {
  color: #000000;
}

@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
</style>
