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

export const getSupplierList = () => request.get('/api/supplier/list')
export const getSupplierPage = (params) => request.get('/api/supplier/page', { params })
export const addSupplier = (data) => request.post('/api/supplier', data)
export const updateSupplier = (data) => request.put('/api/supplier', data)
export const deleteSupplier = (id) => request.delete(`/api/supplier/${id}`)

export const getCustomerList = () => request.get('/api/customer/list')
export const getCustomerPage = (params) => request.get('/api/customer/page', { params })
export const addCustomer = (data) => request.post('/api/customer', data)
export const updateCustomer = (data) => request.put('/api/customer', data)
export const deleteCustomer = (id) => request.delete(`/api/customer/${id}`)

export const getCategoryList = () => request.get('/api/category/list')
export const getCategoryPage = (params) => request.get('/api/category/page', { params })
export const addCategory = (data) => request.post('/api/category', data)
export const updateCategory = (data) => request.put('/api/category', data)
export const deleteCategory = (id) => request.delete(`/api/category/${id}`)

export const getProductList = () => request.get('/api/product/list')
export const getProductPage = (params) => request.get('/api/product/page', { params })
export const addProduct = (data) => request.post('/api/product', data)
export const updateProduct = (data) => request.put('/api/product', data)
export const deleteProduct = (id) => request.delete(`/api/product/${id}`)

export const getPurchasePage = (params) => request.get('/api/purchase/page', { params })
export const addPurchase = (data) => request.post('/api/purchase', data)
export const updatePurchase = (data) => request.put('/api/purchase', data)
export const approvePurchase = (id) => request.put(`/api/purchase/approve/${id}`)
export const deletePurchase = (id) => request.delete(`/api/purchase/${id}`)

export const getSalePage = (params) => request.get('/api/sale/page', { params })
export const addSale = (data) => request.post('/api/sale', data)
export const updateSale = (data) => request.put('/api/sale', data)
export const approveSale = (id) => request.put(`/api/sale/approve/${id}`)
export const deleteSale = (id) => request.delete(`/api/sale/${id}`)

export const getStockPage = (params) => request.get('/api/stock/page', { params })

export const getAnnouncementList = () => request.get('/api/announcement/list')
export const getAnnouncementPage = (params) => request.get('/api/announcement/page', { params })
export const addAnnouncement = (data) => request.post('/api/announcement', data)
export const updateAnnouncement = (data) => request.put('/api/announcement', data)
export const deleteAnnouncement = (id) => request.delete(`/api/announcement/${id}`)

export const getDashboardStats = () => request.get('/api/dashboard/stats')
export const getDashboardTrend = () => request.get('/api/dashboard/trend')
