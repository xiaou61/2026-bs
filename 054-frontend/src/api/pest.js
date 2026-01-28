import request from '@/utils/request'

export function getPestPage(params) {
  return request.get('/pest/page', { params })
}

export function getPestById(id) {
  return request.get(`/pest/${id}`)
}

export function addPest(data) {
  return request.post('/pest', data)
}

export function updatePest(data) {
  return request.put('/pest', data)
}

export function deletePest(id) {
  return request.delete(`/pest/${id}`)
}

export function getWarningPage(params) {
  return request.get('/pest/warning/page', { params })
}

export function addWarning(data) {
  return request.post('/pest/warning', data)
}

export function updateWarning(data) {
  return request.put('/pest/warning', data)
}

export function deleteWarning(id) {
  return request.delete(`/pest/warning/${id}`)
}

export function getTreatmentPage(params) {
  return request.get('/pest/treatment/page', { params })
}

export function addTreatment(data) {
  return request.post('/pest/treatment', data)
}

export function updateTreatment(data) {
  return request.put('/pest/treatment', data)
}
