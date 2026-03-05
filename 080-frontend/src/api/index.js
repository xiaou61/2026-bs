import request from './request'

export const login = (data) => request.post('/user/login', data)
export const register = (data) => request.post('/user/register', data)
export const getUserInfo = () => request.get('/user/info')
export const getUserList = (params) => request.get('/user/list', { params })
export const addUser = (data) => request.post('/user/add', data)
export const updateUser = (data) => request.put('/user/update', data)
export const deleteUser = (id) => request.delete(`/user/delete/${id}`)

export const getChildList = (params) => request.get('/child/list', { params })
export const getChildDetail = (id) => request.get(`/child/detail/${id}`)
export const addChild = (data) => request.post('/child/add', data)
export const updateChild = (data) => request.put('/child/update', data)
export const deleteChild = (id) => request.delete(`/child/delete/${id}`)

export const getDonorList = (params) => request.get('/donor/list', { params })
export const addDonor = (data) => request.post('/donor/add', data)
export const updateDonor = (data) => request.put('/donor/update', data)
export const deleteDonor = (id) => request.delete(`/donor/delete/${id}`)

export const getApplicationList = (params) => request.get('/application/list', { params })
export const submitApplication = (data) => request.post('/application/submit', data)
export const reviewApplication = (data) => request.put('/application/review', data)

export const getDonationList = (params) => request.get('/donation/list', { params })
export const addDonation = (data) => request.post('/donation/add', data)
export const confirmDonation = (id) => request.put(`/donation/confirm/${id}`)

export const getProjectList = (params) => request.get('/project/list', { params })
export const addProject = (data) => request.post('/project/add', data)
export const updateProject = (data) => request.put('/project/update', data)
export const deleteProject = (id) => request.delete(`/project/delete/${id}`)

export const getFundList = (params) => request.get('/fund/list', { params })
export const addFund = (data) => request.post('/fund/add', data)

export const getFeedbackList = (params) => request.get('/feedback/list', { params })
export const addFeedback = (data) => request.post('/feedback/add', data)

export const getGrowthList = (params) => request.get('/growth/list', { params })
export const addGrowth = (data) => request.post('/growth/add', data)

export const getAnnouncementList = (params) => request.get('/announcement/list', { params })
export const addAnnouncement = (data) => request.post('/announcement/add', data)
export const updateAnnouncement = (data) => request.put('/announcement/update', data)
export const deleteAnnouncement = (id) => request.delete(`/announcement/delete/${id}`)

export const getSponsorList = (params) => request.get('/sponsor/list', { params })
export const addSponsor = (data) => request.post('/sponsor/add', data)
export const updateSponsor = (data) => request.put('/sponsor/update', data)

export const getDashboard = () => request.get('/statistics/dashboard')
export const getDonationTrend = (params) => request.get('/statistics/donation-trend', { params })
export const getRegionDistribution = () => request.get('/statistics/region-distribution')
