const { request } = require('../utils/request')

module.exports = {
  login: (data) => request({ url: '/api/user/login', method: 'POST', data }),
  getUserInfo: () => request({ url: '/api/user/info' }),
  updateUserInfo: (data) => request({ url: '/api/user/info', method: 'PUT', data }),
  getUserStats: () => request({ url: '/api/user/stats' }),

  getCategoryList: () => request({ url: '/api/category/list' }),
  getArticleList: (params) => request({ url: '/api/article/list', data: params }),
  getArticleDetail: (id) => request({ url: '/api/article/' + id }),
  likeArticle: (id) => request({ url: '/api/article/like/' + id, method: 'POST' }),
  recordLearn: (articleId, progress) => request({ url: '/api/article/learn', method: 'POST', data: { articleId, progress } }),

  addFavorite: (articleId) => request({ url: '/api/favorite/add', method: 'POST', data: { articleId } }),
  removeFavorite: (articleId) => request({ url: '/api/favorite/' + articleId, method: 'DELETE' }),
  getFavoriteList: (params) => request({ url: '/api/favorite/list', data: params }),

  getDailyQuestions: () => request({ url: '/api/question/daily' }),
  getCategoryQuestions: (categoryId) => request({ url: '/api/question/category/' + categoryId }),
  submitAnswer: (data) => request({ url: '/api/answer/submit', method: 'POST', data }),
  getWrongList: (params) => request({ url: '/api/answer/wrong-list', data: params }),
  getRankList: () => request({ url: '/api/rank/list' }),

  getNewsList: (params) => request({ url: '/api/news/list', data: params }),
  getNewsDetail: (id) => request({ url: '/api/news/' + id }),

  getQaList: (params) => request({ url: '/api/qa/list', data: params }),
  getQaDetail: (id) => request({ url: '/api/qa/' + id }),
  createQaPost: (data) => request({ url: '/api/qa/post', method: 'POST', data }),
  createQaReply: (data) => request({ url: '/api/qa/reply', method: 'POST', data }),
  likeReply: (replyId) => request({ url: '/api/qa/like/' + replyId, method: 'POST' })
}
