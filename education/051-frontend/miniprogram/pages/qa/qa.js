const api = require('../../api/index')

Page({
  data: {
    qaList: [],
    page: 1
  },

  onShow() {
    this.loadQa()
  },

  async loadQa() {
    try {
      const res = await api.getQaList({ page: this.data.page, size: 10 })
      this.setData({ qaList: res.records || [] })
    } catch (e) {
      console.error(e)
    }
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
    this.setData({ page: 1 })
    this.loadQa().then(() => wx.stopPullDownRefresh())
  }
})
