<template>
  <div class="home">
    <b-container class="bv-example-row mt-5 text-center">
      <b-carousel
        id="mainCarousel"
        v-model="slide"
        :interval="4000"
        controls
        indicators
        background="#ababab"
        img-width="1280"
        img-height="720"
        style="text-shadow: 1px 1px 2px #333"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <b-carousel-slide
          caption="HappyHouse"
          text="아파트 거래 정보는 이곳에서!"
          ><template #img
            ><img
              src="@/assets/carousel/1.jpg"
              width="1280"
              height="720"
              alt="HappyHouse" /></template
        ></b-carousel-slide>
        <b-carousel-slide
          caption="HappyHouse"
          text="실시간 갱신되는 아파트 거래 정보!"
          ><template #img
            ><img
              src="@/assets/carousel/2.jpg"
              width="1280"
              height="720"
              alt="HappyHouse" /></template
        ></b-carousel-slide>
      </b-carousel>
      <div align="left">
        <b-jumbotron
          header="HappyHouse"
          lead="아파트 거래 정보의 새 지평을 열다.."
        >
          <hr class="my-4" />
          <p>실시간으로 갱신되는 아파트 거래 정보를 만나보세요!</p>

          <b-button to="/user/join" variant="primary" href="#"
            >시작해보기</b-button
          >
          <b-button to="/user/login" variant="warning" href="#"
            >아이디가 이미 있다면?</b-button
          >
        </b-jumbotron>
      </div>

      <h1 align="left"><b>팀 소개</b></h1>
      <hr />
      <b-row align="center">
        <b-col cols="4" align="left">
          <h3 class="mt-3">
            <b><em>팀 민세우</em></b>
          </h3>
          <h4>
            <b><em>SSAFY 6기 서울캠퍼스 9반</em></b>
          </h4>
          <h4>
            <b><em>취준자 와 취직자의 콜라보</em></b>
          </h4>
          <h4>
            <b><em>면접 일정만 아니었다면!</em></b>
          </h4>
          <h4>
            <b><em>더 멋있게 만들 수 있었는데!</em></b>
          </h4>
          <h4>
            <b><em>너무 아쉽다!</em></b>
          </h4>
        </b-col>
        <b-card-group>
          <b-card fluid>
            <img
              src="@/assets/profile/kim.jpg"
              alt="kim picture"
              class="card-img-top"
              height="400px"
            />
            <b-card-text class="mt-5"><h3>조장 - 김민우</h3></b-card-text>
          </b-card>
          <b-card img-bottom>
            <img
              src="@/assets/profile/say.jpg"
              alt="say picutre"
              height="400px"
              class="card-img-bottom"
            />
            <b-card-text class="mt-5"><h3>팀원 - 나세이</h3></b-card-text>
          </b-card>
        </b-card-group>
      </b-row>
    </b-container>
  </div>
</template>

<script>
// @ is an alias to /src
//import HelloWorld from "@/components/HelloWorld.vue";

export default {
  data() {
    return {
      slide: 0,
      sliding: null,
    };
  },
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
};
</script>
