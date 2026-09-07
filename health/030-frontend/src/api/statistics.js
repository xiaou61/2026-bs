import request from '../utils/request'

export const getAdminStatistics = () => {
  return request.get('/statistics/admin')
}

export const getDoctorStatistics = () => {
  return request.get('/statistics/doctor')
}
