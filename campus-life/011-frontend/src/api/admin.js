import request from '@/utils/request'

export const getOverview = () => {
  return request({
    url: '/admin/stats/overview',
    method: 'get'
  })
}

export const getUserList = (params) => {
  return request({
    url: '/admin/user/list',
    method: 'get',
    params
  })
}

export const updateUserStatus = (id, status) => {
  return request({
    url: `/admin/user/${id}/status`,
    method: 'put',
    params: { status }
  })
}

export const getVideoList = (params) => {
  return request({
    url: '/admin/video/list',
    method: 'get',
    params
  })
}

export const auditVideo = (id, data) => {
  return request({
    url: `/admin/video/${id}/audit`,
    method: 'put',
    data
  })
}

export const deleteVideo = (id) => {
  return request({
    url: `/admin/video/${id}`,
    method: 'delete'
  })
}

export const getTopicList = (params) => {
  return request({
    url: '/admin/topic/list',
    method: 'get',
    params
  })
}

export const createTopic = (data) => {
  return request({
    url: '/admin/topic',
    method: 'post',
    data
  })
}

export const updateTopic = (id, data) => {
  return request({
    url: `/admin/topic/${id}`,
    method: 'put',
    data
  })
}

export const deleteTopic = (id) => {
  return request({
    url: `/admin/topic/${id}`,
    method: 'delete'
  })
}

export const getCommentList = (params) => {
  return request({
    url: '/admin/comment/list',
    method: 'get',
    params
  })
}

export const deleteComment = (id) => {
  return request({
    url: `/admin/comment/${id}`,
    method: 'delete'
  })
}

export const getReportList = (params) => {
  return request({
    url: '/admin/report/list',
    method: 'get',
    params
  })
}

export const handleReport = (id, data) => {
  return request({
    url: `/admin/report/${id}/handle`,
    method: 'put',
    data
  })
}

