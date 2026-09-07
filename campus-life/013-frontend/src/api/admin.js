import request from '@/utils/request'

export function getUserList(params) {
  return request({
    url: '/admin/user/list',
    method: 'get',
    params
  })
}

export function updateUserStatus(id, data) {
  return request({
    url: `/admin/user/${id}/status`,
    method: 'put',
    data
  })
}

export function getAuthList(params) {
  return request({
    url: '/admin/auth/list',
    method: 'get',
    params
  })
}

export function auditAuth(id, data) {
  return request({
    url: `/admin/auth/${id}/audit`,
    method: 'put',
    data
  })
}

export function getIdleAuditList(params) {
  return request({
    url: '/admin/idle/list',
    method: 'get',
    params
  })
}

export function auditIdle(id, data) {
  return request({
    url: `/admin/idle/${id}/audit`,
    method: 'put',
    data
  })
}

export function getStatsOverview() {
  return request({
    url: '/admin/stats/overview',
    method: 'get'
  })
}

export function getSharedItemList(params) {
  return request({
    url: '/admin/shared/list',
    method: 'get',
    params
  })
}

export function addSharedItem(data) {
  return request({
    url: '/admin/shared/add',
    method: 'post',
    data
  })
}

export function updateSharedItem(id, data) {
  return request({
    url: `/admin/shared/${id}`,
    method: 'put',
    data
  })
}

