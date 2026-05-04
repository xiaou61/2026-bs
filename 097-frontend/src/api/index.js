import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const getUserInfo = () => request.get('/api/auth/info')
export const logout = () => request.post('/api/auth/logout')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const updateUserStatus = (id, status) => request.put(`/api/user/status/${id}/${status}`)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getTeamPage = (params) => request.get('/api/team/page', { params })
export const addTeam = (data) => request.post('/api/team', data)
export const updateTeam = (data) => request.put('/api/team', data)
export const deleteTeam = (id) => request.delete(`/api/team/${id}`)

export const getCategoryPage = (params) => request.get('/api/category/page', { params })
export const addCategory = (data) => request.post('/api/category', data)
export const updateCategory = (data) => request.put('/api/category', data)
export const deleteCategory = (id) => request.delete(`/api/category/${id}`)

export const getAssetPage = (params) => request.get('/api/asset/page', { params })
export const addAsset = (data) => request.post('/api/asset', data)
export const updateAsset = (data) => request.put('/api/asset', data)
export const deleteAsset = (id) => request.delete(`/api/asset/${id}`)

export const getVersionPage = (params) => request.get('/api/version/page', { params })
export const addVersion = (data) => request.post('/api/version', data)
export const updateVersion = (data) => request.put('/api/version', data)
export const publishVersion = (id) => request.put(`/api/version/publish/${id}`)
export const deleteVersion = (id) => request.delete(`/api/version/${id}`)

export const getCasePage = (params) => request.get('/api/case/page', { params })
export const addCase = (data) => request.post('/api/case', data)
export const updateCase = (data) => request.put('/api/case', data)
export const deleteCase = (id) => request.delete(`/api/case/${id}`)

export const getModelPage = (params) => request.get('/api/model/page', { params })
export const addModel = (data) => request.post('/api/model', data)
export const updateModel = (data) => request.put('/api/model', data)
export const deleteModel = (id) => request.delete(`/api/model/${id}`)

export const getRulePage = (params) => request.get('/api/rule/page', { params })
export const addRule = (data) => request.post('/api/rule', data)
export const updateRule = (data) => request.put('/api/rule', data)
export const deleteRule = (id) => request.delete(`/api/rule/${id}`)

export const getEvaluationPage = (params) => request.get('/api/evaluation/page', { params })
export const addEvaluation = (data) => request.post('/api/evaluation', data)
export const updateEvaluation = (data) => request.put('/api/evaluation', data)
export const runEvaluation = (id) => request.put(`/api/evaluation/run/${id}`)
export const finishEvaluation = (id) => request.put(`/api/evaluation/finish/${id}`)
export const cancelEvaluation = (id) => request.put(`/api/evaluation/cancel/${id}`)
export const deleteEvaluation = (id) => request.delete(`/api/evaluation/${id}`)

export const getResultPage = (params) => request.get('/api/result/page', { params })
export const reviewResult = (data) => request.put('/api/result/review', data)

export const getFeedbackPage = (params) => request.get('/api/feedback/page', { params })
export const addFeedback = (data) => request.post('/api/feedback', data)
export const updateFeedback = (data) => request.put('/api/feedback', data)
export const replyFeedback = (data) => request.put('/api/feedback/reply', data)

export const getDashboard = () => request.get('/api/statistics/dashboard')
export const getLogPage = (params) => request.get('/api/log/page', { params })
