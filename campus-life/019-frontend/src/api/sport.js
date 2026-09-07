import request from '@/utils/request'

export const createRecord = (data) => {
  return request({
    url: '/sport/record',
    method: 'post',
    data
  })
}

export const getRecords = (params) => {
  return request({
    url: '/sport/records',
    method: 'get',
    params
  })
}

export const getRecordDetail = (id) => {
  return request({
    url: `/sport/record/${id}`,
    method: 'get'
  })
}

export const saveTrack = (data) => {
  return request({
    url: '/sport/track',
    method: 'post',
    data
  })
}

export const getTrack = (recordId) => {
  return request({
    url: `/sport/track/${recordId}`,
    method: 'get'
  })
}

export const getStats = () => {
  return request({
    url: '/sport/stats',
    method: 'get'
  })
}

