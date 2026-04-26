import request from '../utils/request'

export const generateAssessment = () => {
  return request.post('/health-assessment/generate')
}

export const getLatestAssessment = () => {
  return request.get('/health-assessment/latest')
}

export const getAssessmentHistory = () => {
  return request.get('/health-assessment/history')
}

export const getAlerts = (params) => {
  return request.get('/health-assessment/alerts', { params })
}

export const getUnreadAlertCount = () => {
  return request.get('/health-assessment/alerts/unread-count')
}

export const markAlertAsRead = (id) => {
  return request.put(`/health-assessment/alerts/${id}/read`)
}
