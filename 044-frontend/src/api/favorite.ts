import request from '@/utils/request'

export function addFavorite(homestayId: number) {
  return request.post(`/favorite/add/${homestayId}`)
}

export function removeFavorite(homestayId: number) {
  return request.delete(`/favorite/remove/${homestayId}`)
}

export function checkFavorite(homestayId: number) {
  return request.get(`/favorite/check/${homestayId}`)
}

export function getMyFavorites(params: { current?: number; size?: number }) {
  return request.get('/favorite/my', { params })
}
