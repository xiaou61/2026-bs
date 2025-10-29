import request from '@/utils/request'

export const createInterview = (data) => {
  return request.post('/interview/create', data)
}

export const updateInterview = (data) => {
  return request.put('/interview/update', data)
}

export const getMyInterviews = (params) => {
  return request.get('/interview/my', { params })
}

export const cancelInterview = (id) => {
  return request.put(`/interview/cancel/${id}`)
}

