import request from '@/utils/request'

export const getOverview = () => {
  return request({
    url: '/stats/overview',
    method: 'get'
  })
}

export const getTrend = (days = 7) => {
  return request({
    url: '/stats/trend',
    method: 'get',
    params: { days }
  })
}

export const getCompanyRank = () => {
  return request({
    url: '/stats/company-rank',
    method: 'get'
  })
}

export const getStationRank = () => {
  return request({
    url: '/stats/station-rank',
    method: 'get'
  })
}

