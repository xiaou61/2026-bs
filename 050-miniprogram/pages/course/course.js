const { get } = require('../../utils/request')
const app = getApp()

Page({
  data: {
    courses: []
  },

  onShow() {
    this.loadCourses()
  },

  async loadCourses() {
    const userInfo = app.globalData.userInfo
    if (!userInfo) return

    try {
      let res
      if (userInfo.role == 0) {
        res = await get('/course/student')
      } else if (userInfo.role == 1) {
        res = await get('/course/teacher')
      }
      this.setData({ courses: res.data || [] })
    } catch (err) {
      console.error('获取课程失败', err)
    }
  },

  viewDetail(e) {
    const id = e.currentTarget.dataset.id
    wx.showToast({
      title: '功能开发中',
      icon: 'none'
    })
  },

  viewRecord(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/record/record?courseId=${id}`
    })
  }
})
