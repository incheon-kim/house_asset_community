<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글작성</h3></b-alert>
      </b-col>
    </b-row>

    <board-write-form type="register" />
  </b-container>
</template>

<script>
// import { mapActions } from "vuex";
import BoardWriteForm from "./child/BoardWriteForm.vue";

export default {
  name: "BoardWrite",
  components: {
    BoardWriteForm,
  },
  methods: {
    // ...mapActions(["writeArticle"]),
    // writeArticle() {
    //   if (this.isValide) this.writeArticle();
    // },
  },
};
</script>

<style></style>
