import request from '@/utils/request'

export const publishJob = (data) => {
  return request.post('/job/publish', data)
}

export const updateJob = (data) => {
  return request.put('/job/update', data)
}

export const deleteJob = (id) => {
  return request.delete(`/job/${id}`)
}

export const getJobById = (id) => {
  return request.get(`/job/${id}`)
}

export const getJobList = (params) => {
  return request.get('/job/list', { params })
}

export const getRecommendJobs = () => {
  return request.get('/job/recommend')
}

export const getJobsByCompanyId = (companyId, params) => {
  return request.get(`/job/company/${companyId}`, { params })
}

