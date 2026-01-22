const { get } = require('../../utils/request')
const app = getApp()

Page({
  data: {
    userInfo: null,
    unreadCount: 0
  },

  onShow() {
    this.setData({ userInfo: app.globalData.userInfo })
    this.loadUnreadCount()
  },

  async loadUnreadCount() {
    try {
      const res = await get('/message/unreadCount')
      this.setData({ unreadCount: res.data || 0 })
    } catch (err) {
      console.error('获取未读消息数失败', err)
    }
  },

  goMessage() {
    wx.navigateTo({
      url: '/pages/message/message'
    })
  },

  goLeave() {
    wx.navigateTo({
      url: '/pages/leave/leave'
    })
  },

  goMakeup() {
    wx.navigateTo({
      url: '/pages/leave/leave?type=makeup'
    })
  },

  goAbout() {
    wx.showModal({
      title: '关于系统',
      content: '课堂考勤签到APP v1.0\n便捷签到 高效管理',
      showCancel: false
    })
  },

  logout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          app.logout()
          wx.navigateTo({
            url: '/pages/login/login'
          })
        }
      }
    })
  }
})
