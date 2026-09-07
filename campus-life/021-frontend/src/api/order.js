import request from '@/utils/request'

export const orderApi = {
  createOrder: (data) => {
    return request({
      url: '/order/create',
      method: 'post',
      data
    })
  },

  getMySellOrders: (params) => {
    return request({
      url: '/order/my/sell',
      method: 'get',
      params
    })
  },

  getMyBuyOrders: (params) => {
    return request({
      url: '/order/my/buy',
      method: 'get',
      params
    })
  },

  getOrderDetail: (id) => {
    return request({
      url: `/order/${id}`,
      method: 'get'
    })
  },

  completeOrder: (id) => {
    return request({
      url: `/order/${id}/complete`,
      method: 'put'
    })
  },

  cancelOrder: (id) => {
    return request({
      url: `/order/${id}/cancel`,
      method: 'put'
    })
  },

  rateOrder: (id, data) => {
    return request({
      url: `/order/${id}/rate`,
      method: 'post',
      data
    })
  }
}
