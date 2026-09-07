import request from '@/utils/request'

export const submitApplication = (data) => {
  return request.post('/application/submit', data)
}

export const getMyApplications = (params) => {
  return request.get('/application/my', { params })
}

export const getReceivedApplications = (params) => {
  return request.get('/application/received', { params })
}

export const updateApplicationStatus = (data) => {
  return request.put('/application/updateStatus', data)
}

