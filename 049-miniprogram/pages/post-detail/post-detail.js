const app = getApp()
const { get, post } = require('../../utils/request')

Page({
  data: {
    postInfo: null,
    comments: [],
    commentContent: ''
  },

  onLoad(options) {
    this.postId = options.id
    this.loadPost()
    this.loadComments()
  },

  async loadPost() {
    try {
      const res = await get(`/post/${this.postId}`)
      this.setData({ postInfo: res.data })
    } catch (e) {}
  },

  async loadComments() {
    try {
      const res = await get(`/post/${this.postId}/comments`)
      this.setData({ comments: res.data || [] })
    } catch (e) {}
  },

  onCommentInput(e) {
    this.setData({ commentContent: e.detail.value })
  },

  async submitComment() {
    if (!app.checkLogin()) return
    if (!this.data.commentContent.trim()) {
      wx.showToast({ title: '请输入评论内容', icon: 'none' })
      return
    }
    try {
      await post(`/post/${this.postId}/comment`, { content: this.data.commentContent })
      wx.showToast({ title: '评论成功', icon: 'success' })
      this.setData({ commentContent: '' })
      this.loadComments()
      this.loadPost()
    } catch (e) {}
  }
})
