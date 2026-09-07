import request from '@/utils/request'

export function getNotificationList() {
  return request({
    url: '/notification/list',
    method: 'get'
  })
}

export function getUnreadCount() {
  return request({
    url: '/notification/unread-count',
    method: 'get'
  })
}

export function markRead(id) {
  return request({
    url: `/notification/${id}/read`,
    method: 'put'
  })
}

export function markAllRead() {
  return request({
    url: '/notification/read-all',
    method: 'put'
  })
}

