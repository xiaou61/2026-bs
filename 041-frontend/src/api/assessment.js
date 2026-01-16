import request from './request'

export function getScales() {
  return request({
    url: '/assessments/scales',
    method: 'get'
  })
}

export function getScaleById(id) {
  return request({
    url: `/assessments/scales/${id}`,
    method: 'get'
  })
}

export function getQuestions(scaleId) {
  return request({
    url: `/assessments/scales/${scaleId}/questions`,
    method: 'get'
  })
}

export function submitAssessment(data) {
  return request({
    url: '/assessments',
    method: 'post',
    params: data
  })
}

export function getMyAssessments() {
  return request({
    url: '/assessments/my',
    method: 'get'
  })
}
