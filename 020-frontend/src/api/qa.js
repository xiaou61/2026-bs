import request from '@/utils/request'

export const askQuestion = (data) => {
  return request({
    url: '/qa/ask',
    method: 'post',
    data
  })
}

export const getQuestionList = (params) => {
  return request({
    url: '/qa/list',
    method: 'get',
    params
  })
}

export const getQuestionDetail = (id) => {
  return request({
    url: `/qa/${id}`,
    method: 'get'
  })
}

export const getMyQuestions = (params) => {
  return request({
    url: '/qa/my',
    method: 'get',
    params
  })
}

export const addAnswer = (data) => {
  return request({
    url: '/qa/answer',
    method: 'post',
    data
  })
}

export const getAnswers = (questionId) => {
  return request({
    url: `/qa/answers/${questionId}`,
    method: 'get'
  })
}

export const acceptAnswer = (data) => {
  return request({
    url: '/qa/accept',
    method: 'post',
    data
  })
}

export const likeAnswer = (answerId) => {
  return request({
    url: `/qa/like/${answerId}`,
    method: 'post'
  })
}

