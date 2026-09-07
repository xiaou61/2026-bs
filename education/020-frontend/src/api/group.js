import request from '@/utils/request'

export const createGroup = (data) => {
  return request({
    url: '/group/create',
    method: 'post',
    data
  })
}

export const getGroupList = (params) => {
  return request({
    url: '/group/list',
    method: 'get',
    params
  })
}

export const getGroupDetail = (id) => {
  return request({
    url: `/group/${id}`,
    method: 'get'
  })
}

export const joinGroup = (data) => {
  return request({
    url: '/group/join',
    method: 'post',
    data
  })
}

export const leaveGroup = (data) => {
  return request({
    url: '/group/leave',
    method: 'post',
    data
  })
}

export const getGroupMembers = (groupId) => {
  return request({
    url: `/group/members/${groupId}`,
    method: 'get'
  })
}

export const getMyGroups = (params) => {
  return request({
    url: '/group/my',
    method: 'get',
    params
  })
}

