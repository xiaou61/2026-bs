const app = getApp()

Page({
  data: {
    userInfo: null
  },

  onShow() {
    this.setData({ userInfo: app.globalData.userInfo })
  },

  goToLogin() {
    wx.navigateTo({ url: '/pages/login/login' })
  },

  goToPage(e) {
    if (!app.checkLogin()) return
    const page = e.currentTarget.dataset.page
    wx.navigateTo({ url: `/pages/${page}/${page}` })
  },

  logout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          app.logout()
          this.setData({ userInfo: null })
          wx.showToast({ title: '已退出登录', icon: 'success' })
        }
      }
    })
  }
})
