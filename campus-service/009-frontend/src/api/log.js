import request from '@/utils/request'

export const getLogList = (params) => {
  return request({
    url: '/log/list',
    method: 'get',
    params
  })
}

