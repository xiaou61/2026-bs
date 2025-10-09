import request from '@/utils/request'

export const getMyGrades = () => {
  return request({
    url: '/grades/my',
    method: 'get'
  })
}

export const getGradesByStudent = (studentId) => {
  return request({
    url: `/grades/student/${studentId}`,
    method: 'get'
  })
}

export const getGradesByCourse = (courseId) => {
  return request({
    url: `/grades/course/${courseId}`,
    method: 'get'
  })
}

export const submitGrade = (data) => {
  return request({
    url: '/grades/submit',
    method: 'post',
    data
  })
}

