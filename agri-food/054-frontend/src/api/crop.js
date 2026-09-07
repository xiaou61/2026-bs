import request from '@/utils/request'

export function getCropPage(params) {
  return request.get('/crop/page', { params })
}

export function getCropById(id) {
  return request.get(`/crop/${id}`)
}

export function addCrop(data) {
  return request.post('/crop', data)
}

export function updateCrop(data) {
  return request.put('/crop', data)
}

export function deleteCrop(id) {
  return request.delete(`/crop/${id}`)
}

export function getCategoryList() {
  return request.get('/crop/category/list')
}

export function getCategoryTree() {
  return request.get('/crop/category/tree')
}

export function addCategory(data) {
  return request.post('/crop/category', data)
}

export function updateCategory(data) {
  return request.put('/crop/category', data)
}

export function deleteCategory(id) {
  return request.delete(`/crop/category/${id}`)
}
