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

export const getAreaList = params => request.get('/area/list', { params })
export const getAreaOptions = () => request.get('/area/all')
export const saveArea = data => request.post('/area/save', data)
export const deleteArea = id => request.delete(`/area/${id}`)

export const getShopPublicList = () => request.get('/shop/public/list')
export const getShopList = params => request.get('/shop/list', { params })
export const getShopOptions = () => request.get('/shop/all')
export const saveShop = data => request.post('/shop/save', data)
export const deleteShop = id => request.delete(`/shop/${id}`)

export const getPetPublicList = () => request.get('/pet/public/list')
export const getPetList = params => request.get('/pet/list', { params })
export const savePet = data => request.post('/pet/save', data)
export const deletePet = id => request.delete(`/pet/${id}`)

export const getCategoryList = params => request.get('/category/list', { params })
export const getCategoryOptions = () => request.get('/category/all')
export const saveCategory = data => request.post('/category/save', data)
export const deleteCategory = id => request.delete(`/category/${id}`)

export const getMenuPublicList = () => request.get('/menu/public/list')
export const getMenuList = params => request.get('/menu/list', { params })
export const getMenuOptions = () => request.get('/menu/all')
export const saveMenu = data => request.post('/menu/save', data)
export const deleteMenu = id => request.delete(`/menu/${id}`)

export const getSeatList = params => request.get('/seat/list', { params })
export const getShopSeatList = shopId => request.get(`/seat/shop/${shopId}`)
export const saveSeat = data => request.post('/seat/save', data)
export const deleteSeat = id => request.delete(`/seat/${id}`)

export const getReservationList = params => request.get('/reservation/list', { params })
export const createReservation = data => request.post('/reservation/create', data)
export const updateReservationStatus = (id, data) => request.put(`/reservation/status/${id}`, data)
export const cancelReservation = id => request.put(`/reservation/cancel/${id}`)

export const createOrder = data => request.post('/order/create', data)
export const getOrderList = params => request.get('/order/list', { params })
export const getOrderDetail = id => request.get(`/order/${id}`)
export const cancelOrder = id => request.put(`/order/cancel/${id}`)

export const recharge = data => request.post('/payment/recharge', data)
export const balancePay = data => request.post('/payment/balance', data)
export const getPaymentList = params => request.get('/payment/list', { params })

export const getReviewList = params => request.get('/review/list', { params })
export const addReview = data => request.post('/review/add', data)
export const replyReview = (id, data) => request.put(`/review/reply/${id}`, data)
export const deleteReview = id => request.delete(`/review/${id}`)

export const getPublicNoticeList = () => request.get('/notice/public/list')
export const getNoticeList = params => request.get('/notice/list', { params })
export const saveNotice = data => request.post('/notice/save', data)
export const deleteNotice = id => request.delete(`/notice/${id}`)

export const getDashboardStats = () => request.get('/statistics/dashboard')
export const getSalesTrend = params => request.get('/statistics/sales-trend', { params })
export const getHotMenus = () => request.get('/statistics/hot-menus')
export const getShopRank = () => request.get('/statistics/shop-rank')
