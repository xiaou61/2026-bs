import request from '@/utils/request'

export function getNearbyItems(params) {
  return request({
    url: '/shared/nearby',
    method: 'get',
    params
  })
}

export function getItemDetail(id) {
  return request({
    url: `/shared/${id}`,
    method: 'get'
  })
}

export function scanItem(data) {
  return request({
    url: '/shared/scan',
    method: 'post',
    data
  })
}

export function rentItem(data) {
  return request({
    url: '/shared/rent',
    method: 'post',
    data
  })
}

export function returnItem(data) {
  return request({
    url: '/shared/return',
    method: 'post',
    data
  })
}

export function getBikes(params) {
  return request({
    url: '/shared/bikes',
    method: 'get',
    params
  })
}

export function getChargers(params) {
  return request({
    url: '/shared/chargers',
    method: 'get',
    params
  })
}

export function getUmbrellas(params) {
  return request({
    url: '/shared/umbrellas',
    method: 'get',
    params
  })
}

