const app = getApp()
const { get, post } = require('../../utils/request')

Page({
  data: {
    posts: [],
    current: 1,
    hasMore: true,
    loading: false
  },

  onLoad() {
    this.loadPosts()
  },

  onShow() {
    if (this.needRefresh) {
      this.loadPosts()
      this.needRefresh = false
    }
  },

  async loadPosts(refresh = true) {
    if (this.data.loading) return
    if (!refresh && !this.data.hasMore) return
    this.setData({ loading: true })
    const current = refresh ? 1 : this.data.current
    try {
      const res = await get('/post/list', { current, size: 10 })
      const records = res.data.records || []
      this.setData({
        posts: refresh ? records : [...this.data.posts, ...records],
        current: current + 1,
        hasMore: records.length === 10
      })
    } catch (e) {}
    this.setData({ loading: false })
  },

  goToDetail(e) {
    wx.navigateTo({ url: `/pages/post-detail/post-detail?id=${e.currentTarget.dataset.id}` })
  },

  createPost() {
    if (!app.checkLogin()) return
    wx.showModal({
      title: '发布帖子',
      editable: true,
      placeholderText: '请输入内容...',
      success: async (res) => {
        if (res.confirm && res.content) {
          try {
            await post('/post', { title: res.content.substring(0, 20), content: res.content })
            wx.showToast({ title: '发布成功', icon: 'success' })
            this.loadPosts()
          } catch (e) {}
        }
      }
    })
  },

  async likePost(e) {
    const id = e.currentTarget.dataset.id
    try {
      await post(`/post/${id}/like`)
      this.loadPosts()
    } catch (e) {}
  },

  onReachBottom() {
    this.loadPosts(false)
  },

  onPullDownRefresh() {
    this.loadPosts().then(() => wx.stopPullDownRefresh())
  }
})
