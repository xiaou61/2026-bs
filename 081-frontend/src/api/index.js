import request from './request'

export const login = (data) => request.post('/user/login', data)
export const register = (data) => request.post('/user/register', data)
export const getUserInfo = () => request.get('/user/info')
export const getUserList = (params) => request.get('/user/list', { params })
export const addUser = (data) => request.post('/user/add', data)
export const updateUser = (data) => request.put('/user/update', data)
export const deleteUser = (id) => request.delete(`/user/delete/${id}`)

export const getCategoryList = (params) => request.get('/category/list', { params })
export const getCategoryPublicList = () => request.get('/category/public/list')
export const addCategory = (data) => request.post('/category/add', data)
export const updateCategory = (data) => request.put('/category/update', data)
export const deleteCategory = (id) => request.delete(`/category/delete/${id}`)

export const getTechnicianList = (params) => request.get('/technician/list', { params })
export const addTechnician = (data) => request.post('/technician/add', data)
export const updateTechnician = (data) => request.put('/technician/update', data)
export const deleteTechnician = (id) => request.delete(`/technician/delete/${id}`)

export const getOrderList = (params) => request.get('/order/list', { params })
export const getOrderDetail = (id) => request.get(`/order/detail/${id}`)
export const addOrder = (data) => request.post('/order/add', data)
export const updateOrder = (data) => request.put('/order/update', data)
export const deleteOrder = (id) => request.delete(`/order/delete/${id}`)
export const assignOrder = (data) => request.put('/order/assign', data)
export const updateOrderStatus = (data) => request.put('/order/status', data)
export const userPayOrder = (id, data) => request.put(`/order/user/pay/${id}`, data || {})
export const getTechnicianOrderList = (params) => request.get('/order/technician/list', { params })
export const technicianUpdateOrderStatus = (data) => request.put('/order/technician/status', data)

export const getProcessList = (orderId) => request.get(`/process/list/${orderId}`)
export const technicianAddProcess = (data) => request.post('/process/technician/add', data)

export const getPartList = (params) => request.get('/part/list', { params })
export const addPart = (data) => request.post('/part/add', data)
export const updatePart = (data) => request.put('/part/update', data)
export const deletePart = (id) => request.delete(`/part/delete/${id}`)

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getEvaluateList = (params) => request.get('/evaluate/list', { params })

export const getDashboard = () => request.get('/statistics/dashboard')
export const getOrderTrend = (params) => request.get('/statistics/order-trend', { params })
export const uploadFile = (formData) => request.post('/common/upload', formData, {
  headers: { 'Content-Type': 'multipart/form-data' }
})
