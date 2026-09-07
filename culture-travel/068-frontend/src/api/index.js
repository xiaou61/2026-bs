import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const register = (data) => request.post('/api/auth/register', data)
export const getUserInfo = () => request.get('/api/auth/info')
export const updatePassword = (data) => request.put('/api/auth/password', data)
export const logout = () => request.post('/api/auth/logout')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const updateUserStatus = (data) => request.put('/api/user/status', data)
export const updateProfile = (data) => request.put('/api/user/profile', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getSpotList = (params) => request.get('/api/spot/list', { params })
export const getSpotPage = (params) => request.get('/api/spot/page', { params })
export const addSpot = (data) => request.post('/api/spot', data)
export const updateSpot = (data) => request.put('/api/spot', data)
export const updateSpotStatus = (data) => request.put('/api/spot/status', data)
export const deleteSpot = (id) => request.delete(`/api/spot/${id}`)

export const getTravelerPage = (params) => request.get('/api/traveler/page', { params })
export const getTravelerList = () => request.get('/api/traveler/list')
export const addTraveler = (data) => request.post('/api/traveler', data)
export const updateTraveler = (data) => request.put('/api/traveler', data)
export const deleteTraveler = (id) => request.delete(`/api/traveler/${id}`)

export const getFavoritePage = (params) => request.get('/api/favorite/page', { params })
export const toggleFavorite = (data) => request.post('/api/favorite/toggle', data)

export const getOrderPage = (params) => request.get('/api/order/page', { params })
export const getMyOrderPage = (params) => request.get('/api/order/my-page', { params })
export const exportOrder = (params) => request.get('/api/order/export', { params })
export const addOrder = (data) => request.post('/api/order', data)
export const cancelOrder = (data) => request.put('/api/order/cancel', data)
export const payOrder = (data) => request.put('/api/order/pay', data)
export const finishOrder = (data) => request.put('/api/order/finish', data)

export const getReviewPage = (params) => request.get('/api/review/page', { params })
export const getMyReviewPage = (params) => request.get('/api/review/my-page', { params })
export const addReview = (data) => request.post('/api/review', data)
export const replyReview = (data) => request.put('/api/review/reply', data)
export const updateReviewStatus = (data) => request.put('/api/review/status', data)

export const getComplaintPage = (params) => request.get('/api/complaint/page', { params })
export const getMyComplaintPage = (params) => request.get('/api/complaint/my-page', { params })
export const addComplaint = (data) => request.post('/api/complaint', data)
export const handleComplaint = (data) => request.put('/api/complaint/handle', data)

export const getDashboardStats = () => request.get('/api/dashboard/stats')
export const getDashboardTrend = () => request.get('/api/dashboard/trend')
