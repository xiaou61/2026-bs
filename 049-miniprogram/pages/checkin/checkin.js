const { get, post } = require('../../utils/request')

Page({
  data: { stats: {}, records: [] },
  onLoad() { this.loadData() },
  async loadData() {
    try {
      const [stats, records] = await Promise.all([get('/study/checkin/stats'), get('/study/checkin/records')])
      this.setData({ stats: stats.data || {}, records: records.data || [] })
    } catch (e) {}
  },
  async checkin() {
    try {
      await post('/study/checkin', { studyDuration: 60, questionCount: 10, correctCount: 8, mood: '开心' })
      wx.showToast({ title: '打卡成功', icon: 'success' })
      this.loadData()
    } catch (e) {}
  }
})
