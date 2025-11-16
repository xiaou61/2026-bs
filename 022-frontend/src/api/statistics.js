import request from '@/utils/request'

// 获取使用统计数据
export function getUsageStatistics(params) {
  return request({
    url: '/statistics/usage',
    method: 'get',
    params
  })
}

// 获取今日统计
export function getTodayStatistics() {
  return request({
    url: '/statistics/today',
    method: 'get'
  })
}

// 获取趋势数据
export function getTrendData(params) {
  return request({
    url: '/statistics/trend',
    method: 'get',
    params
  })
}

// 获取热门座位
export function getPopularSeats(params) {
  return request({
    url: '/statistics/popular-seats',
    method: 'get',
    params
  })
}

// 获取高峰时段
export function getPeakHours(params) {
  return request({
    url: '/statistics/peak-hours',
    method: 'get',
    params
  })
}

// 导出统计报表
export function exportStatistics(params) {
  return request({
    url: '/statistics/export',
    method: 'get',
    params,
    responseType: 'blob'
  })
}