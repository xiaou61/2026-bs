import request from '@/utils/request'

export const getStationList = (params) => {
  return request({
    url: '/station/list',
    method: 'get',
    params
  })
}

export const getAllStations = () => {
  return request({
    url: '/station/all',
    method: 'get'
  })
}

export const getStationDetail = (id) => {
  return request({
    url: `/station/${id}`,
    method: 'get'
  })
}

export const addStation = (data) => {
  return request({
    url: '/station',
    method: 'post',
    data
  })
}

export const updateStation = (id, data) => {
  return request({
    url: `/station/${id}`,
    method: 'put',
    data
  })
}

export const deleteStation = (id) => {
  return request({
    url: `/station/${id}`,
    method: 'delete'
  })
}

export const getStationStats = (id) => {
  return request({
    url: `/station/${id}/stats`,
    method: 'get'
  })
}

