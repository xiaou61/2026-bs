import request from '@/utils/request'

export function getProfile() {
  return request({
    url: '/user/profile',
    method: 'get'
  })
}

export function updateProfile(data) {
  return request({
    url: '/user/profile',
    method: 'put',
    data
  })
}

export function submitAuth(data) {
  return request({
    url: '/user/auth/submit',
    method: 'post',
    data
  })
}

export function getAuthStatus() {
  return request({
    url: '/user/auth/status',
    method: 'get'
  })
}

export function getCredit() {
  return request({
    url: '/user/credit',
    method: 'get'
  })
}

export function getCreditLog() {
  return request({
    url: '/user/credit/log',
    method: 'get'
  })
}

