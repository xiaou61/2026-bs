import request from './request'

export const getProjects = (params) =&gt; {
  return request.get('/projects', { params })
}

export const getProjectDetail = (id) =&gt; {
  return request.get(`/projects/${id}`)
}

export const createProject = (data) =&gt; {
  return request.post('/projects', data)
}

export const getCategories = () =&gt; {
  return request.get('/projects/categories')
}

export const getLatestProjects = (limit) =&gt; {
  return request.get('/projects/latest', { params: { limit } })
}

export const getMyProjects = (params) =&gt; {
  return request.get('/projects/my', { params })
}
