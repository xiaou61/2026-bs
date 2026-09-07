import request from '@/utils/request'

export function getPlanPage(params) {
  return request.get('/plan/page', { params })
}

export function getPlanById(id) {
  return request.get(`/plan/${id}`)
}

export function addPlan(data) {
  return request.post('/plan', data)
}

export function updatePlan(data) {
  return request.put('/plan', data)
}

export function deletePlan(id) {
  return request.delete(`/plan/${id}`)
}

export function updatePlanStatus(id, status) {
  return request.put(`/plan/${id}/status`, { status })
}

export function getTaskPage(params) {
  return request.get('/task/page', { params })
}

export function addTask(data) {
  return request.post('/task', data)
}

export function updateTask(data) {
  return request.put('/task', data)
}

export function deleteTask(id) {
  return request.delete(`/task/${id}`)
}

export function updateTaskStatus(id, status) {
  return request.put(`/task/${id}/status`, { status })
}
