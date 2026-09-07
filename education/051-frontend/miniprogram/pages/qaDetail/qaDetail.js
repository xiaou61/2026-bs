const api = require('../../api/index')
Page({
  data: { post: null, replyContent: '' },
  onLoad(options) { if (options.id) { this.postId = options.id; this.loadPost() } },
  async loadPost() {
    const post = await api.getQaDetail(this.postId)
    this.setData({ post })
  },
  inputReply(e) { this.setData({ replyContent: e.detail.value }) },
  async submitReply() {
    if (!wx.getStorageSync('token')) { wx.showToast({ title: '请先登录', icon: 'none' }); return }
    if (!this.data.replyContent.trim()) { wx.showToast({ title: '请输入内容', icon: 'none' }); return }
    await api.createQaReply({ postId: this.postId, content: this.data.replyContent })
    this.setData({ replyContent: '' })
    this.loadPost()
    wx.showToast({ title: '回复成功', icon: 'success' })
  },
  async likeReply(e) {
    if (!wx.getStorageSync('token')) { wx.showToast({ title: '请先登录', icon: 'none' }); return }
    await api.likeReply(e.currentTarget.dataset.id)
    this.loadPost()
  }
})
