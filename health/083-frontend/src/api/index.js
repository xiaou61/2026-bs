import request from './request'

export const login = (data) => request.post('/auth/login', data)
export const getUserInfo = () => request.get('/auth/info')
export const logout = () => request.post('/auth/logout')

export const getElderList = (params) => request.get('/elder/list', { params })
export const getElderAll = () => request.get('/elder/all')
export const addElder = (data) => request.post('/elder/add', data)
export const updateElder = (data) => request.put('/elder/update', data)
export const deleteElder = (id) => request.delete(`/elder/delete/${id}`)

export const getItemList = (params) => request.get('/item/list', { params })
export const getItemAll = () => request.get('/item/all')
export const addItem = (data) => request.post('/item/add', data)
export const updateItem = (data) => request.put('/item/update', data)
export const deleteItem = (id) => request.delete(`/item/delete/${id}`)

export const getPackageList = (params) => request.get('/package/list', { params })
export const getPackageAll = () => request.get('/package/all')
export const addPackage = (data) => request.post('/package/add', data)
export const updatePackage = (data) => request.put('/package/update', data)
export const deletePackage = (id) => request.delete(`/package/delete/${id}`)

export const getAppointmentList = (params) => request.get('/appointment/list', { params })
export const addAppointment = (data) => request.post('/appointment/add', data)
export const updateAppointment = (data) => request.put('/appointment/update', data)
export const updateAppointmentStatus = (id, status) => request.put('/appointment/status', null, { params: { id, status } })
export const deleteAppointment = (id) => request.delete(`/appointment/delete/${id}`)

export const getResultList = (params) => request.get('/result/list', { params })
export const addResult = (data) => request.post('/result/add', data)
export const updateResult = (data) => request.put('/result/update', data)
export const deleteResult = (id) => request.delete(`/result/delete/${id}`)

export const getWarningList = (params) => request.get('/warning/list', { params })
export const updateWarningStatus = (id, status) => request.put('/warning/status', null, { params: { id, status } })
export const deleteWarning = (id) => request.delete(`/warning/delete/${id}`)

export const getFollowUpList = (params) => request.get('/follow-up/list', { params })
export const addFollowUp = (data) => request.post('/follow-up/add', data)
export const updateFollowUp = (data) => request.put('/follow-up/update', data)
export const deleteFollowUp = (id) => request.delete(`/follow-up/delete/${id}`)

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const getNoticePublicList = () => request.get('/notice/public/list')
export const getNoticePublicDetail = (id) => request.get(`/notice/public/detail/${id}`)
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getDashboard = () => request.get('/statistics/dashboard')
