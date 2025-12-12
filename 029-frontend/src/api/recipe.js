import request from './request'

// 获取食谱列表
export function getRecipeList(params) {
  return request.get('/recipe/list', { params })
}

// 获取食谱详情
export function getRecipeDetail(id) {
  return request.get(`/recipe/${id}/detail`)
}

// 创建食谱
export function createRecipe(data) {
  return request.post('/recipe/create', data)
}

// 更新食谱
export function updateRecipe(id, data) {
  return request.put(`/recipe/${id}`, data)
}

// 删除食谱
export function deleteRecipe(id) {
  return request.delete(`/recipe/${id}`)
}

// 搜索食谱
export function searchRecipes(keyword) {
  return request.get('/recipe/search', { params: { keyword } })
}

// 收藏食谱
export function collectRecipe(id) {
  return request.post(`/recipe/${id}/collect`)
}

// 取消收藏食谱
export function uncollectRecipe(id) {
  return request.delete(`/recipe/${id}/collect`)
}
