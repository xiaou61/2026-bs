import request from '@/utils/request'

export const getRewardList = (params) => {
  return request.get('/rewards', { params })
}

export const getAvailableRewards = () => {
  return request.get('/rewards/available')
}

export const getRewardById = (id) => {
  return request.get(`/rewards/${id}`)
}

export const createReward = (data) => {
  return request.post('/rewards', data)
}

export const updateReward = (id, data) => {
  return request.put(`/rewards/${id}`, data)
}

export const deleteReward = (id) => {
  return request.delete(`/rewards/${id}`)
}

export const updateRewardStatus = (id, status) => {
  return request.put(`/rewards/${id}/status`, { status })
}

