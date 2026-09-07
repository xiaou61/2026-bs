const api = require('../../api/index')

Page({
  data: {
    categories: [],
    articles: []
  },

  onLoad() {
    this.loadData()
  },

  onShow() {
    this.loadData()
  },

  async loadData() {
    try {
      const categories = await api.getCategoryList()
      const articlesRes = await api.getArticleList({ page: 1, size: 6 })
      this.setData({
        categories,
        articles: articlesRes.records || []
      })
    } catch (e) {
      console.error(e)
    }
  },

  goCategory(e) {
    const { id, name } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/category/category?id=${id}&name=${name}`
    })
  },

  goArticle(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/article/article?id=${id}`
    })
  },

  goMore() {
    wx.navigateTo({
      url: '/pages/category/category'
    })
  }
})
