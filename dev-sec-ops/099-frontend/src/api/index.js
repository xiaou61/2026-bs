import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const getUserInfo = () => request.get('/api/auth/info')
export const logout = () => request.post('/api/auth/logout')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getAssetPage = (params) => request.get('/api/asset/page', { params })
export const addAsset = (data) => request.post('/api/asset', data)
export const updateAsset = (data) => request.put('/api/asset', data)
export const deleteAsset = (id) => request.delete(`/api/asset/${id}`)

export const getRulePage = (params) => request.get('/api/rule/page', { params })
export const addRule = (data) => request.post('/api/rule', data)
export const updateRule = (data) => request.put('/api/rule', data)
export const deleteRule = (id) => request.delete(`/api/rule/${id}`)

export const getTaskPage = (params) => request.get('/api/task/page', { params })
export const addTask = (data) => request.post('/api/task', data)
export const updateTask = (data) => request.put('/api/task', data)
export const runTask = (id) => request.put(`/api/task/run/${id}`)
export const finishTask = (id) => request.put(`/api/task/finish/${id}`)
export const rejectTask = (id) => request.put(`/api/task/reject/${id}`)
export const deleteTask = (id) => request.delete(`/api/task/${id}`)

export const getResultPage = (params) => request.get('/api/result/page', { params })
export const reviewResult = (data) => request.put('/api/result/review', data)

export const getTagPage = (params) => request.get('/api/tag/page', { params })
export const addTag = (data) => request.post('/api/tag', data)
export const updateTag = (data) => request.put('/api/tag', data)
export const deleteTag = (id) => request.delete(`/api/tag/${id}`)

export const getRegisterPage = (params) => request.get('/api/register/page', { params })
export const addRegister = (data) => request.post('/api/register', data)
export const updateRegister = (data) => request.put('/api/register', data)
export const approveRegister = (id, comment) => request.put(`/api/register/approve/${id}`, { comment })
export const rejectRegister = (id, comment) => request.put(`/api/register/reject/${id}`, { comment })
export const deleteRegister = (id) => request.delete(`/api/register/${id}`)

export const getEvidencePage = (params) => request.get('/api/evidence/page', { params })
export const generateEvidence = (registerId) => request.post(`/api/evidence/generate/${registerId}`)
export const addEvidence = (data) => request.post('/api/evidence', data)
export const updateEvidence = (data) => request.put('/api/evidence', data)
export const deleteEvidence = (id) => request.delete(`/api/evidence/${id}`)

export const getLicensePage = (params) => request.get('/api/license/page', { params })
export const addLicense = (data) => request.post('/api/license', data)
export const updateLicense = (data) => request.put('/api/license', data)
export const revokeLicense = (id) => request.put(`/api/license/revoke/${id}`)
export const deleteLicense = (id) => request.delete(`/api/license/${id}`)

export const getCluePage = (params) => request.get('/api/clue/page', { params })
export const addClue = (data) => request.post('/api/clue', data)
export const updateClue = (data) => request.put('/api/clue', data)
export const handleClue = (data) => request.put('/api/clue/handle', data)
export const deleteClue = (id) => request.delete(`/api/clue/${id}`)

export const getAppealPage = (params) => request.get('/api/appeal/page', { params })
export const addAppeal = (data) => request.post('/api/appeal', data)
export const updateAppeal = (data) => request.put('/api/appeal', data)
export const handleAppeal = (data) => request.put('/api/appeal/handle', data)
export const deleteAppeal = (id) => request.delete(`/api/appeal/${id}`)

export const getDashboard = () => request.get('/api/statistics/dashboard')
export const getLogPage = (params) => request.get('/api/log/page', { params })
