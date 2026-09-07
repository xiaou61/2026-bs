const api = require('../../api/index')

Page({
  data: {
    categories: [],
    articles: [],
    currentCategoryId: '',
    page: 1,
    hasMore: true
  },

  onLoad(options) {
    if (options.id) {
      this.setData({ currentCategoryId: Number(options.id) })
    }
    if (options.name) {
      wx.setNavigationBarTitle({ title: options.name })
    }
    this.loadCategories()
    this.loadArticles()
  },

  async loadCategories() {
    const categories = await api.getCategoryList()
    this.setData({ categories })
  },

  async loadArticles() {
    const { currentCategoryId, page } = this.data
    const params = { page, size: 10 }
    if (currentCategoryId) params.categoryId = currentCategoryId
    
    const res = await api.getArticleList(params)
    const articles = page === 1 ? res.records : [...this.data.articles, ...res.records]
    this.setData({
      articles,
      hasMore: res.records.length === 10
    })
  },

  switchCategory(e) {
    const id = e.currentTarget.dataset.id
    this.setData({ currentCategoryId: id, page: 1, articles: [] })
    this.loadArticles()
  },

  goArticle(e) {
    wx.navigateTo({
      url: '/pages/article/article?id=' + e.currentTarget.dataset.id
    })
  },

  onReachBottom() {
    if (this.data.hasMore) {
      this.setData({ page: this.data.page + 1 })
      this.loadArticles()
    }
  }
})
