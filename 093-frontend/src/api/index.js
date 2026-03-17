import request from './request'

export const login = data => request.post('/auth/login', data)
export const register = data => request.post('/auth/register', data)
export const getInfo = () => request.get('/auth/info')
export const updatePassword = data => request.put('/auth/password', data)
export const logout = () => request.post('/auth/logout')

export const getUserList = params => request.get('/user/list', { params })
export const getUserDetail = id => request.get(`/user/${id}`)
export const saveUser = data => request.post('/user/save', data)
export const updateUserStatus = (id, status) => request.put(`/user/status/${id}/${status}`)
export const deleteUser = id => request.delete(`/user/${id}`)
export const updateProfile = data => request.put('/user/profile', data)

export const getLocationList = params => request.get('/location/list', { params })
export const getLocationOptions = () => request.get('/location/all')
export const saveLocation = data => request.post('/location/save', data)
export const deleteLocation = id => request.delete(`/location/${id}`)

export const getMachinePublicList = () => request.get('/machine/public/list')
export const getMachineList = params => request.get('/machine/list', { params })
export const getMachineOptions = () => request.get('/machine/all')
export const saveMachine = data => request.post('/machine/save', data)
export const deleteMachine = id => request.delete(`/machine/${id}`)

export const getCategoryList = params => request.get('/category/list', { params })
export const getCategoryOptions = () => request.get('/category/all')
export const saveCategory = data => request.post('/category/save', data)
export const deleteCategory = id => request.delete(`/category/${id}`)

export const getProductPublicList = () => request.get('/product/public/list')
export const getProductList = params => request.get('/product/list', { params })
export const getProductOptions = () => request.get('/product/all')
export const saveProduct = data => request.post('/product/save', data)
export const deleteProduct = id => request.delete(`/product/${id}`)

export const getSlotList = params => request.get('/slot/list', { params })
export const getMachineSlotList = machineId => request.get(`/slot/machine/${machineId}`)
export const saveSlot = data => request.post('/slot/save', data)
export const deleteSlot = id => request.delete(`/slot/${id}`)

export const getReplenishList = params => request.get('/replenish/list', { params })
export const saveReplenish = data => request.post('/replenish/save', data)

export const createOrder = data => request.post('/order/create', data)
export const getOrderList = params => request.get('/order/list', { params })
export const getOrderDetail = id => request.get(`/order/${id}`)
export const cancelOrder = id => request.put(`/order/cancel/${id}`)

export const recharge = data => request.post('/payment/recharge', data)
export const balancePay = data => request.post('/payment/balance', data)
export const getPaymentList = params => request.get('/payment/list', { params })

export const getShipmentList = params => request.get('/shipment/list', { params })

export const getFaultList = params => request.get('/fault/list', { params })
export const addFault = data => request.post('/fault/add', data)
export const handleFault = (id, data) => request.put(`/fault/handle/${id}`, data)
export const deleteFault = id => request.delete(`/fault/${id}`)

export const getPublicNoticeList = () => request.get('/notice/public/list')
export const getNoticeList = params => request.get('/notice/list', { params })
export const saveNotice = data => request.post('/notice/save', data)
export const deleteNotice = id => request.delete(`/notice/${id}`)

export const getDashboardStats = () => request.get('/statistics/dashboard')
export const getSalesTrend = params => request.get('/statistics/sales-trend', { params })
export const getHotProducts = () => request.get('/statistics/hot-products')
export const getMachineRank = () => request.get('/statistics/machine-rank')
