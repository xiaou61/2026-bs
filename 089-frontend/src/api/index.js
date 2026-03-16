import request from './request'

export const login = data => request.post('/auth/login', data)
export const register = data => request.post('/auth/register', data)
export const getInfo = () => request.get('/auth/info')
export const updatePassword = data => request.put('/auth/password', data)
export const logout = () => request.post('/auth/logout')

export const getUserList = params => request.get('/user/list', { params })
export const saveUser = data => request.post('/user/save', data)
export const updateUserStatus = (id, status) => request.put(`/user/status/${id}/${status}`)
export const deleteUser = id => request.delete(`/user/${id}`)
export const updateProfile = data => request.put('/user/profile', data)

export const getTrainList = params => request.get('/train/list', { params })
export const getTrainEnabledList = () => request.get('/train/enabled')
export const saveTrain = data => request.post('/train/save', data)
export const deleteTrain = id => request.delete(`/train/${id}`)

export const getStationList = params => request.get('/station/list', { params })
export const getStationEnabledList = () => request.get('/station/enabled')
export const saveStation = data => request.post('/station/save', data)
export const deleteStation = id => request.delete(`/station/${id}`)

export const getCarriageList = params => request.get('/carriage/list', { params })
export const getCarriageEnabledList = () => request.get('/carriage/enabled')
export const saveCarriage = data => request.post('/carriage/save', data)
export const deleteCarriage = id => request.delete(`/carriage/${id}`)

export const getScheduleList = params => request.get('/schedule/list', { params })
export const getScheduleEnabledList = () => request.get('/schedule/enabled')
export const saveSchedule = data => request.post('/schedule/save', data)
export const deleteSchedule = id => request.delete(`/schedule/${id}`)

export const getSeatList = scheduleId => request.get(`/seat/schedule/${scheduleId}`)
export const lockSeat = data => request.post('/seat/lock', data)
export const unlockSeat = data => request.post('/seat/unlock', data)

export const createOrder = data => request.post('/order/create', data)
export const getOrderList = params => request.get('/order/list', { params })
export const getMyOrderList = params => request.get('/order/my', { params })
export const getOrderDetail = id => request.get(`/order/${id}`)
export const cancelOrder = id => request.put(`/order/cancel/${id}`)
export const finishOrder = id => request.put(`/order/finish/${id}`)

export const createPayment = data => request.post('/payment/create', data)
export const balancePay = data => request.post('/payment/balance', data)
export const recharge = data => request.post('/payment/recharge', data)
export const getPaymentList = params => request.get('/payment/list', { params })

export const getTicketList = params => request.get('/ticket/list', { params })
export const getMyTicketList = params => request.get('/ticket/my', { params })
export const getTicketDetail = id => request.get(`/ticket/${id}`)
export const verifyTicket = data => request.post('/ticket/verify', data)

export const getPassengerList = params => request.get('/passenger/list', { params })
export const getPassengerAll = () => request.get('/passenger/all')
export const savePassenger = data => request.post('/passenger/save', data)
export const deletePassenger = id => request.delete(`/passenger/${id}`)

export const getAfterSaleList = params => request.get('/after-sale/list', { params })
export const applyAfterSale = data => request.post('/after-sale/apply', data)
export const reviewAfterSale = (id, data) => request.put(`/after-sale/review/${id}`, data)

export const getPublicNoticeList = () => request.get('/notice/public/list')
export const getNoticeList = params => request.get('/notice/list', { params })
export const saveNotice = data => request.post('/notice/save', data)
export const deleteNotice = id => request.delete(`/notice/${id}`)

export const getDashboardStats = () => request.get('/statistics/dashboard')
export const getSalesTrend = params => request.get('/statistics/sales-trend', { params })
export const getHotTrain = () => request.get('/statistics/hot-train')
