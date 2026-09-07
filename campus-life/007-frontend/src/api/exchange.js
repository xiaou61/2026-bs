import request from '@/utils/request'

export const exchangeReward = (data) => {
  return request.post('/exchanges', data)
}

export const getExchangeList = (params) => {
  return request.get('/exchanges', { params })
}

export const getMyExchanges = () => {
  return request.get('/exchanges/my')
}

export const updateExchangeStatus = (id, status) => {
  return request.put(`/exchanges/${id}/status`, { status })
}

