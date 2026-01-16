import request from './request'

export function createAppointment(data) {
  return request({
    url: '/appointments',
    method: 'post',
    data
  })
}

export function getMyAppointments() {
  return request({
    url: '/appointments/my',
    method: 'get'
  })
}

export function getAppointmentById(id) {
  return request({
    url: `/appointments/${id}`,
    method: 'get'
  })
}
