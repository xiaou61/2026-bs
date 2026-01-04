import request from './request'

export const createDonation = (data) =&gt; {
  return request.post('/donations', data)
}

export const getMyDonations = (params) =&gt; {
  return request.get('/donations/my', { params })
}

export const getProjectDonations = (projectId, params) =&gt; {
  return request.get(`/donations/project/${projectId}`, { params })
}
