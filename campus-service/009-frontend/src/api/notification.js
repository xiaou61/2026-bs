import request from '@/utils/request'

export const getNotificationList = (params) => {
  return request({
    url: '/notification/list',
    method: 'get',
    params
  })
}

export const getUnreadCount = () => {
  return request({
    url: '/notification/unread-count',
    method: 'get'
  })
}

export const markRead = (id) => {
  return request({
    url: `/notification/${id}/read`,
    method: 'put'
  })
}

export const markAllRead = () => {
  return request({
    url: '/notification/read-all',
    method: 'put'
  })
}

