import request from '@/utils/request'

// ==================== 用户相关 ====================
export const userApi = {
  login: (data) => request.post('/user/login', data),
  register: (data) => request.post('/user/register', data),
  getInfo: () => request.get('/user/info'),
  updateInfo: (data) => request.put('/user/info', data),
  changePassword: (oldPassword, newPassword) => 
    request.put(`/user/password?oldPassword=${oldPassword}&newPassword=${newPassword}`),
  submitAuth: (data) => request.post('/user/auth', null, { params: data })
}

// ==================== 租借相关 ====================
export const rentalApi = {
  start: (data) => request.post('/rental/start', data),
  end: (data) => request.post('/rental/end', data),
  getCurrent: () => request.get('/rental/current')
}

// ==================== 订单相关 ====================
export const orderApi = {
  getList: (params) => request.get('/order/list', { params }),
  getDetail: (orderId) => request.get(`/order/${orderId}`)
}

// ==================== 停车点相关 ====================
export const stationApi = {
  getList: () => request.get('/station/list'),
  getDetail: (id) => request.get(`/station/${id}`),
  getBikes: (id) => request.get(`/station/${id}/bikes`)
}

// ==================== 钱包相关 ====================
export const walletApi = {
  getBalance: () => request.get('/wallet/balance'),
  recharge: (data) => request.post('/wallet/recharge', data),
  refundDeposit: () => request.post('/wallet/refund-deposit'),
  getRechargeRecords: (params) => request.get('/wallet/recharge-records', { params })
}

// ==================== 故障上报相关 ====================
export const faultApi = {
  report: (data) => request.post('/fault/report', data),
  getMyReports: (params) => request.get('/fault/my-reports', { params })
}

// ==================== 管理端相关 ====================
export const adminApi = {
  login: (data) => request.post('/admin/login', data),
  getInfo: () => request.get('/admin/info'),
  getOverview: () => request.get('/admin/stats/overview'),
  
  // 用户管理
  getUserList: (params) => request.get('/admin/user/list', { params }),
  updateUserStatus: (userId, status) => request.put(`/admin/user/${userId}/status?status=${status}`),
  auditAuth: (userId, authStatus) => request.put(`/admin/user/${userId}/auth?authStatus=${authStatus}`),
  adjustCredit: (userId, creditScore) => request.put(`/admin/user/${userId}/credit?creditScore=${creditScore}`),
  
  // 车辆管理
  getBikeList: (params) => request.get('/admin/bike/list', { params }),
  addBike: (data) => request.post('/admin/bike/add', data),
  updateBike: (id, data) => request.put(`/admin/bike/${id}`, data),
  deleteBike: (id) => request.delete(`/admin/bike/${id}`),
  
  // 停车点管理
  getStationList: (params) => request.get('/admin/station/list', { params }),
  addStation: (data) => request.post('/admin/station/add', data),
  updateStation: (id, data) => request.put(`/admin/station/${id}`, data),
  deleteStation: (id) => request.delete(`/admin/station/${id}`),
  
  // 订单管理
  getOrderList: (params) => request.get('/admin/order/list', { params }),
  
  // 故障管理
  getFaultList: (params) => request.get('/admin/fault/list', { params }),
  handleFault: (id, status, handleResult) => 
    request.put(`/admin/fault/${id}/handle?status=${status}&handleResult=${handleResult || ''}`)
}
