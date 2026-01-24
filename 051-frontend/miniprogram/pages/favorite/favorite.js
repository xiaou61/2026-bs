const api = require('../../api/index')
Page({
  data: { articles: [] },
  onShow() { this.loadData() },
  async loadData() {
    const res = await api.getFavoriteList({ page: 1, size: 50 })
    this.setData({ articles: res.records || [] })
  },
  goArticle(e) {
    wx.navigateTo({ url: '/pages/article/article?id=' + e.currentTarget.dataset.id })
  }
})
