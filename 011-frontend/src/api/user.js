import request from '@/utils/request'

export const getUserInfo = (id) => {
  return request({
    url: `/user/${id}`,
    method: 'get'
  })
}

export const updateProfile = (data) => {
  return request({
    url: '/user/profile',
    method: 'put',
    data
  })
}

export const uploadAvatar = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/user/avatar',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const getUserVideos = (id, params) => {
  return request({
    url: `/user/${id}/videos`,
    method: 'get',
    params
  })
}

export const followUser = (id) => {
  return request({
    url: `/user/follow/${id}`,
    method: 'post'
  })
}

export const unfollowUser = (id) => {
  return request({
    url: `/user/follow/${id}`,
    method: 'delete'
  })
}

export const getFollowers = (id) => {
  return request({
    url: `/user/${id}/followers`,
    method: 'get'
  })
}

export const getFollowing = (id) => {
  return request({
    url: `/user/${id}/following`,
    method: 'get'
  })
}

export const getPointsLog = () => {
  return request({
    url: '/user/points/log',
    method: 'get'
  })
}

