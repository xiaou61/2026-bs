import request from '@/utils/request'

export function createReservation(data) {
  return request({
    url: '/reservation',
    method: 'post',
    data
  })
}

export function getMyReservations(params) {
  return request({
    url: '/reservation/my',
    method: 'get',
    params
  })
}

export function getReservationDetail(reservationId) {
  return request({
    url: `/reservation/${reservationId}`,
    method: 'get'
  })
}

export function cancelReservation(reservationId) {
  return request({
    url: `/reservation/${reservationId}/cancel`,
    method: 'put'
  })
}

export function checkIn(reservationId) {
  return request({
    url: `/reservation/${reservationId}/checkin`,
    method: 'put'
  })
}

export function endReservation(reservationId) {
  return request({
    url: `/reservation/${reservationId}/end`,
    method: 'put'
  })
}

export function getAllReservations(params) {
  return request({
    url: '/reservation/page',
    method: 'get',
    params
  })
}

export function getCurrentReservation(seatId) {
  return request({
    url: `/reservation/seat/${seatId}/current`,
    method: 'get'
  })
}
