import request from '@/utils/request'

// 获取信用记录列表
export function getCreditRecords(params) {
  return request({
    url: '/credit/list',
    method: 'get',
    params
  })
}

// 获取我的信用记录
export function getMyCreditRecords(params) {
  return request({
    url: '/credit/my',
    method: 'get',
    params
  })
}

// 添加信用记录（管理员）
export function addCreditRecord(data) {
  return request({
    url: '/credit/add',
    method: 'post',
    data
  })
}

// 获取信用分统计
export function getCreditStatistics(userId) {
  return request({
    url: `/credit/statistics/${userId}`,
    method: 'get'
  })
}