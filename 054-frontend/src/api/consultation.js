import request from '@/utils/request'

export function getConsultationPage(params) {
  return request.get('/consultation/page', { params })
}

export function getConsultationById(id) {
  return request.get(`/consultation/${id}`)
}

export function addConsultation(data) {
  return request.post('/consultation', data)
}

export function updateConsultation(data) {
  return request.put('/consultation', data)
}

export function deleteConsultation(id) {
  return request.delete(`/consultation/${id}`)
}

export function getAnswers(id) {
  return request.get(`/consultation/${id}/answers`)
}

export function addAnswer(id, data) {
  return request.post(`/consultation/${id}/answer`, data)
}

export function adoptAnswer(id) {
  return request.put(`/consultation/answer/${id}/adopt`)
}
