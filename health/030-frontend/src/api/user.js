import request from '../utils/request'

export const login = (data) => {
  return request.post('/users/login', data)
}

export const register = (data) => {
  return request.post('/users/register', data)
}

export const getUserInfo = () => {
  return request.get('/users/info')
}

export const updateUserInfo = (data) => {
  return request.put('/users/info', data)
}
