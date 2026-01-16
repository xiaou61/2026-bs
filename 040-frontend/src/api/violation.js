import request from './request'

export function getViolationTypes() {
  return request({
    url: '/violation-types',
    method: 'get'
  })
}
