import request from './request'

// 获取食材详情
export function getIngredientDetail(id) {
  return request.get(`/ingredient/${id}/detail`)
}

// 按分类获取食材
export function getIngredientsByCategory(category, params) {
  return request.get(`/ingredient/category/${category}`, { params })
}

// 搜索食材
export function searchIngredients(keyword) {
  return request.get('/ingredient/search', { params: { keyword } })
}

// 获取食材禁忌
export function getIngredientTaboos(id) {
  return request.get(`/ingredient/${id}/taboos`)
}

// 获取禁忌食材
export function getTabooedIngredients(id) {
  return request.get(`/ingredient/${id}/tabooed-ingredients`)
}
