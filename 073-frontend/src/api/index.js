import request from './request'

export const userApi = {
  login: data => request.post('/user/login', data),
  logout: () => request.post('/user/logout'),
  getInfo: () => request.get('/user/info'),
  getList: params => request.get('/user/list', { params }),
  add: data => request.post('/user', data),
  update: data => request.put('/user', data),
  delete: id => request.delete(`/user/${id}`),
  resetPassword: data => request.post('/user/resetPassword', data),
  updatePassword: data => request.post('/user/updatePassword', data)
}

export const departmentApi = {
  getById: id => request.get(`/department/${id}`),
  getList: params => request.get('/department/list', { params }),
  getAll: () => request.get('/department/all'),
  getTree: () => request.get('/department/tree'),
  add: data => request.post('/department', data),
  update: data => request.put('/department', data),
  delete: id => request.delete(`/department/${id}`)
}

export const positionApi = {
  getById: id => request.get(`/position/${id}`),
  getList: params => request.get('/position/list', { params }),
  getAll: () => request.get('/position/all'),
  add: data => request.post('/position', data),
  update: data => request.put('/position', data),
  delete: id => request.delete(`/position/${id}`)
}

export const employeeApi = {
  getById: id => request.get(`/employee/${id}`),
  getList: params => request.get('/employee/list', { params }),
  getAll: () => request.get('/employee/all'),
  getStatistics: () => request.get('/employee/statistics'),
  add: data => request.post('/employee', data),
  update: data => request.put('/employee', data),
  delete: id => request.delete(`/employee/${id}`)
}

export const attendanceApi = {
  getById: id => request.get(`/attendance/${id}`),
  getList: params => request.get('/attendance/list', { params }),
  clockIn: () => request.post('/attendance/clockIn'),
  clockOut: () => request.post('/attendance/clockOut'),
  getToday: () => request.get('/attendance/today'),
  getStatistics: () => request.get('/attendance/statistics'),
  add: data => request.post('/attendance', data),
  update: data => request.put('/attendance', data),
  delete: id => request.delete(`/attendance/${id}`)
}

export const leaveApi = {
  getById: id => request.get(`/leave/${id}`),
  getList: params => request.get('/leave/list', { params }),
  getPendingCount: () => request.get('/leave/pendingCount'),
  add: data => request.post('/leave', data),
  update: data => request.put('/leave', data),
  delete: id => request.delete(`/leave/${id}`),
  approve: data => request.post('/leave/approve', data)
}

export const salaryApi = {
  getById: id => request.get(`/salary/${id}`),
  getList: params => request.get('/salary/list', { params }),
  add: data => request.post('/salary', data),
  update: data => request.put('/salary', data),
  delete: id => request.delete(`/salary/${id}`),
  pay: id => request.post(`/salary/pay/${id}`)
}

export const recruitmentApi = {
  getById: id => request.get(`/recruitment/${id}`),
  getList: params => request.get('/recruitment/list', { params }),
  getOpen: () => request.get('/recruitment/open'),
  getOpenCount: () => request.get('/recruitment/openCount'),
  add: data => request.post('/recruitment', data),
  update: data => request.put('/recruitment', data),
  delete: id => request.delete(`/recruitment/${id}`),
  close: id => request.post(`/recruitment/close/${id}`)
}

export const resumeApi = {
  getById: id => request.get(`/resume/${id}`),
  getList: params => request.get('/resume/list', { params }),
  add: data => request.post('/resume', data),
  update: data => request.put('/resume', data),
  delete: id => request.delete(`/resume/${id}`),
  updateStatus: data => request.post('/resume/updateStatus', data)
}

export const trainingApi = {
  getById: id => request.get(`/training/${id}`),
  getList: params => request.get('/training/list', { params }),
  add: data => request.post('/training', data),
  update: data => request.put('/training', data),
  delete: id => request.delete(`/training/${id}`)
}

export const contractApi = {
  getById: id => request.get(`/contract/${id}`),
  getList: params => request.get('/contract/list', { params }),
  getByEmployeeId: id => request.get(`/contract/employee/${id}`),
  add: data => request.post('/contract', data),
  update: data => request.put('/contract', data),
  delete: id => request.delete(`/contract/${id}`),
  terminate: id => request.post(`/contract/terminate/${id}`)
}

export const announcementApi = {
  getById: id => request.get(`/announcement/${id}`),
  getList: params => request.get('/announcement/list', { params }),
  getTop: limit => request.get('/announcement/top', { params: { limit } }),
  add: data => request.post('/announcement', data),
  update: data => request.put('/announcement', data),
  delete: id => request.delete(`/announcement/${id}`)
}
