const api = require('../../api/index')

Page({
  data: {
    tabIndex: 0,
    newsList: [],
    qaList: [],
    newsPage: 1,
    qaPage: 1
  },

  onLoad() {
    this.loadNews()
  },

  switchTab(e) {
    const index = e.currentTarget.dataset.index
    this.setData({ tabIndex: index })
    if (index === 0 && this.data.newsList.length === 0) {
      this.loadNews()
    } else if (index === 1 && this.data.qaList.length === 0) {
      this.loadQa()
    }
  },

  async loadNews() {
    const res = await api.getNewsList({ page: this.data.newsPage, size: 10 })
    this.setData({ newsList: res.records || [] })
  },

  async loadQa() {
    const res = await api.getQaList({ page: this.data.qaPage, size: 10 })
    this.setData({ qaList: res.records || [] })
  },

  goNewsDetail(e) {
    wx.navigateTo({ url: '/pages/newsDetail/newsDetail?id=' + e.currentTarget.dataset.id })
  },

  goQaDetail(e) {
    wx.navigateTo({ url: '/pages/qaDetail/qaDetail?id=' + e.currentTarget.dataset.id })
  },

  goQaPost() {
    if (!wx.getStorageSync('token')) {
      wx.showToast({ title: '请先登录', icon: 'none' })
      return
    }
    wx.navigateTo({ url: '/pages/qaPost/qaPost' })
  },

  onPullDownRefresh() {
    if (this.data.tabIndex === 0) {
      this.setData({ newsPage: 1 })
      this.loadNews().then(() => wx.stopPullDownRefresh())
    } else {
      this.setData({ qaPage: 1 })
      this.loadQa().then(() => wx.stopPullDownRefresh())
    }
  }
})
