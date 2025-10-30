import request from '@/utils/request'

export const getPointsRank = (params) => {
  return request({
    url: '/rank/points',
    method: 'get',
    params
  })
}

