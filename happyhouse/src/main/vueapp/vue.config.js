const path = require("path");

module.exports = {
  outputDir: path.resolve(__dirname, "../resources/static"),
  devServer: {
    headers: { "Access-Control-Allow-Origin": "*" },
  },
};
