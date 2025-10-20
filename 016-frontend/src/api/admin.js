import request from '../utils/request'

export const getStatistics = () => {
  return request({
    url: '/admin/statistics',
    method: 'get'
  })
}

export const getUserList = (params) => {
  return request({
    url: '/admin/users',
    method: 'get',
    params
  })
}

export const updateUserStatus = (id, status) => {
  return request({
    url: `/admin/users/${id}/status`,
    method: 'put',
    data: status,
    headers: {
      'Content-Type': 'text/plain'
    }
  })
}

export const getOrderList = (params) => {
  return request({
    url: '/admin/orders',
    method: 'get',
    params
  })
}

export const handleOrder = (id, action) => {
  return request({
    url: `/admin/orders/${id}/handle`,
    method: 'put',
    data: action,
    headers: {
      'Content-Type': 'text/plain'
    }
  })
}

export const getComplaintList = (params) => {
  return request({
    url: '/admin/complaints',
    method: 'get',
    params
  })
}

export const handleComplaint = (id, data) => {
  return request({
    url: `/admin/complaints/${id}/handle`,
    method: 'put',
    data
  })
}

export const getTransactionList = (params) => {
  return request({
    url: '/admin/transactions',
    method: 'get',
    params
  })
}

