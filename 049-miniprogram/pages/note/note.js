const { get, post } = require('../../utils/request')

Page({
  data: { notes: [], current: 1, hasMore: true, loading: false },
  onLoad() { this.loadNotes() },
  async loadNotes(refresh = true) {
    if (this.data.loading) return
    this.setData({ loading: true })
    const current = refresh ? 1 : this.data.current
    try {
      const res = await get('/note/my', { current, size: 10 })
      const records = res.data.records || []
      this.setData({
        notes: refresh ? records : [...this.data.notes, ...records],
        current: current + 1,
        hasMore: records.length === 10
      })
    } catch (e) {}
    this.setData({ loading: false })
  },
  createNote() {
    wx.showModal({
      title: '新建笔记',
      editable: true,
      placeholderText: '请输入笔记内容',
      success: async (res) => {
        if (res.confirm && res.content) {
          await post('/note', { title: res.content.substring(0, 20), content: res.content, isPublic: 0 })
          wx.showToast({ title: '创建成功', icon: 'success' })
          this.loadNotes()
        }
      }
    })
  },
  onReachBottom() { this.loadNotes(false) }
})
