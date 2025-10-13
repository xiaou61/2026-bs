import request from '@/utils/request'

export const saveDraft = (data) => {
  return request({
    url: '/draft/save',
    method: 'post',
    data
  })
}

export const getDraftList = () => {
  return request({
    url: '/draft/list',
    method: 'get'
  })
}

export const getDraftDetail = (id) => {
  return request({
    url: `/draft/${id}`,
    method: 'get'
  })
}

export const deleteDraft = (id) => {
  return request({
    url: `/draft/${id}`,
    method: 'delete'
  })
}

