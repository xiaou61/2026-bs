import request from '@/utils/request'

export const getHotTopics = () => {
  return request({
    url: '/topic/hot',
    method: 'get'
  })
}

export const getRecommendTopics = () => {
  return request({
    url: '/topic/recommend',
    method: 'get'
  })
}

export const getTopicDetail = (id) => {
  return request({
    url: `/topic/${id}`,
    method: 'get'
  })
}

export const getTopicVideos = (id, params) => {
  return request({
    url: `/topic/${id}/videos`,
    method: 'get',
    params
  })
}

export const searchTopics = (keyword) => {
  return request({
    url: '/topic/search',
    method: 'get',
    params: { keyword }
  })
}

