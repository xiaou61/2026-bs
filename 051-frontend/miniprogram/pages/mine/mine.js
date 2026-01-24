const api = require('../../api/index')

Page({
  data: {
    userInfo: null,
    stats: null
  },

  onShow() {
    this.checkLogin()
  },

  checkLogin() {
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo) {
      this.setData({ userInfo })
      this.loadStats()
    } else {
      this.setData({ userInfo: null, stats: null })
    }
  },

  async loadStats() {
    const stats = await api.getUserStats()
    this.setData({ stats })
  },

  handleLogin() {
    wx.login({
      success: async (res) => {
        if (res.code) {
          try {
            const loginRes = await api.login({ code: res.code })
            wx.setStorageSync('token', loginRes.token)
            const userInfo = {
              userId: loginRes.userId,
              nickname: loginRes.nickname,
              avatar: loginRes.avatar
            }
            wx.setStorageSync('userInfo', userInfo)
            
            const fullUserInfo = await api.getUserInfo()
            this.setData({ userInfo: fullUserInfo })
            this.loadStats()
            wx.showToast({ title: '登录成功', icon: 'success' })
          } catch (e) {
            console.error(e)
          }
        }
      }
    })
  },

  handleLogout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          wx.removeStorageSync('token')
          wx.removeStorageSync('userInfo')
          this.setData({ userInfo: null, stats: null })
          wx.showToast({ title: '已退出', icon: 'success' })
        }
      }
    })
  },

  goFavorite() {
    if (!wx.getStorageSync('token')) {
      wx.showToast({ title: '请先登录', icon: 'none' })
      return
    }
    wx.navigateTo({ url: '/pages/favorite/favorite' })
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
