import request from '@/utils/request'

export const publishReferral = (data) => {
  return request.post('/referral/publish', data)
}

export const getReferralList = (params) => {
  return request.get('/referral/list', { params })
}

export const getReferralById = (id) => {
  return request.get(`/referral/${id}`)
}

export const closeReferral = (id) => {
  return request.put(`/referral/close/${id}`)
}

