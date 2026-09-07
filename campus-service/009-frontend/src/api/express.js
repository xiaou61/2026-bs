import request from '@/utils/request'

export const expressIn = (data) => {
  return request({
    url: '/express/in',
    method: 'post',
    data
  })
}

export const getExpressList = (params) => {
  return request({
    url: '/express/list',
    method: 'get',
    params
  })
}

export const getExpressDetail = (id) => {
  return request({
    url: `/express/${id}`,
    method: 'get'
  })
}

export const verifyPickup = (pickupCode) => {
  return request({
    url: '/express/verify-pickup',
    method: 'post',
    data: { pickupCode }
  })
}

export const pickup = (pickupCode) => {
  return request({
    url: '/express/pickup',
    method: 'post',
    data: { pickupCode }
  })
}

export const getMyPackages = () => {
  return request({
    url: '/express/my-packages',
    method: 'get'
  })
}

export const getMyHistory = (params) => {
  return request({
    url: '/express/my-history',
    method: 'get',
    params
  })
}

