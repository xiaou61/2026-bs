import request from '../utils/request'

export const createOrder = (data) => {
  return request({
    url: '/orders',
    method: 'post',
    data
  })
}

export const getOrderSquare = (params) => {
  return request({
    url: '/orders',
    method: 'get',
    params
  })
}

export const getOrderDetail = (id) => {
  return request({
    url: `/orders/${id}`,
    method: 'get'
  })
}

export const acceptOrder = (id) => {
  return request({
    url: `/orders/${id}/accept`,
    method: 'post'
  })
}

export const uploadPickupImage = (id, image) => {
  return request({
    url: `/orders/${id}/pickup`,
    method: 'post',
    data: image,
    headers: {
      'Content-Type': 'text/plain'
    }
  })
}

export const uploadDeliveryImage = (id, image) => {
  return request({
    url: `/orders/${id}/deliver`,
    method: 'post',
    data: image,
    headers: {
      'Content-Type': 'text/plain'
    }
  })
}

export const confirmOrder = (id) => {
  return request({
    url: `/orders/${id}/confirm`,
    method: 'post'
  })
}

export const cancelOrder = (id, reason) => {
  return request({
    url: `/orders/${id}/cancel`,
    method: 'post',
    data: reason,
    headers: {
      'Content-Type': 'text/plain'
    }
  })
}

export const getMyPublishedOrders = (params) => {
  return request({
    url: '/orders/my/published',
    method: 'get',
    params
  })
}

export const getMyAcceptedOrders = (params) => {
  return request({
    url: '/orders/my/accepted',
    method: 'get',
    params
  })
}

