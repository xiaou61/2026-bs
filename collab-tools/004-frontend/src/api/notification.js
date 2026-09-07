import request from '@/utils/request'

export const getNotifications = (page = 1, size = 20) => {
  return request.get('/notify/list', { params: { page, size } })
}

export const getUnreadNotifications = () => {
  return request.get('/notify/unread')
}

export const getUnreadCount = () => {
  return request.get('/notify/unread/count')
}

export const markAsRead = (id) => {
  return request.put(`/notify/read/${id}`)
}

export const markAllAsRead = () => {
  return request.put('/notify/read/all')
}

