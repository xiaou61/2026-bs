import request from '../utils/request'

export const addHealthData = (data) => {
  return request.post('/health-data', data)
}

export const getHealthDataList = () => {
  return request.get('/health-data/list')
}

export const getHealthDataByType = (dataType) => {
  return request.get(`/health-data/list/${dataType}`)
}

export const deleteHealthData = (id) => {
  return request.delete(`/health-data/${id}`)
}

export const getPatientInfo = () => {
  return request.get('/patients/info')
}

export const updatePatientInfo = (data) => {
  return request.post('/patients/info', data)
}
