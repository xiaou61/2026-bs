import request from '@/utils/request'

export const enrollActivity = (activityId) => {
  return request.post('/enrollments', { activityId })
}

export const cancelEnrollment = (id) => {
  return request.delete(`/enrollments/${id}`)
}

export const getEnrollmentList = (params) => {
  return request.get('/enrollments', { params })
}

export const getMyEnrollments = () => {
  return request.get('/enrollments/my')
}

export const updateEnrollmentStatus = (id, status) => {
  return request.put(`/enrollments/${id}/status`, { status })
}

