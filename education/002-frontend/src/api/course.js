import request from '@/utils/request'

export const getCourseList = (params) => {
  return request({
    url: '/courses',
    method: 'get',
    params
  })
}

export const getCourseById = (id) => {
  return request({
    url: `/courses/${id}`,
    method: 'get'
  })
}

export const getMyCourses = () => {
  return request({
    url: '/courses/my',
    method: 'get'
  })
}

export const createCourse = (data) => {
  return request({
    url: '/courses',
    method: 'post',
    data
  })
}

export const updateCourse = (id, data) => {
  return request({
    url: `/courses/${id}`,
    method: 'put',
    data
  })
}

export const deleteCourse = (id) => {
  return request({
    url: `/courses/${id}`,
    method: 'delete'
  })
}

