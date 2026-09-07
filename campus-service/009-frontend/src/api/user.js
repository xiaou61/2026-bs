import request from '@/utils/request'

export const getUserList = (params) => {
  return request({
    url: '/user/list',
    method: 'get',
    params
  })
}

export const getUserDetail = (id) => {
  return request({
    url: `/user/${id}`,
    method: 'get'
  })
}

export const updateUser = (id, data) => {
  return request({
    url: `/user/${id}`,
    method: 'put',
    data
  })
}

export const updateUserStatus = (id, status) => {
  return request({
    url: `/user/${id}/status`,
    method: 'put',
    params: { status }
  })
}

export const updateUserRole = (id, role) => {
  return request({
    url: `/user/${id}/role`,
    method: 'put',
    params: { role }
  })
}

export const updatePassword = (id, data) => {
  return request({
    url: `/user/${id}/password`,
    method: 'put',
    data
  })
}

