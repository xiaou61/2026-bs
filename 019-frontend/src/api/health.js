import request from '@/utils/request'

export const createHealthRecord = (data) => {
  return request({
    url: '/health/record',
    method: 'post',
    data
  })
}

export const getHealthRecords = (params) => {
  return request({
    url: '/health/records',
    method: 'get',
    params
  })
}

export const getHealthTrend = (params) => {
  return request({
    url: '/health/trend',
    method: 'get',
    params
  })
}

