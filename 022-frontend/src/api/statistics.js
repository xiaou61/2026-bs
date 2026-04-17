import request from '@/utils/request'

export function getDashboardData() {
  return request({
    url: '/statistics/dashboard',
    method: 'get'
  })
}

export function getOverallStatistics(params) {
  return request({
    url: '/statistics/overall',
    method: 'get',
    params
  })
}

export function getRoomStatistics(roomId, params) {
  return request({
    url: `/statistics/room/${roomId}`,
    method: 'get',
    params
  })
}

export function getDateRangeStatistics(params) {
  return request({
    url: '/statistics/date-range',
    method: 'get',
    params
  })
}

export function getPeakHoursStatistics(roomId, params) {
  return request({
    url: `/statistics/peak-hours/${roomId}`,
    method: 'get',
    params
  })
}

export function generateStatistics() {
  return request({
    url: '/statistics/generate',
    method: 'post'
  })
}
