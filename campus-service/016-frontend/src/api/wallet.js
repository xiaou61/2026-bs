import request from '../utils/request'

export const getBalance = () => {
  return request({
    url: '/wallet/balance',
    method: 'get'
  })
}

export const recharge = (amount) => {
  return request({
    url: '/wallet/recharge',
    method: 'post',
    data: { amount }
  })
}

export const withdraw = (amount) => {
  return request({
    url: '/wallet/withdraw',
    method: 'post',
    data: { amount }
  })
}

export const getTransactions = (params) => {
  return request({
    url: '/wallet/transactions',
    method: 'get',
    params
  })
}

