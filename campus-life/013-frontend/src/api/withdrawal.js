import request from '@/utils/request'

export function applyWithdrawal(data) {
  return request({
    url: '/withdrawal/apply',
    method: 'post',
    data
  })
}

export function getWithdrawalList() {
  return request({
    url: '/withdrawal/list',
    method: 'get'
  })
}
