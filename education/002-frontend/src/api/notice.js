import request from '@/utils/request'

export const getNoticeList = (params) => {
  return request({
    url: '/notices',
    method: 'get',
    params
  })
}

export const getNoticeById = (id) => {
  return request({
    url: `/notices/${id}`,
    method: 'get'
  })
}

export const createNotice = (data) => {
  return request({
    url: '/notices',
    method: 'post',
    data
  })
}

export const updateNotice = (id, data) => {
  return request({
    url: `/notices/${id}`,
    method: 'put',
    data
  })
}

export const deleteNotice = (id) => {
  return request({
    url: `/notices/${id}`,
    method: 'delete'
  })
}

