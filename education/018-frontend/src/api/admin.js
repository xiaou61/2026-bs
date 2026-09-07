import request from '@/utils/request'

export const getAdminOverview = () => {
  return request.get('/admin/overview')
}

export const getAdminCompanyList = (params) => {
  return request.get('/admin/company/list', { params })
}

export const reviewCompany = (data) => {
  return request.put('/admin/company/review', data)
}
