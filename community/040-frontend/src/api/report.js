import request from './request'

export function submitReport(data) {
  return request({
    url: '/reports',
    method: 'post',
    data
  })
}

export function getMyReports() {
  return request({
    url: '/reports/my',
    method: 'get'
  })
}

export function getPendingReports() {
  return request({
    url: '/reports/pending',
    method: 'get'
  })
}

export function getReportById(id) {
  return request({
    url: `/reports/${id}`,
    method: 'get'
  })
}

export const getReportDetail = getReportById

export function auditReport(id, data) {
  return request({
    url: `/reports/${id}/audit`,
    method: 'post',
    params: data
  })
}
