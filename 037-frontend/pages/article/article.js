Page({
  data: {
    title: '技术文章',
    tips: '文章模块当前为小程序占位页，后端文章接口需后续补齐。'
  },

  publish() {
    wx.navigateTo({ url: '/pages/publish-article/publish-article' });
  }
});
