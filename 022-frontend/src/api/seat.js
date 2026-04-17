import request from '@/utils/request'

export function getSeats(params) {
  return request({
    url: '/seat/list',
    method: 'get',
    params
  })
}

export function getSeatPage(params) {
  return request({
    url: '/seat/page',
    method: 'get',
    params
  })
}

export function getSeatDetail(seatId) {
  return request({
    url: `/seat/${seatId}`,
    method: 'get'
  })
}

export function createSeat(data) {
  return request({
    url: '/seat',
    method: 'post',
    data
  })
}

export function batchCreateSeats(data) {
  return request({
    url: '/seat/batch-create',
    method: 'post',
    data
  })
}

export function updateSeat(seatId, data) {
  return request({
    url: `/seat/${seatId}`,
    method: 'put',
    data
  })
}

export function updateSeatStatus(seatId, status) {
  return request({
    url: `/seat/${seatId}/status`,
    method: 'put',
    params: { status }
  })
}

export function updateSeatsStatus(seatIds, status) {
  return request({
    url: '/seat/batch/status',
    method: 'put',
    params: { status },
    data: seatIds
  })
}

export function deleteSeat(seatId) {
  return request({
    url: `/seat/${seatId}`,
    method: 'delete'
  })
}

export function getSeatsRealtime(roomId) {
  return request({
    url: `/seat/realtime/${roomId}`,
    method: 'get'
  })
}
