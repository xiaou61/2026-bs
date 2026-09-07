import request from '@/utils/request'

export function createBooking(data: {
  homestayId: number
  roomTypeId: number
  checkInDate: string
  checkOutDate: string
  guests?: number
  contactName: string
  contactPhone: string
  remark?: string
}) {
  return request.post('/booking/create', data)
}

export function cancelBooking(id: number, reason?: string) {
  return request.post(`/booking/cancel/${id}`, null, { params: { reason } })
}

export function getMyBookings(params: { current?: number; size?: number; status?: number }) {
  return request.get('/booking/my', { params })
}

export function getBookingDetail(id: number) {
  return request.get(`/booking/detail/${id}`)
}
