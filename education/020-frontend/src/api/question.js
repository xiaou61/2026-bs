import request from '@/utils/request'

export const addQuestion = (data) => {
  return request({
    url: '/question/add',
    method: 'post',
    data
  })
}

export const getQuestionList = (params) => {
  return request({
    url: '/question/list',
    method: 'get',
    params
  })
}

export const getQuestionDetail = (id) => {
  return request({
    url: `/question/${id}`,
    method: 'get'
  })
}

export const getRandomQuestions = (data) => {
  return request({
    url: '/question/random',
    method: 'post',
    data
  })
}

export const generatePaper = (data) => {
  return request({
    url: '/question/generate-paper',
    method: 'post',
    data
  })
}

export const addWrongQuestion = (data) => {
  return request({
    url: '/question/wrong/add',
    method: 'post',
    data
  })
}

export const getWrongQuestionList = (params) => {
  return request({
    url: '/question/wrong/list',
    method: 'get',
    params
  })
}

export const removeWrongQuestion = (id) => {
  return request({
    url: `/question/wrong/${id}`,
    method: 'delete'
  })
}

