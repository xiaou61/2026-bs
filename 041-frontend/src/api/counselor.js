import request from './request'

export function getCounselors() {
  return request({
    url: '/counselors',
    method: 'get'
  })
}

export function getCounselorById(id) {
  return request({
    url: `/counselors/${id}`,
    method: 'get'
  })
}

export function getTimeSlots(counselorId) {
  return request({
    url: `/counselors/${counselorId}/timeslots`,
    method: 'get'
  })
}
