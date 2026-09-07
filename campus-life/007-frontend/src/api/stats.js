import request from '@/utils/request'

export const getOverview = () => {
  return request.get('/stats/overview')
}

export const getMyStats = () => {
  return request.get('/stats/my')
}

export const getActivityRanking = () => {
  return request.get('/stats/activity-ranking')
}

