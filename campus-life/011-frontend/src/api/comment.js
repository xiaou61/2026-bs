import request from '@/utils/request'

export const publishComment = (data) => {
  return request({
    url: '/comment/publish',
    method: 'post',
    data
  })
}

export const getCommentList = (params) => {
  return request({
    url: '/comment/list',
    method: 'get',
    params
  })
}

export const deleteComment = (id) => {
  return request({
    url: `/comment/${id}`,
    method: 'delete'
  })
}

export const likeComment = (id) => {
  return request({
    url: `/comment/${id}/like`,
    method: 'post'
  })
}

export const unlikeComment = (id) => {
  return request({
    url: `/comment/${id}/like`,
    method: 'delete'
  })
}

export const getReplies = (id) => {
  return request({
    url: `/comment/${id}/replies`,
    method: 'get'
  })
}

