import request from './request'

export const getProjects = (params) => {
  return request.get('/projects', { params })
}

export const getProjectDetail = (id) => {
  return request.get(`/projects/${id}`)
}

export const createProject = (data) => {
  return request.post('/projects', data)
}

export const getCategories = () => {
  return request.get('/projects/categories')
}

export const getLatestProjects = (limit) => {
  return request.get('/projects/latest', { params: { limit } })
}

export const getMyProjects = (params) => {
  return request.get('/projects/my', { params })
}
