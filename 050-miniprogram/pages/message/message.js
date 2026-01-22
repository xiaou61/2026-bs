const { get, put } = require('../../utils/request')

Page({
  data: {
    messages: []
  },

  onShow() {
    this.loadMessages()
  },

  async loadMessages() {
    try {
      const res = await get('/message/page', { page: 1, size: 50 })
      this.setData({ messages: res.data.records || [] })
    } catch (err) {
      console.error('获取消息失败', err)
    }
  },

  async readMessage(e) {
    const item = e.currentTarget.dataset.item
    if (item.isRead == 0) {
      try {
        await put(`/message/read/${item.id}`)
        this.loadMessages()
      } catch (err) {
        console.error('标记已读失败', err)
      }
    }
  },

  async markAllRead() {
    try {
      await put('/message/readAll')
      wx.showToast({ title: '已全部标记已读', icon: 'success' })
      this.loadMessages()
    } catch (err) {
      console.error('标记全部已读失败', err)
    }
  }
})
