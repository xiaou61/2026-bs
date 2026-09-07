import request from '@/utils/request'

export const checkIn = (activityId) => {
  return request.post('/attendances/check-in', { activityId })
}

export const checkOut = (id) => {
  return request.put(`/attendances/${id}/check-out`)
}

export const getAttendanceList = (params) => {
  return request.get('/attendances', { params })
}

export const getMyAttendances = () => {
  return request.get('/attendances/my')
}

export const manualCheckIn = (data) => {
  return request.post('/attendances/manual', data)
}

