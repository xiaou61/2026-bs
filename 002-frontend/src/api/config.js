import request from '@/utils/request'

export const getConfigList = () => {
  return request({
    url: '/system-config',
    method: 'get'
  })
}

export const getConfigById = (id) => {
  return request({
    url: `/system-config/${id}`,
    method: 'get'
  })
}

export const getConfigByKey = (key) => {
  return request({
    url: `/system-config/key/${key}`,
    method: 'get'
  })
}

export const updateConfig = (id, data) => {
  return request({
    url: `/system-config/${id}`,
    method: 'put',
    data
  })
}

