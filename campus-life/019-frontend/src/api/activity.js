import request from '@/utils/request'

export const createActivity = (data) => {
  return request({
    url: '/activity/create',
    method: 'post',
    data
  })
}

export const getActivityList = (params) => {
  return request({
    url: '/activity/list',
    method: 'get',
    params
  })
}

export const getActivityDetail = (id) => {
  return request({
    url: `/activity/${id}`,
    method: 'get'
  })
}

export const joinActivity = (id) => {
  return request({
    url: `/activity/${id}/join`,
    method: 'post'
  })
}

export const cancelActivity = (id) => {
  return request({
    url: `/activity/${id}/cancel`,
    method: 'post'
  })
}

export const updateActivityStatus = (id, data) => {
  return request({
    url: `/activity/${id}/status`,
    method: 'put',
    data
  })
}

