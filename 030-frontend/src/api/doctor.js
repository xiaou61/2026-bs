import request from '../utils/request'

export const getDoctorInfo = () => {
  return request.get('/doctors/info')
}

export const updateDoctorInfo = (data) => {
  return request.post('/doctors/info', data)
}

export const getVerifiedDoctors = () => {
  return request.get('/doctors/verified')
}

export const getPendingDoctors = () => {
  return request.get('/doctors/pending')
}

export const verifyDoctor = (id, status) => {
  return request.post(`/doctors/${id}/verify`, null, { params: { status } })
}
