import request from '@/utils/request'

export const getPointsRecords = (params) => {
  return request.get('/points/records', { params })
}

export const getMyPointsRecords = () => {
  return request.get('/points/my')
}

export const getPointsRanking = () => {
  return request.get('/points/ranking')
}

export const adjustPoints = (data) => {
  return request.post('/points/adjust', data)
}

