import request from '@/utils/request'

export const getChatHistory = (friendId, page = 1, size = 20) => {
  return request.get('/chat/history', { params: { friendId, page, size } })
}

export const getUnreadMessages = () => {
  return request.get('/chat/unread')
}

export const markAsRead = (messageId) => {
  return request.put(`/chat/read/${messageId}`)
}

export const markAllAsRead = (fromUserId) => {
  return request.put(`/chat/read/all/${fromUserId}`)
}

export const searchMessages = (keyword, page = 1, size = 20) => {
  return request.get('/chat/search', { params: { keyword, page, size } })
}

