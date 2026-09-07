Page({
  data: {
    title: '问答社区',
    tips: '问题列表接口尚未在当前后端实现，本页用于保证小程序结构完整。'
  },

  publish() {
    wx.navigateTo({ url: '/pages/publish-question/publish-question' });
  }
});
