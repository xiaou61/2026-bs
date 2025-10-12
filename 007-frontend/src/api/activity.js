import request from '@/utils/request'

export const getActivityList = (params) => {
  return request.get('/activities', { params })
}

export const getActivityById = (id) => {
  return request.get(`/activities/${id}`)
}

export const createActivity = (data) => {
  return request.post('/activities', data)
}

export const updateActivity = (id, data) => {
  return request.put(`/activities/${id}`, data)
}

export const deleteActivity = (id) => {
  return request.delete(`/activities/${id}`)
}

export const updateActivityStatus = (id, status) => {
  return request.put(`/activities/${id}/status`, { status })
}

export const getMyPublishedActivities = () => {
  return request.get('/activities/my/published')
}

export const getMyEnrolledActivities = () => {
  return request.get('/activities/my/enrolled')
}

