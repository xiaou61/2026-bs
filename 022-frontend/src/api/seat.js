import request from '@/utils/request'

// 获取座位列表
export function getSeats(params) {
  return request({
    url: '/seat/list',
    method: 'get',
    params
  })
}

// 获取座位详情
export function getSeatDetail(seatId) {
  return request({
    url: `/seat/${seatId}`,
    method: 'get'
  })
}

// 批量创建座位（管理员）
export function createSeats(data) {
  return request({
    url: '/seat/batch-create',
    method: 'post',
    data
  })
}

// 更新座位信息（管理员）
export function updateSeat(seatId, data) {
  return request({
    url: `/seat/${seatId}`,
    method: 'put',
    data
  })
}

// 批量更新座位状态（管理员）
export function updateSeatsStatus(data) {
  return request({
    url: '/seat/batch-status',
    method: 'put',
    data
  })
}

// 删除座位（管理员）
export function deleteSeat(seatId) {
  return request({
    url: `/seat/${seatId}`,
    method: 'delete'
  })
}

// 获取座位实时状态
export function getSeatsRealtime(roomId) {
  return request({
    url: `/seat/realtime/${roomId}`,
    method: 'get'
  })
}