const { get, post, put } = require('../../utils/request')

Page({
  data: { plans: [] },
  onLoad() { this.loadPlans() },
  async loadPlans() {
    try {
      const res = await get('/study/plans')
      this.setData({ plans: res.data || [] })
    } catch (e) {}
  },
  createPlan() {
    wx.showModal({
      title: '创建学习计划',
      editable: true,
      placeholderText: '请输入计划内容',
      success: async (res) => {
        if (res.confirm && res.content) {
          await post('/study/plan', {
            title: res.content.substring(0, 20),
            targetContent: res.content,
            startDate: new Date().toISOString().split('T')[0],
            endDate: new Date(Date.now() + 30 * 24 * 60 * 60 * 1000).toISOString().split('T')[0],
            dailyHours: 2,
            progress: 0
          })
          wx.showToast({ title: '创建成功', icon: 'success' })
          this.loadPlans()
        }
      }
    })
  },
  async updateProgress(e) {
    const { id, progress } = e.currentTarget.dataset
    await put(`/study/plan/${id}/progress`, { progress: Math.min(100, Number(progress) + 10) })
    this.loadPlans()
  }
})
