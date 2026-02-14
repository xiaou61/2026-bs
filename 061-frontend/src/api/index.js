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

export const getCategoryList = () => request.get('/api/category/list')
export const getCategoryPage = (params) => request.get('/api/category/page', { params })
export const addCategory = (data) => request.post('/api/category', data)
export const updateCategory = (data) => request.put('/api/category', data)
export const deleteCategory = (id) => request.delete(`/api/category/${id}`)

export const getItemList = (params) => request.get('/api/item/list', { params })
export const getItemPage = (params) => request.get('/api/item/page', { params })
export const getItemById = (id) => request.get(`/api/item/${id}`)
export const addItem = (data) => request.post('/api/item', data)
export const updateItem = (data) => request.put('/api/item', data)
export const updateItemStatus = (data) => request.put('/api/item/status', data)
export const deleteItem = (id) => request.delete(`/api/item/${id}`)

export const createOrder = (data) => request.post('/api/order', data)
export const getOrderPage = (params) => request.get('/api/order/page', { params })
export const getMyOrders = (params) => request.get('/api/order/my', { params })
export const getSaleOrders = (params) => request.get('/api/order/sale', { params })
export const payOrder = (id) => request.put(`/api/order/pay/${id}`)
export const deliverOrder = (id) => request.put(`/api/order/deliver/${id}`)
export const completeOrder = (id) => request.put(`/api/order/complete/${id}`)
export const cancelOrder = (id) => request.put(`/api/order/cancel/${id}`)

export const getReviewByItem = (itemId) => request.get(`/api/review/item/${itemId}`)
export const getReviewPage = (params) => request.get('/api/review/page', { params })
export const addReview = (data) => request.post('/api/review', data)
export const deleteReview = (id) => request.delete(`/api/review/${id}`)

export const toggleFavorite = (itemId) => request.post(`/api/favorite/${itemId}`)
export const getMyFavorite = () => request.get('/api/favorite/my')
export const checkFavorite = (itemId) => request.get(`/api/favorite/check/${itemId}`)

export const getComplaintPage = (params) => request.get('/api/complaint/page', { params })
export const getMyComplaint = (params) => request.get('/api/complaint/my', { params })
export const addComplaint = (data) => request.post('/api/complaint', data)
export const replyComplaint = (data) => request.put('/api/complaint/reply', data)

export const getAnnouncementList = () => request.get('/api/announcement/list')
export const getAnnouncementPage = (params) => request.get('/api/announcement/page', { params })
export const addAnnouncement = (data) => request.post('/api/announcement', data)
export const updateAnnouncement = (data) => request.put('/api/announcement', data)
export const deleteAnnouncement = (id) => request.delete(`/api/announcement/${id}`)

export const getDashboardStats = () => request.get('/api/dashboard/stats')
export const getCategoryStats = () => request.get('/api/dashboard/categoryStats')
export const getOrderTrend = () => request.get('/api/dashboard/orderTrend')
export const getHotItems = () => request.get('/api/dashboard/hotItems')

export const uploadFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/api/upload', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
