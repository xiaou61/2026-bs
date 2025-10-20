import request from '../utils/request'

export const createComplaint = (data) => {
  return request({
    url: '/complaints',
    method: 'post',
    data
  })
}

export const getMyComplaints = () => {
  return request({
    url: '/complaints/my',
    method: 'get'
  })
}

