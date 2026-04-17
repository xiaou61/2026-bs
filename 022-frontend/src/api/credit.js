import request from '@/utils/request'

export function getMyCreditRecords(params) {
  return request({
    url: '/credit/my',
    method: 'get',
    params
  })
}

export function getCreditRecordPage(params) {
  return request({
    url: '/credit/page',
    method: 'get',
    params
  })
}

export function addCreditRecord(data) {
  return request({
    url: '/credit/record',
    method: 'post',
    data
  })
}

export function getCreditStatistics(userId, params) {
  return request({
    url: `/credit/statistics/${userId}`,
    method: 'get',
    params
  })
}
