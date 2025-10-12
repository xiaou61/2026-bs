import request from '@/utils/request'

export const getConfigList = () => {
  return request({
    url: '/config/list',
    method: 'get'
  })
}

export const updateConfig = (data) => {
  return request({
    url: '/config',
    method: 'put',
    data
  })
}

