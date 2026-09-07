import request from '@/utils/request'

// 房源API
export const houseApi = {
  getList: (params) => request.get('/house/list', { params }),
  getDetail: (id) => request.get(`/house/detail/${id}`),
  publish: (data) => request.post('/house/publish', data),
  update: (id, data) => request.put(`/house/${id}`, data),
  delete: (id) => request.delete(`/house/${id}`),
  getMyHouses: (params) => request.get('/house/my', { params }),
  updateStatus: (id, status) => request.put(`/house/${id}/status`, { status }),
  toggleFavorite: (id) => request.post(`/house/${id}/favorite`),
  getFavorites: (params) => request.get('/house/favorites', { params })
}

// 预约API
export const appointmentApi = {
  create: (data) => request.post('/appointment', data),
  getList: (params) => request.get('/appointment/list', { params }),
  confirm: (id) => request.put(`/appointment/${id}/confirm`),
  reject: (id, reason) => request.put(`/appointment/${id}/reject`, { reason }),
  complete: (id) => request.put(`/appointment/${id}/complete`),
  cancel: (id) => request.put(`/appointment/${id}/cancel`)
}

// 合同API
export const contractApi = {
  create: (data) => request.post('/contract', data),
  getList: (params) => request.get('/contract/list', { params }),
  getDetail: (id) => request.get(`/contract/${id}`),
  sign: (id) => request.put(`/contract/${id}/sign`),
  terminate: (id, reason) => request.put(`/contract/${id}/terminate`, { reason })
}

// 账单API
export const billApi = {
  getList: (params) => request.get('/bill/list', { params }),
  pay: (id) => request.post(`/bill/${id}/pay`),
  getStatistics: () => request.get('/bill/statistics')
}

// 报修API
export const repairApi = {
  create: (data) => request.post('/repair', data),
  getList: (params) => request.get('/repair/list', { params }),
  process: (id) => request.put(`/repair/${id}/process`),
  complete: (id, result) => request.put(`/repair/${id}/complete`, { result })
}

// 评价API
export const reviewApi = {
  create: (data) => request.post('/review', data),
  getByHouse: (houseId, params) => request.get(`/review/house/${houseId}`, { params })
}

// 消息API
export const messageApi = {
  getList: (params) => request.get('/message/list', { params }),
  markRead: (id) => request.put(`/message/${id}/read`),
  markAllRead: () => request.put('/message/read-all'),
  getUnreadCount: () => request.get('/message/unread-count')
}

// 管理员API
export const adminApi = {
  getStatistics: () => request.get('/admin/statistics'),
  getUsers: (params) => request.get('/admin/users', { params }),
  updateUserStatus: (id, status) => request.put(`/admin/user/${id}/status`, { status }),
  getHouses: (params) => request.get('/admin/houses', { params }),
  auditHouse: (id, status) => request.put(`/admin/house/${id}/audit`, { status }),
  getContracts: (params) => request.get('/admin/contracts', { params })
}

// 文件上传
export const uploadFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/file/upload', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
