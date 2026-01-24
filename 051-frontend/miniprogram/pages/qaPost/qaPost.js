const api = require('../../api/index')
Page({
  data: { title: '', content: '' },
  inputTitle(e) { this.setData({ title: e.detail.value }) },
  inputContent(e) { this.setData({ content: e.detail.value }) },
  async submit() {
    if (!this.data.title.trim()) { wx.showToast({ title: '请输入标题', icon: 'none' }); return }
    if (!this.data.content.trim()) { wx.showToast({ title: '请输入内容', icon: 'none' }); return }
    await api.createQaPost({ title: this.data.title, content: this.data.content })
    wx.showToast({ title: '发布成功', icon: 'success' })
    setTimeout(() => wx.navigateBack(), 1500)
  }
})
