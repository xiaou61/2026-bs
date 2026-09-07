import request from '@/utils/request'

export const uploadResource = (data) => {
  return request({
    url: '/resource/upload',
    method: 'post',
    data
  })
}

export const getResourceList = (params) => {
  return request({
    url: '/resource/list',
    method: 'get',
    params
  })
}

export const getResourceDetail = (id) => {
  return request({
    url: `/resource/${id}`,
    method: 'get'
  })
}

export const downloadResource = (id) => {
  return request({
    url: `/resource/download/${id}`,
    method: 'post'
  })
}

export const rateResource = (data) => {
  return request({
    url: '/resource/rate',
    method: 'post',
    data
  })
}

export const getResourceRatings = (resourceId) => {
  return request({
    url: `/resource/ratings/${resourceId}`,
    method: 'get'
  })
}

export const getMyResources = (params) => {
  return request({
    url: '/resource/my',
    method: 'get',
    params
  })
}

