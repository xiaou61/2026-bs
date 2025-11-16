import request from '@/utils/request'

// 创建预约
export function createReservation(data) {
  return request({
    url: '/reservation/create',
    method: 'post',
    data
  })
}

// 获取我的预约列表
export function getMyReservations(params) {
  return request({
    url: '/reservation/my',
    method: 'get',
    params
  })
}

// 获取预约详情
export function getReservationDetail(reservationId) {
  return request({
    url: `/reservation/${reservationId}`,
    method: 'get'
  })
}

// 取消预约
export function cancelReservation(reservationId) {
  return request({
    url: `/reservation/${reservationId}/cancel`,
    method: 'put'
  })
}

// 签到
export function checkIn(reservationId) {
  return request({
    url: `/reservation/${reservationId}/checkin`,
    method: 'put'
  })
}

// 结束使用
export function endReservation(reservationId) {
  return request({
    url: `/reservation/${reservationId}/end`,
    method: 'put'
  })
}

// 获取所有预约列表（管理员）
export function getAllReservations(params) {
  return request({
    url: '/reservation/all',
    method: 'get',
    params
  })
}

// 获取预约统计
export function getReservationStatistics(params) {
  return request({
    url: '/reservation/statistics',
    method: 'get',
    params
  })
}

// 扫码验证
export function verifyQRCode(data) {
  return request({
    url: '/reservation/verify-qrcode',
    method: 'post',
    data
  })
}