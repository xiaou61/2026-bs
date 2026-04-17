import request from '@/utils/request'

export const chatApi = {
  sendMessage: (data) => {
    return request({
      url: '/chat/send',
      method: 'post',
      data
    })
  },

  getChatList: () => {
    return request({
      url: '/chat/list',
      method: 'get'
    })
  },

  getMessages: (params) => {
    return request({
      url: '/chat/messages',
      method: 'get',
      params
    })
  },

  markRead: (data) => {
    return request({
      url: '/chat/read',
      method: 'put',
      data
    })
  },

  bargain: (data) => {
    return request({
      url: '/chat/bargain',
      method: 'post',
      data
    })
  },

  acceptBargain: (id) => {
    return request({
      url: `/chat/bargain/${id}/accept`,
      method: 'put'
    })
  },

  rejectBargain: (id) => {
    return request({
      url: `/chat/bargain/${id}/reject`,
      method: 'put'
    })
  }
}
