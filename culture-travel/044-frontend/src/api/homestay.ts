import request from '@/utils/request'

export function getHomestayList(params: {
  current?: number
  size?: number
  city?: string
  minPrice?: number
  maxPrice?: number
  keyword?: string
}) {
  return request.get('/homestay/list', { params })
}

export function getHomestayDetail(id: number) {
  return request.get(`/homestay/detail/${id}`)
}

export function searchHomestay(params: { keyword: string; current?: number; size?: number }) {
  return request.get('/homestay/search', { params })
}
