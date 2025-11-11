import request from '@/utils/request'

export const createNote = (data) => {
  return request({
    url: '/note/create',
    method: 'post',
    data
  })
}

export const updateNote = (data) => {
  return request({
    url: '/note/update',
    method: 'put',
    data
  })
}

export const getNoteList = (params) => {
  return request({
    url: '/note/list',
    method: 'get',
    params
  })
}

export const getNoteDetail = (id) => {
  return request({
    url: `/note/${id}`,
    method: 'get'
  })
}

export const getMyNotes = (params) => {
  return request({
    url: '/note/my',
    method: 'get',
    params
  })
}

export const deleteNote = (id) => {
  return request({
    url: `/note/${id}`,
    method: 'delete'
  })
}

export const likeNote = (id) => {
  return request({
    url: `/note/like/${id}`,
    method: 'post'
  })
}

