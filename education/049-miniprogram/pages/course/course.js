const { get } = require('../../utils/request')

Page({
  data: {
    courses: [],
    subjects: [],
    currentSubject: null,
    keyword: '',
    isFree: null,
    current: 1,
    hasMore: true,
    loading: false
  },

  onLoad(options) {
    if (options.subjectId) {
      this.setData({ currentSubject: parseInt(options.subjectId) })
    }
    this.loadSubjects()
    this.loadCourses()
  },

  async loadSubjects() {
    try {
      const res = await get('/subject/list')
      this.setData({ subjects: [{ id: null, name: '全部' }, ...res.data] })
    } catch (e) {}
  },

  async loadCourses(refresh = true) {
    if (this.data.loading) return
    if (!refresh && !this.data.hasMore) return
    
    this.setData({ loading: true })
    const current = refresh ? 1 : this.data.current
    
    try {
      const res = await get('/course/list', {
        current,
        size: 10,
        subjectId: this.data.currentSubject,
        keyword: this.data.keyword || undefined,
        isFree: this.data.isFree
      })
      const records = res.data.records || []
      this.setData({
        courses: refresh ? records : [...this.data.courses, ...records],
        current: current + 1,
        hasMore: records.length === 10
      })
    } catch (e) {}
    this.setData({ loading: false })
  },

  onSubjectChange(e) {
    const subject = this.data.subjects[e.currentTarget.dataset.index]
    this.setData({ currentSubject: subject.id })
    this.loadCourses()
  },

  onSearch(e) {
    this.setData({ keyword: e.detail.value })
    this.loadCourses()
  },

  onFilterChange(e) {
    const type = e.currentTarget.dataset.type
    let isFree = null
    if (type === 'free') isFree = 1
    else if (type === 'paid') isFree = 0
    this.setData({ isFree })
    this.loadCourses()
  },

  goToDetail(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({ url: `/pages/course-detail/course-detail?id=${id}` })
  },

  onReachBottom() {
    this.loadCourses(false)
  }
})
