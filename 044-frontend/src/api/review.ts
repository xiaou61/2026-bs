import request from '@/utils/request'

export function addReview(data: {
  bookingId: number
  rating: number
  content?: string
  images?: string
}) {
  return request.post('/review/add', null, { params: data })
}

export function getHomestayReviews(homestayId: number, params: { current?: number; size?: number }) {
  return request.get(`/review/homestay/${homestayId}`, { params })
}

export function getMyReviews(params: { current?: number; size?: number }) {
  return request.get('/review/my', { params })
}
