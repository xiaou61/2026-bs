import request from '@/utils/request'

export function getAppointmentPage(params) {
  return request.get('/appointment/page', { params })
}

export function getMyAppointments(params) {
  return request.get('/appointment/my', { params })
}

export function addAppointment(data) {
  return request.post('/appointment', data)
}

export function confirmAppointment(id) {
  return request.put(`/appointment/${id}/confirm`)
}

export function completeAppointment(id) {
  return request.put(`/appointment/${id}/complete`)
}

export function cancelAppointment(id) {
  return request.put(`/appointment/${id}/cancel`)
}

export function rateAppointment(id, data) {
  return request.post(`/appointment/${id}/rate`, data)
}
