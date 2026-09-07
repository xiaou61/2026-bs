import request from '@/utils/request'

export const registerCompany = (data) => {
  return request.post('/company/register', data)
}

export const getCompanyById = (id) => {
  return request.get(`/company/${id}`)
}

export const updateCompany = (data) => {
  return request.put('/company/update', data)
}

export const getCompanyList = (params) => {
  return request.get('/company/list', { params })
}

