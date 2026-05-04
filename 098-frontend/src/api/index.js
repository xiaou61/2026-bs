import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const getUserInfo = () => request.get('/api/auth/info')
export const logout = () => request.post('/api/auth/logout')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getSpacePage = (params) => request.get('/api/space/page', { params })
export const addSpace = (data) => request.post('/api/space', data)
export const updateSpace = (data) => request.put('/api/space', data)
export const deleteSpace = (id) => request.delete(`/api/space/${id}`)

export const getCategoryPage = (params) => request.get('/api/category/page', { params })
export const addCategory = (data) => request.post('/api/category', data)
export const updateCategory = (data) => request.put('/api/category', data)
export const deleteCategory = (id) => request.delete(`/api/category/${id}`)

export const getDocumentPage = (params) => request.get('/api/document/page', { params })
export const addDocument = (data) => request.post('/api/document', data)
export const updateDocument = (data) => request.put('/api/document', data)
export const publishDocument = (id) => request.put(`/api/document/publish/${id}`)
export const archiveDocument = (id) => request.put(`/api/document/archive/${id}`)
export const deleteDocument = (id) => request.delete(`/api/document/${id}`)

export const getChunkPage = (params) => request.get('/api/chunk/page', { params })
export const addChunk = (data) => request.post('/api/chunk', data)
export const updateChunk = (data) => request.put('/api/chunk', data)
export const indexChunk = (id) => request.put(`/api/chunk/index/${id}`)
export const deleteChunk = (id) => request.delete(`/api/chunk/${id}`)

export const getGroupPage = (params) => request.get('/api/group/page', { params })
export const addGroup = (data) => request.post('/api/group', data)
export const updateGroup = (data) => request.put('/api/group', data)
export const deleteGroup = (id) => request.delete(`/api/group/${id}`)

export const getMemberPage = (params) => request.get('/api/member/page', { params })
export const addMember = (data) => request.post('/api/member', data)
export const updateMember = (data) => request.put('/api/member', data)
export const deleteMember = (id) => request.delete(`/api/member/${id}`)

export const getPermissionPage = (params) => request.get('/api/permission/page', { params })
export const addPermission = (data) => request.post('/api/permission', data)
export const updatePermission = (data) => request.put('/api/permission', data)
export const deletePermission = (id) => request.delete(`/api/permission/${id}`)

export const getSessionPage = (params) => request.get('/api/session/page', { params })
export const addSession = (data) => request.post('/api/session', data)
export const updateSession = (data) => request.put('/api/session', data)
export const closeSession = (id) => request.put(`/api/session/close/${id}`)
export const deleteSession = (id) => request.delete(`/api/session/${id}`)

export const getRecordPage = (params) => request.get('/api/record/page', { params })
export const askQuestion = (data) => request.post('/api/record/ask', data)
export const resolveRecord = (id, resolved) => request.put(`/api/record/resolve/${id}/${resolved}`)

export const getFeedbackPage = (params) => request.get('/api/feedback/page', { params })
export const addFeedback = (data) => request.post('/api/feedback', data)
export const updateFeedback = (data) => request.put('/api/feedback', data)
export const replyFeedback = (data) => request.put('/api/feedback/reply', data)

export const getDashboard = () => request.get('/api/statistics/dashboard')
export const getLogPage = (params) => request.get('/api/log/page', { params })
