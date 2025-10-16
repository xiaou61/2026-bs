import request from '@/utils/request'

export function publishIdle(data) {
  return request({
    url: '/idle/publish',
    method: 'post',
    data
  })
}

export function getIdleList(params) {
  return request({
    url: '/idle/list',
    method: 'get',
    params
  })
}

export function getIdleDetail(id) {
  return request({
    url: `/idle/${id}`,
    method: 'get'
  })
}

export function updateIdle(id, data) {
  return request({
    url: `/idle/${id}`,
    method: 'put',
    data
  })
}

export function deleteIdle(id) {
  return request({
    url: `/idle/${id}`,
    method: 'delete'
  })
}

export function getMyIdle(params) {
  return request({
    url: '/idle/my',
    method: 'get',
    params
  })
}

export function searchIdle(params) {
  return request({
    url: '/idle/search',
    method: 'get',
    params
  })
}

