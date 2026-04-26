import request from '../utils/request'

export const getKnowledgeList = (params) => {
  return request.get('/health-knowledge/list', { params })
}

export const getKnowledgeDetail = (id) => {
  return request.get(`/health-knowledge/detail/${id}`)
}

export const getAdminKnowledgeList = () => {
  return request.get('/health-knowledge/admin/list')
}

export const createKnowledge = (data) => {
  return request.post('/health-knowledge', data)
}

export const updateKnowledge = (id, data) => {
  return request.put(`/health-knowledge/${id}`, data)
}

export const updateKnowledgeStatus = (id, status) => {
  return request.put(`/health-knowledge/${id}/status`, null, { params: { status } })
}

export const deleteKnowledge = (id) => {
  return request.delete(`/health-knowledge/${id}`)
}
