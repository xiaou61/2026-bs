import request from '@/utils/request'

export const getUserList = (params) => {
  return request.get('/users', { params })
}

export const getUserById = (id) => {
  return request.get(`/users/${id}`)
}

export const updateUser = (id, data) => {
  return request.put(`/users/${id}`, data)
}

export const updateUserStatus = (id, status) => {
  return request.put(`/users/${id}/status`, { status })
}

export const updatePassword = (id, data) => {
  return request.put(`/users/${id}/password`, data)
}

export const getUserStats = (id) => {
  return request.get(`/users/${id}/stats`)
}

