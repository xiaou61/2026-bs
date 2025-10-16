import request from '@/utils/request'

export function chargeDeposit(data) {
  return request({
    url: '/payment/deposit/charge',
    method: 'post',
    data
  })
}

export function chargeBalance(data) {
  return request({
    url: '/payment/balance/charge',
    method: 'post',
    data
  })
}

export function getDepositRecord() {
  return request({
    url: '/deposit/record',
    method: 'get'
  })
}

export function getBalanceRecord() {
  return request({
    url: '/balance/record',
    method: 'get'
  })
}

