const { get, post } = require('../../utils/request')

Page({
  data: {
    questions: [],
    current: 1,
    hasMore: true,
    loading: false
  },

  onLoad() {
    this.loadData()
  },

  async loadData(refresh = true) {
    if (this.data.loading) return
    if (!refresh && !this.data.hasMore) return
    this.setData({ loading: true })
    const current = refresh ? 1 : this.data.current
    try {
      const res = await get('/question/wrong', { current, size: 10 })
      const records = res.data.records || []
      this.setData({
        questions: refresh ? records : [...this.data.questions, ...records],
        current: current + 1,
        hasMore: records.length === 10
      })
    } catch (e) {}
    this.setData({ loading: false })
  },

  async markMastered(e) {
    const id = e.currentTarget.dataset.id
    try {
      await post(`/question/wrong/${id}/mastered`)
      wx.showToast({ title: '已标记为掌握', icon: 'success' })
      this.loadData()
    } catch (e) {}
  },

  onReachBottom() {
    this.loadData(false)
  }
})
