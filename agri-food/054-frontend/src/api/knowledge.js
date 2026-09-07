import request from '@/utils/request'

export function getKnowledgePage(params) {
  return request.get('/knowledge/page', { params })
}

export function getKnowledgeById(id) {
  return request.get(`/knowledge/${id}`)
}

export function addKnowledge(data) {
  return request.post('/knowledge', data)
}

export function updateKnowledge(data) {
  return request.put('/knowledge', data)
}

export function deleteKnowledge(id) {
  return request.delete(`/knowledge/${id}`)
}

export function likeKnowledge(id) {
  return request.post(`/knowledge/${id}/like`)
}

export function collectKnowledge(id) {
  return request.post(`/knowledge/${id}/collect`)
}
