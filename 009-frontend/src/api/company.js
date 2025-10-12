import request from '@/utils/request'

export const getCompanyList = (params) => {
  return request({
    url: '/company/list',
    method: 'get',
    params
  })
}

export const getAllCompanies = () => {
  return request({
    url: '/company/all',
    method: 'get'
  })
}

export const getCompanyDetail = (id) => {
  return request({
    url: `/company/${id}`,
    method: 'get'
  })
}

export const addCompany = (data) => {
  return request({
    url: '/company',
    method: 'post',
    data
  })
}

export const updateCompany = (id, data) => {
  return request({
    url: `/company/${id}`,
    method: 'put',
    data
  })
}

export const deleteCompany = (id) => {
  return request({
    url: `/company/${id}`,
    method: 'delete'
  })
}

