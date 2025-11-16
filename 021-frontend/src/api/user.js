import request from '@/utils/request'

export const userApi = {
  // 用户登录
  login: (data) => {
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  },

  // 用户注册
  register: (data) => {
    return request({
      url: '/user/register',
      method: 'post',
      data
    })
  },

  // 获取用户信息
  getUserInfo: () => {
    return request({
      url: '/user/info',
      method: 'get'
    })
  },

  // 更新用户信息
  updateUserInfo: (data) => {
    return request({
      url: '/user/update',
      method: 'put',
      data
    })
  },

  // 查看用户资料
  getUserProfile: (id) => {
    return request({
      url: `/user/profile/${id}`,
      method: 'get'
    })
  }
}