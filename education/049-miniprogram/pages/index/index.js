const app = getApp()
const { get } = require('../../utils/request')

Page({
  data: {
    userInfo: null,
    stats: {},
    notices: [],
    hotCourses: [],
    subjects: []
  },

  onLoad() {
    this.loadSubjects()
    this.loadNotices()
    this.loadHotCourses()
  },

  onShow() {
    this.setData({ userInfo: app.globalData.userInfo })
    if (app.globalData.token) {
      this.loadStats()
    }
  },

  async loadStats() {
    try {
      const res = await get('/user/stats')
      this.setData({ stats: res.data })
    } catch (e) {}
  },

  async loadSubjects() {
    try {
      const res = await get('/subject/list')
      this.setData({ subjects: res.data })
    } catch (e) {}
  },

  async loadNotices() {
    try {
      const res = await get('/notice/list', { size: 3 })
      this.setData({ notices: res.data.records || [] })
    } catch (e) {}
  },

  async loadHotCourses() {
    try {
      const res = await get('/course/list', { size: 4 })
      this.setData({ hotCourses: res.data.records || [] })
    } catch (e) {}
  },

  goToSubject(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({ url: `/pages/course/course?subjectId=${id}` })
  },

  goToCourse(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({ url: `/pages/course-detail/course-detail?id=${id}` })
  },

  goToNotice(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({ url: `/pages/notice/notice?id=${id}` })
  },

  goToCheckin() {
    if (!app.checkLogin()) return
    wx.navigateTo({ url: '/pages/checkin/checkin' })
  },

  goToPlan() {
    if (!app.checkLogin()) return
    wx.navigateTo({ url: '/pages/study-plan/study-plan' })
  },

  goToWrongBook() {
    if (!app.checkLogin()) return
    wx.navigateTo({ url: '/pages/wrong-book/wrong-book' })
  },

  goToNote() {
    if (!app.checkLogin()) return
    wx.navigateTo({ url: '/pages/note/note' })
  }
})
