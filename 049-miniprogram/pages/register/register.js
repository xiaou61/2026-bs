const { post } = require('../../utils/request')

Page({
  data: {
    username: '',
    password: '',
    confirmPassword: '',
    nickname: '',
    phone: '',
    targetSchool: '',
    targetMajor: '',
    examYear: ''
  },

  onInput(e) {
    const field = e.currentTarget.dataset.field
    this.setData({ [field]: e.detail.value })
  },

  async register() {
    const { username, password, confirmPassword, nickname, phone, targetSchool, targetMajor, examYear } = this.data
    if (!username || !password) {
      wx.showToast({ title: '请输入用户名和密码', icon: 'none' })
      return
    }
    if (password !== confirmPassword) {
      wx.showToast({ title: '两次密码不一致', icon: 'none' })
      return
    }
    try {
      wx.showLoading({ title: '注册中...' })
      await post('/auth/register', { username, password, nickname, phone, targetSchool, targetMajor, examYear: examYear ? parseInt(examYear) : null })
      wx.showToast({ title: '注册成功', icon: 'success' })
      setTimeout(() => {
        wx.navigateBack()
      }, 1000)
    } catch (e) {
    } finally {
      wx.hideLoading()
    }
  }
})
