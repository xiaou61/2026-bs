import request from '@/utils/request'

export const searchVideos = (params) => {
  return request({
    url: '/search/video',
    method: 'get',
    params
  })
}

export const searchUsers = (params) => {
  return request({
    url: '/search/user',
    method: 'get',
    params
  })
}

