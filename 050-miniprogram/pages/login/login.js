const { post } = require('../../utils/request')
const app = getApp()

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

  async onLogin() {
    const { username, password } = this.data
    
    if (!username || !password) {
      wx.showToast({
        title: '请输入用户名和密码',
        icon: 'none'
      })
      return
    }

    wx.showLoading({ title: '登录中...' })
    
    try {
      const res = await post('/auth/login', { username, password })
      
      app.setLoginInfo(res.data.token, res.data.user)
      
      wx.showToast({
        title: '登录成功',
        icon: 'success'
      })
      
      setTimeout(() => {
        wx.switchTab({
          url: '/pages/index/index'
        })
      }, 1000)
    } catch (err) {
      console.error('登录失败', err)
    } finally {
      wx.hideLoading()
    }
  },

  onWxLogin() {
    wx.showToast({
      title: '请先使用账号登录后绑定微信',
      icon: 'none'
    })
  }
})
