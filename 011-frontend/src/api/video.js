import request from '@/utils/request'

export const uploadVideo = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/video/upload',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const uploadCover = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/video/uploadCover',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const publishVideo = (data) => {
  return request({
    url: '/video/publish',
    method: 'post',
    data
  })
}

export const getRecommendVideos = (params) => {
  return request({
    url: '/video/recommend',
    method: 'get',
    params
  })
}

export const getFollowingVideos = (params) => {
  return request({
    url: '/video/following',
    method: 'get',
    params
  })
}

export const getVideoDetail = (id) => {
  return request({
    url: `/video/${id}`,
    method: 'get'
  })
}

export const deleteVideo = (id) => {
  return request({
    url: `/video/${id}`,
    method: 'delete'
  })
}

export const likeVideo = (id) => {
  return request({
    url: `/video/${id}/like`,
    method: 'post'
  })
}

export const unlikeVideo = (id) => {
  return request({
    url: `/video/${id}/like`,
    method: 'delete'
  })
}

export const collectVideo = (id) => {
  return request({
    url: `/video/${id}/collect`,
    method: 'post'
  })
}

export const uncollectVideo = (id) => {
  return request({
    url: `/video/${id}/collect`,
    method: 'delete'
  })
}

export const shareVideo = (id, data) => {
  return request({
    url: `/video/${id}/share`,
    method: 'post',
    data
  })
}

export const recordPlay = (id) => {
  return request({
    url: `/video/${id}/play`,
    method: 'post'
  })
}

