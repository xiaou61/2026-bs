import request from './request'

export const createDonation = (data) => {
  return request.post('/donations', data)
}

export const getMyDonations = (params) => {
  return request.get('/donations/my', { params })
}

export const getProjectDonations = (projectId, params) => {
  return request.get(`/donations/project/${projectId}`, { params })
}
