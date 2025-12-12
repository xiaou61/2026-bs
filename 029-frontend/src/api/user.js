import request from './request'

// 用户注册
export function register(data) {
  return request.post('/user/register', data)
}

// 用户登录
export function login(data) {
  return request.post('/user/login', data)
}

// 获取用户信息
export function getUserInfo() {
  return request.get('/user/info')
}

// 更新用户信息
export function updateUserInfo(data) {
  return request.put('/user/info', data)
}

// 获取用户的食谱列表
export function getMyRecipes() {
  return request.get('/recipe/my-recipes')
}

// 获取用户的话题列表
export function getMyTopics() {
  return request.get('/topic/my-topics')
}

// 获取收藏列表
export function getCollections(params) {
  return request.get('/collection/list', { params })
}

// 取消收藏
export function removeCollection(id) {
  return request.delete(`/collection/${id}`)
}
