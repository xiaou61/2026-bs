import request from './request'

export const login = (data) => request.post('/auth/login', data)
export const logout = () => request.post('/auth/logout')
export const getUserInfo = () => request.get('/auth/info')

export const getCategoryList = (params) => request.get('/category/list', { params })
export const getCategoryEnabled = () => request.get('/category/enabled')
export const addCategory = (data) => request.post('/category/add', data)
export const updateCategory = (data) => request.put('/category/update', data)
export const deleteCategory = (id) => request.delete(`/category/delete/${id}`)

export const getMaterialList = (params) => request.get('/material/list', { params })
export const getMaterialDetail = (id) => request.get(`/material/detail/${id}`)
export const addMaterial = (data) => request.post('/material/add', data)
export const updateMaterial = (data) => request.put('/material/update', data)
export const publishMaterial = (id, publishStatus) => request.put('/material/publish', null, { params: { id, publishStatus } })
export const deleteMaterial = (id) => request.delete(`/material/delete/${id}`)
export const downloadMaterial = (id) => request.post(`/material/download/${id}`)

export const getAuditList = (params) => request.get('/audit/list', { params })
export const submitAudit = (data) => request.put('/audit/submit', data)

export const getFavoriteList = () => request.get('/favorite/list')
export const addFavorite = (materialId) => request.post(`/favorite/add/${materialId}`)
export const cancelFavorite = (materialId) => request.delete(`/favorite/cancel/${materialId}`)
export const getStudyList = () => request.get('/favorite/study/list')
export const updateStudy = (data) => request.put('/favorite/study/update', data)
export const deleteStudy = (id) => request.delete(`/favorite/study/delete/${id}`)

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getDashboard = () => request.get('/statistics/dashboard')
