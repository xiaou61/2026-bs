import request from '@/utils/request'

export const getMySelections = () => {
  return request({
    url: '/selections/my',
    method: 'get'
  })
}

export const getSelectionsByCourse = (courseId) => {
  return request({
    url: `/selections/course/${courseId}`,
    method: 'get'
  })
}

export const selectCourse = (courseId) => {
  return request({
    url: `/selections/select/${courseId}`,
    method: 'post'
  })
}

export const dropCourse = (courseId) => {
  return request({
    url: `/selections/drop/${courseId}`,
    method: 'delete'
  })
}

