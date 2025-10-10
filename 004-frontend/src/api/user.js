import request from '@/utils/request'

export const getUserInfo = () => {
  return request.get('/user/info')
}

export const updateUserInfo = (data) => {
  return request.put('/user/update', data)
}

export const searchUsers = (keyword) => {
  return request.get('/user/search', { params: { keyword } })
}

