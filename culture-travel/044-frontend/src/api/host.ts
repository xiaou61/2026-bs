import request from '@/utils/request'

export function getHostHomestays(params: { current?: number; size?: number }) {
  return request.get('/host/homestay/list', { params })
}

export function addHomestay(data: any) {
  return request.post('/host/homestay/add', data)
}

export function updateHomestay(data: any) {
  return request.put('/host/homestay/update', data)
}

export function deleteHomestay(id: number) {
  return request.delete(`/host/homestay/delete/${id}`)
}

export function addRoomType(data: any) {
  return request.post('/host/roomtype/add', data)
}

export function updateRoomType(data: any) {
  return request.put('/host/roomtype/update', data)
}

export function deleteRoomType(id: number) {
  return request.delete(`/host/roomtype/delete/${id}`)
}

export function getHostBookings(params: { current?: number; size?: number; status?: number }) {
  return request.get('/host/booking/list', { params })
}

export function confirmBooking(id: number) {
  return request.post(`/host/booking/confirm/${id}`)
}

export function replyReview(id: number, reply: string) {
  return request.post(`/host/review/reply/${id}`, null, { params: { reply } })
}
