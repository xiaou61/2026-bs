const app = getApp()
const { post } = require('../../utils/request')

Page({
  data: {
    username: '',
    password: ''
  },

  onUsernameInput(e) {
    this.setData({ username: e.detail.value })
  },

  onPasswordInput(e) {
    this.setData({ password: e.detail.value })
  },

  async login() {
    const { username, password } = this.data
    if (!username || !password) {
      wx.showToast({ title: '请输入用户名和密码', icon: 'none' })
      return
    }
    try {
      wx.showLoading({ title: '登录中...' })
      const res = await post('/auth/login', { username, password })
      app.globalData.token = res.data.token
      app.globalData.userInfo = res.data.user
      wx.setStorageSync('token', res.data.token)
      wx.setStorageSync('userInfo', res.data.user)
      wx.showToast({ title: '登录成功', icon: 'success' })
      setTimeout(() => {
        wx.navigateBack()
      }, 1000)
    } catch (e) {
    } finally {
      wx.hideLoading()
    }
  },

  goToRegister() {
    wx.navigateTo({ url: '/pages/register/register' })
  }
})
