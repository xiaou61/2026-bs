import request from '@/utils/request'

export const getVenueList = (params) => {
  return request({
    url: '/venue/list',
    method: 'get',
    params
  })
}

export const getVenueDetail = (id) => {
  return request({
    url: `/venue/${id}`,
    method: 'get'
  })
}

export const createBooking = (data) => {
  return request({
    url: '/booking/create',
    method: 'post',
    data
  })
}

export const getMyBookings = (params) => {
  return request({
    url: '/booking/my',
    method: 'get',
    params
  })
}

export const checkinBooking = (id) => {
  return request({
    url: `/booking/${id}/checkin`,
    method: 'put'
  })
}

export const cancelBooking = (id, data) => {
  return request({
    url: `/booking/${id}/cancel`,
    method: 'put',
    data
  })
}

