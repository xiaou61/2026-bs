import request from '@/utils/request'

export const createResume = (data) => {
  return request.post('/resume/create', data)
}

export const updateResume = (data) => {
  return request.put('/resume/update', data)
}

export const getMyResume = () => {
  return request.get('/resume/my')
}

export const getResumeById = (id) => {
  return request.get(`/resume/${id}`)
}

