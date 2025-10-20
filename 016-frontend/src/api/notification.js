import request from '../utils/request'

export const getNotifications = (params) => {
  return request({
    url: '/notifications',
    method: 'get',
    params
  })
}

export const markAsRead = (id) => {
  return request({
    url: `/notifications/${id}/read`,
    method: 'put'
  })
}

export const getUnreadCount = () => {
  return request({
    url: '/notifications/unread-count',
    method: 'get'
  })
}

