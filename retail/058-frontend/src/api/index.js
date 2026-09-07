import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const register = (data) => request.post('/api/auth/register', data)
export const getUserInfo = () => request.get('/api/auth/info')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getCategoryList = () => request.get('/api/category/list')
export const getCategoryPage = (params) => request.get('/api/category/page', { params })
export const addCategory = (data) => request.post('/api/category', data)
export const updateCategory = (data) => request.put('/api/category', data)
export const deleteCategory = (id) => request.delete(`/api/category/${id}`)

export const getProductList = (params) => request.get('/api/product/list', { params })
export const getProductPage = (params) => request.get('/api/product/page', { params })
export const getProductById = (id) => request.get(`/api/product/${id}`)
export const addProduct = (data) => request.post('/api/product', data)
export const updateProduct = (data) => request.put('/api/product', data)
export const deleteProduct = (id) => request.delete(`/api/product/${id}`)

export const getAddressList = () => request.get('/api/address/list')
export const addAddress = (data) => request.post('/api/address', data)
export const updateAddress = (data) => request.put('/api/address', data)
export const deleteAddress = (id) => request.delete(`/api/address/${id}`)
export const setDefaultAddress = (id) => request.put(`/api/address/default/${id}`)

export const getAreaList = () => request.get('/api/area/list')
export const getAreaPage = (params) => request.get('/api/area/page', { params })
export const addArea = (data) => request.post('/api/area', data)
export const updateArea = (data) => request.put('/api/area', data)
export const deleteArea = (id) => request.delete(`/api/area/${id}`)

export const getMySubscriptions = () => request.get('/api/subscription/my')
export const getSubscriptionPage = (params) => request.get('/api/subscription/page', { params })
export const addSubscription = (data) => request.post('/api/subscription', data)
export const updateSubscription = (data) => request.put('/api/subscription', data)
export const pauseSubscription = (id) => request.put(`/api/subscription/pause/${id}`)
export const resumeSubscription = (id) => request.put(`/api/subscription/resume/${id}`)
export const deleteSubscription = (id) => request.delete(`/api/subscription/${id}`)

export const getMyOrders = (params) => request.get('/api/order/my', { params })
export const getOrderPage = (params) => request.get('/api/order/page', { params })
export const getOrderById = (id) => request.get(`/api/order/${id}`)
export const cancelOrder = (id) => request.put(`/api/order/cancel/${id}`)

export const getRouteList = () => request.get('/api/route/list')
export const getRoutePage = (params) => request.get('/api/route/page', { params })
export const addRoute = (data) => request.post('/api/route', data)
export const updateRoute = (data) => request.put('/api/route', data)
export const deleteRoute = (id) => request.delete(`/api/route/${id}`)

export const getTodayTasks = () => request.get('/api/delivery/today')
export const getDeliveryHistory = (params) => request.get('/api/delivery/history', { params })
export const completeDelivery = (id) => request.put(`/api/delivery/complete/${id}`)
export const exceptionDelivery = (id, data) => request.put(`/api/delivery/exception/${id}`, data)

export const getMyComplaints = () => request.get('/api/complaint/my')
export const getComplaintPage = (params) => request.get('/api/complaint/page', { params })
export const addComplaint = (data) => request.post('/api/complaint', data)
export const replyComplaint = (data) => request.put('/api/complaint/reply', data)

export const getMyNotifications = () => request.get('/api/notification/my')
export const readNotification = (id) => request.put(`/api/notification/read/${id}`)
export const readAllNotifications = () => request.put('/api/notification/readAll')

export const getDashboardStats = () => request.get('/api/stats/dashboard')
export const getOrderStats = () => request.get('/api/stats/order')
export const getDeliveryStats = () => request.get('/api/stats/delivery')
