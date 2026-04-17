import request from '../utils/request'

export const getUserProfile = () => {
  return request({
    url: '/user/profile',
    method: 'get'
  })
}

export const updateUserProfile = (data) => {
  return request({
    url: '/user/profile',
    method: 'put',
    data
  })
}

export const updatePassword = (data) => {
  return request({
    url: '/user/password',
    method: 'put',
    data
  })
}
