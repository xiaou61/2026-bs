import request from '@/utils/request'

export function getSystemConfigs() {
  return request({
    url: '/system-config/list',
    method: 'get'
  })
}

export function updateSystemConfig(data) {
  return request({
    url: '/system-config',
    method: 'put',
    data
  })
}
