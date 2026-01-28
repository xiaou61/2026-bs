import request from '@/utils/request'

export function getMaterialPage(params) {
  return request.get('/material/page', { params })
}

export function addMaterial(data) {
  return request.post('/material', data)
}

export function updateMaterial(data) {
  return request.put('/material', data)
}

export function deleteMaterial(id) {
  return request.delete(`/material/${id}`)
}

export function getRecordPage(params) {
  return request.get('/material/record/page', { params })
}

export function stockIn(data) {
  return request.post('/material/in', data)
}

export function stockOut(data) {
  return request.post('/material/out', data)
}

export function getWarningList() {
  return request.get('/material/warning')
}
