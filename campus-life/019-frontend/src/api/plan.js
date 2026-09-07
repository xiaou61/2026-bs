import request from '@/utils/request'

export const createPlan = (data) => {
  return request({
    url: '/plan/create',
    method: 'post',
    data
  })
}

export const getPlanList = (params) => {
  return request({
    url: '/plan/list',
    method: 'get',
    params
  })
}

export const getPlanDetail = (id) => {
  return request({
    url: `/plan/${id}`,
    method: 'get'
  })
}

export const updateProgress = (id, data) => {
  return request({
    url: `/plan/${id}/progress`,
    method: 'put',
    data
  })
}

export const deletePlan = (id) => {
  return request({
    url: `/plan/${id}`,
    method: 'delete'
  })
}

