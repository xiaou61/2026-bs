import request from '@/utils/request'

export const getCreatorStats = () => {
  return request({
    url: '/stats/creator',
    method: 'get'
  })
}

export const getVideoStats = (id) => {
  return request({
    url: `/stats/video/${id}`,
    method: 'get'
  })
}
