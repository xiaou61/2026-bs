import request from '@/utils/request'

export const getNotificationList = () => {
  return request({
    url: '/notification/list',
    method: 'get'
  })
}

export const getUnreadCount = () => {
  return request({
    url: '/notification/unread-count',
    method: 'get'
  })
}

export const markAsRead = (id) => {
  return request({
    url: `/notification/${id}/read`,
    method: 'put'
  })
}

export const markAllAsRead = () => {
  return request({
    url: '/notification/read-all',
    method: 'put'
  })
}

