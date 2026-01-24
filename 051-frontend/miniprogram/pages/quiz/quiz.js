const api = require('../../api/index')

Page({
  data: {
    categories: []
  },

  onLoad() {
    this.loadCategories()
  },

  async loadCategories() {
    const categories = await api.getCategoryList()
    this.setData({ categories })
  },

  startDaily() {
    if (!wx.getStorageSync('token')) {
      wx.showToast({ title: '请先登录', icon: 'none' })
      return
    }
    wx.navigateTo({ url: '/pages/quizResult/quizResult?type=daily' })
  },

  startCategory(e) {
    if (!wx.getStorageSync('token')) {
      wx.showToast({ title: '请先登录', icon: 'none' })
      return
    }
    const { id, name } = e.currentTarget.dataset
    wx.navigateTo({ url: `/pages/quizResult/quizResult?type=category&categoryId=${id}&name=${name}` })
  },

  goWrongList() {
    if (!wx.getStorageSync('token')) {
      wx.showToast({ title: '请先登录', icon: 'none' })
      return
    }
    wx.navigateTo({ url: '/pages/wrongList/wrongList' })
  },

  goRank() {
    wx.navigateTo({ url: '/pages/rank/rank' })
  }
})
