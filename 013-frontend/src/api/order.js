import request from '@/utils/request'

export function getOrderList(params) {
  return request({
    url: '/order/list',
    method: 'get',
    params
  })
}

export function getOrderDetail(id) {
  return request({
    url: `/order/${id}`,
    method: 'get'
  })
}

export function cancelOrder(id) {
  return request({
    url: `/order/${id}/cancel`,
    method: 'put'
  })
}

export function getOngoingOrders() {
  return request({
    url: '/order/ongoing',
    method: 'get'
  })
}

export function getHistoryOrders() {
  return request({
    url: '/order/history',
    method: 'get'
  })
}

