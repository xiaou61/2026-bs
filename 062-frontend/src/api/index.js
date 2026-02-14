import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const register = (data) => request.post('/api/auth/register', data)
export const getUserInfo = () => request.get('/api/auth/info')
export const updatePassword = (data) => request.put('/api/auth/password', data)
export const logout = () => request.post('/api/auth/logout')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const getRiskUserList = () => request.get('/api/user/risk-list')
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const updateUserStatus = (data) => request.put('/api/user/status', data)
export const updateProfile = (data) => request.put('/api/user/profile', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getBlacklistList = () => request.get('/api/blacklist/list')
export const getBlacklistPage = (params) => request.get('/api/blacklist/page', { params })
export const addBlacklist = (data) => request.post('/api/blacklist', data)
export const updateBlacklist = (data) => request.put('/api/blacklist', data)
export const deleteBlacklist = (id) => request.delete(`/api/blacklist/${id}`)

export const getRuleList = () => request.get('/api/rule/list')
export const getRulePage = (params) => request.get('/api/rule/page', { params })
export const addRule = (data) => request.post('/api/rule', data)
export const updateRule = (data) => request.put('/api/rule', data)
export const deleteRule = (id) => request.delete(`/api/rule/${id}`)

export const getEventPage = (params) => request.get('/api/event/page', { params })
export const getMyEventPage = (params) => request.get('/api/event/my', { params })
export const getEventById = (id) => request.get(`/api/event/${id}`)
export const reportEvent = (data) => request.post('/api/event/report', data)
export const updateEventStatus = (data) => request.put('/api/event/status', data)

export const getAlertPage = (params) => request.get('/api/alert/page', { params })
export const getMyAlertPage = (params) => request.get('/api/alert/my', { params })
export const getAlertById = (id) => request.get(`/api/alert/${id}`)
export const assignAlert = (data) => request.put('/api/alert/assign', data)
export const handleAlert = (data) => request.put('/api/alert/handle', data)

export const getCasePage = (params) => request.get('/api/case/page', { params })
export const getCaseById = (id) => request.get(`/api/case/${id}`)
export const addCase = (data) => request.post('/api/case', data)
export const updateCase = (data) => request.put('/api/case', data)
export const closeCase = (id, result) => request.put(`/api/case/close/${id}`, null, { params: { result } })

export const getAppealPage = (params) => request.get('/api/appeal/page', { params })
export const getMyAppealPage = (params) => request.get('/api/appeal/my', { params })
export const addAppeal = (data) => request.post('/api/appeal', data)
export const replyAppeal = (data) => request.put('/api/appeal/reply', data)

export const getAnnouncementList = () => request.get('/api/announcement/list')
export const getAnnouncementPage = (params) => request.get('/api/announcement/page', { params })
export const addAnnouncement = (data) => request.post('/api/announcement', data)
export const updateAnnouncement = (data) => request.put('/api/announcement', data)
export const deleteAnnouncement = (id) => request.delete(`/api/announcement/${id}`)

export const getDashboardStats = () => request.get('/api/dashboard/stats')
export const getRiskTrend = () => request.get('/api/dashboard/riskTrend')
export const getRiskLevelDist = () => request.get('/api/dashboard/riskLevelDist')
export const getTopRules = () => request.get('/api/dashboard/topRules')
export const getTopUsers = () => request.get('/api/dashboard/topUsers')

export const getLogPage = (params) => request.get('/api/log/page', { params })
