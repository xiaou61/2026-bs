import request from '@/utils/request'

export const getFriendList = () => {
  return request.get('/friend/list')
}

export const addFriend = (data) => {
  return request.post('/friend/add', data)
}

export const deleteFriend = (friendId) => {
  return request.delete(`/friend/delete/${friendId}`)
}

export const getGroupList = () => {
  return request.get('/friend/group/list')
}

export const addGroup = (data) => {
  return request.post('/friend/group/add', data)
}

