import request from './request'

export const login = data => request.post('/user/login', data)
export const getUserInfo = () => request.get('/user/info')
export const updatePassword = data => request.put('/user/password', data)
export const getUserList = params => request.get('/user/page', { params })
export const addUser = data => request.post('/user', data)
export const updateUser = data => request.put('/user', data)
export const deleteUser = id => request.delete(`/user/${id}`)

export const getDisasterList = params => request.get('/disaster/page', { params })
export const addDisaster = data => request.post('/disaster', data)
export const updateDisaster = data => request.put('/disaster', data)
export const deleteDisaster = id => request.delete(`/disaster/${id}`)
export const getDisasterSelect = () => request.get('/disaster/select')

export const getCategoryList = params => request.get('/category/page', { params })
export const addCategory = data => request.post('/category', data)
export const updateCategory = data => request.put('/category', data)
export const deleteCategory = id => request.delete(`/category/${id}`)
export const getCategorySelect = () => request.get('/category/select')

export const getMaterialList = params => request.get('/material/page', { params })
export const addMaterial = data => request.post('/material', data)
export const updateMaterial = data => request.put('/material', data)
export const deleteMaterial = id => request.delete(`/material/${id}`)
export const getMaterialSelect = () => request.get('/material/select')

export const getWarehouseList = params => request.get('/warehouse/page', { params })
export const addWarehouse = data => request.post('/warehouse', data)
export const updateWarehouse = data => request.put('/warehouse', data)
export const deleteWarehouse = id => request.delete(`/warehouse/${id}`)
export const getWarehouseSelect = () => request.get('/warehouse/select')

export const getStockList = params => request.get('/stock/page', { params })
export const addStock = data => request.post('/stock', data)
export const updateStock = data => request.put('/stock', data)
export const getStockRecords = params => request.get('/stock/records', { params })

export const getDispatchList = params => request.get('/dispatch/page', { params })
export const addDispatch = data => request.post('/dispatch', data)
export const updateDispatch = data => request.put('/dispatch', data)
export const deleteDispatch = id => request.delete(`/dispatch/${id}`)
export const updateDispatchStatus = (id, status) => request.put(`/dispatch/${id}/status`, { status })

export const getTaskList = params => request.get('/task/page', { params })
export const addTask = data => request.post('/task', data)
export const updateTask = data => request.put('/task', data)
export const deleteTask = id => request.delete(`/task/${id}`)
export const updateTaskStatus = (id, status) => request.put(`/task/${id}/status`, { status })
export const getMyTasks = params => request.get('/task/my', { params })

export const getNoticeList = params => request.get('/notice/page', { params })
export const addNotice = data => request.post('/notice', data)
export const updateNotice = data => request.put('/notice', data)
export const deleteNotice = id => request.delete(`/notice/${id}`)
export const getPublishedNotices = () => request.get('/notice/published')

export const getStats = () => request.get('/stats/overview')
