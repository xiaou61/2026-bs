import request from '@/utils/request'

export function getNoticePage(params) {
  return request.get('/notice/page', { params })
}

export function getPublishedNotices() {
  return request.get('/notice/published')
}

export function addNotice(data) {
  return request.post('/notice', data)
}

export function updateNotice(data) {
  return request.put('/notice', data)
}

export function deleteNotice(id) {
  return request.delete(`/notice/${id}`)
}

export function getStatsOverview() {
  return request.get('/stats/overview')
}
