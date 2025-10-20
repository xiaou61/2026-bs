import request from '../utils/request'

export const createReview = (data) => {
  return request({
    url: '/reviews',
    method: 'post',
    data
  })
}

export const getUserReviews = (userId) => {
  return request({
    url: `/reviews/user/${userId}`,
    method: 'get'
  })
}

