import request from '@/utils/request'

export const publishExperience = (data) => {
  return request.post('/experience/publish', data)
}

export const getExperienceList = (params) => {
  return request.get('/experience/list', { params })
}

export const getExperienceById = (id) => {
  return request.get(`/experience/${id}`)
}

export const likeExperience = (id) => {
  return request.post(`/experience/like/${id}`)
}

