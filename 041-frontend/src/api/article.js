import request from './request'

export function getArticles(category) {
  return request({
    url: '/articles',
    method: 'get',
    params: { category }
  })
}

export function getArticleById(id) {
  return request({
    url: `/articles/${id}`,
    method: 'get'
  })
}
