const app = getApp()
const { get } = require('../../utils/request')

Page({
  data: {
    subjects: [],
    categories: [],
    currentSubject: null
  },

  onLoad() {
    this.loadSubjects()
  },

  async loadSubjects() {
    try {
      const res = await get('/subject/list')
      this.setData({ subjects: res.data || [] })
      if (res.data && res.data.length) {
        this.setData({ currentSubject: res.data[0].id })
        this.loadCategories(res.data[0].id)
      }
    } catch (e) {}
  },

  async loadCategories(subjectId) {
    try {
      const res = await get(`/subject/${subjectId}/categories`)
      this.setData({ categories: res.data || [] })
    } catch (e) {}
  },

  onSubjectChange(e) {
    const id = e.currentTarget.dataset.id
    this.setData({ currentSubject: id })
    this.loadCategories(id)
  },

  startPractice(e) {
    if (!app.checkLogin()) return
    const { subjectId, categoryId, type } = e.currentTarget.dataset
    let url = `/pages/practice/practice?subjectId=${subjectId || this.data.currentSubject}`
    if (categoryId) url += `&categoryId=${categoryId}`
    if (type) url += `&type=${type}`
    wx.navigateTo({ url })
  },

  goToRandom() {
    if (!app.checkLogin()) return
    wx.navigateTo({ url: `/pages/practice/practice?subjectId=${this.data.currentSubject}&random=1` })
  },

  goToWrongBook() {
    if (!app.checkLogin()) return
    wx.navigateTo({ url: '/pages/wrong-book/wrong-book' })
  }
})
