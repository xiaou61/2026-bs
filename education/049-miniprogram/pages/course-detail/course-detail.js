const app = getApp()
const { get } = require('../../utils/request')

Page({
  data: {
    course: null,
    chapters: [],
    activeTab: 'intro'
  },

  onLoad(options) {
    this.courseId = options.id
    this.loadCourse()
    this.loadChapters()
  },

  async loadCourse() {
    try {
      const res = await get(`/course/${this.courseId}`)
      this.setData({ course: res.data })
      wx.setNavigationBarTitle({ title: res.data.title })
    } catch (e) {}
  },

  async loadChapters() {
    try {
      const res = await get(`/course/${this.courseId}/chapters`)
      this.setData({ chapters: res.data || [] })
    } catch (e) {}
  },

  switchTab(e) {
    this.setData({ activeTab: e.currentTarget.dataset.tab })
  },

  playChapter(e) {
    if (!app.checkLogin()) return
    const chapter = e.currentTarget.dataset.chapter
    if (!this.data.course.isFree && !chapter.isFree) {
      wx.showToast({ title: '请先购买课程', icon: 'none' })
      return
    }
    wx.showToast({ title: '播放: ' + chapter.title, icon: 'none' })
  }
})
