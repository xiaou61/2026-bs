const api = require('../../api/index')
Page({
  data: { news: null },
  onLoad(options) {
    if (options.id) this.loadNews(options.id)
  },
  async loadNews(id) {
    const news = await api.getNewsDetail(id)
    this.setData({ news })
    wx.setNavigationBarTitle({ title: news.title })
  }
})
