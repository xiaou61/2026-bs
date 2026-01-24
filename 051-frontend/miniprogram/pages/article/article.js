const api = require('../../api/index')

Page({
  data: {
    article: null
  },

  onLoad(options) {
    if (options.id) {
      this.loadArticle(options.id)
    }
  },

  async loadArticle(id) {
    const article = await api.getArticleDetail(id)
    this.setData({ article })
    wx.setNavigationBarTitle({ title: article.title })
    api.recordLearn(id, 100)
  },

  async handleLike() {
    if (!wx.getStorageSync('token')) {
      wx.showToast({ title: '请先登录', icon: 'none' })
      return
    }
    if (this.data.article.isLiked) {
      wx.showToast({ title: '已点赞', icon: 'none' })
      return
    }
    await api.likeArticle(this.data.article.id)
    this.setData({
      'article.isLiked': true,
      'article.likeCount': this.data.article.likeCount + 1
    })
    wx.showToast({ title: '点赞成功', icon: 'success' })
  },

  async handleFavorite() {
    if (!wx.getStorageSync('token')) {
      wx.showToast({ title: '请先登录', icon: 'none' })
      return
    }
    const { article } = this.data
    if (article.isFavorite) {
      await api.removeFavorite(article.id)
      this.setData({ 'article.isFavorite': false })
      wx.showToast({ title: '取消收藏', icon: 'success' })
    } else {
      await api.addFavorite(article.id)
      this.setData({ 'article.isFavorite': true })
      wx.showToast({ title: '收藏成功', icon: 'success' })
    }
  }
})
