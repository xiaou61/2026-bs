import request from './request'

export const login = (data) => request.post('/auth/login', data)
export const register = (data) => request.post('/auth/register', data)
export const logout = () => request.post('/auth/logout')
export const getUserInfo = () => request.get('/auth/info')

export const getProfile = () => request.get('/user/profile')
export const updateProfile = (data) => request.put('/user/profile/update', data)

export const getChildList = (params) => request.get('/child/list', { params })
export const getPublicChildList = (params) => request.get('/child/public/list', { params })
export const getChildDetail = (id) => request.get(`/child/detail/${id}`)
export const addChild = (data) => request.post('/child/add', data)
export const updateChild = (data) => request.put('/child/update', data)
export const deleteChild = (id) => request.delete(`/child/delete/${id}`)

export const getAdopterList = (params) => request.get('/adopter/list', { params })

export const getApplicationList = (params) => request.get('/application/list', { params })
export const getMyApplicationList = (params) => request.get('/application/my/list', { params })
export const addApplication = (data) => request.post('/application/add', data)
export const reviewApplication = (params) => request.put('/application/review', null, { params })
export const getMaterialList = (params) => request.get('/application/material/list', { params })
export const addMaterial = (data) => request.post('/application/material/add', data)

export const getHomeVisitList = (params) => request.get('/home-visit/list', { params })
export const addHomeVisit = (data) => request.post('/home-visit/add', data)
export const updateHomeVisit = (data) => request.put('/home-visit/update', data)

export const getMatchList = (params) => request.get('/match/list', { params })
export const addMatch = (data) => request.post('/match/add', data)
export const approveMatch = (params) => request.put('/match/approve', null, { params })

export const getAgreementList = (params) => request.get('/agreement/list', { params })
export const addAgreement = (data) => request.post('/agreement/add', data)
export const updateAgreement = (data) => request.put('/agreement/update', data)

export const getFollowList = (params) => request.get('/follow/list', { params })
export const addFollow = (data) => request.post('/follow/add', data)
export const updateFollow = (data) => request.put('/follow/update', data)

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const getPublicNoticeList = () => request.get('/notice/public/list')
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getDashboard = () => request.get('/statistics/dashboard')
