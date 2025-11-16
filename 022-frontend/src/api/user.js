import request from '@/utils/request'

// 用户登录
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

// 用户注册
export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

// 获取用户信息
export function getUserInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

// 更新用户信息
export function updateUserInfo(data) {
  return request({
    url: '/user/update',
    method: 'put',
    data
  })
}

// 修改密码
export function updatePassword(data) {
  return request({
    url: '/user/password',
    method: 'put',
    data
  })
}

// 获取用户列表（管理员）
export function getUserList(params) {
  return request({
    url: '/user/list',
    method: 'get',
    params
  })
}

// 更新用户状态（管理员）
export function updateUserStatus(userId, status) {
  return request({
    url: `/user/${userId}/status`,
    method: 'put',
    data: { status }
  })
}

// 调整用户信用分（管理员）
export function adjustCreditScore(userId, data) {
  return request({
    url: `/user/${userId}/credit`,
    method: 'put',
    data
  })
}