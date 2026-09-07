import request from '../utils/request'

export const createConsultation = (data) => {
  return request.post('/consultations', data)
}

export const getPatientConsultations = () => {
  return request.get('/consultations/patient')
}

export const getDoctorConsultations = () => {
  return request.get('/consultations/doctor')
}

export const getDoctorPendingConsultations = () => {
  return request.get('/consultations/doctor/pending')
}

export const getConsultationDetail = (id) => {
  return request.get(`/consultations/${id}`)
}

export const answerConsultation = (id, answer) => {
  return request.post(`/consultations/${id}/answer`, { answer })
}

export const rateConsultation = (id, rating, feedback) => {
  return request.post(`/consultations/${id}/rate`, { rating, feedback })
}
