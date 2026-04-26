Page({
  data: {
    title: '代码分享',
    tips: '代码片段接口尚未开放，本页为小程序结构占位。'
  },

  publish() {
    wx.navigateTo({ url: '/pages/publish-code/publish-code' });
  }
});
