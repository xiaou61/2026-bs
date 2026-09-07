import request from '@/utils/request'

export function toggleFavorite(data) {
  return request({
    url: '/favorite/toggle',
    method: 'post',
    data
  })
}

export function getFavoriteStatus(params) {
  return request({
    url: '/favorite/status',
    method: 'get',
    params
  })
}

export function getFavoriteList(params) {
  return request({
    url: '/favorite/list',
    method: 'get',
    params
  })
}
