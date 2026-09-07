import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const logout = () => request.post('/api/auth/logout')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getCustomerProfilePage = (params) => request.get('/api/customer/page', { params })
export const addCustomerProfile = (data) => request.post('/api/customer', data)
export const updateCustomerProfile = (data) => request.put('/api/customer', data)
export const deleteCustomerProfile = (id) => request.delete(`/api/customer/${id}`)

export const getServiceChannelPage = (params) => request.get('/api/channel/page', { params })
export const addServiceChannel = (data) => request.post('/api/channel', data)
export const updateServiceChannel = (data) => request.put('/api/channel', data)
export const deleteServiceChannel = (id) => request.delete(`/api/channel/${id}`)

export const getKnowledgeCategoryPage = (params) => request.get('/api/category/page', { params })
export const addKnowledgeCategory = (data) => request.post('/api/category', data)
export const updateKnowledgeCategory = (data) => request.put('/api/category', data)
export const deleteKnowledgeCategory = (id) => request.delete(`/api/category/${id}`)

export const getKnowledgeArticlePage = (params) => request.get('/api/article/page', { params })
export const addKnowledgeArticle = (data) => request.post('/api/article', data)
export const updateKnowledgeArticle = (data) => request.put('/api/article', data)
export const deleteKnowledgeArticle = (id) => request.delete(`/api/article/${id}`)

export const getWorkOrderPage = (params) => request.get('/api/order/page', { params })
export const addWorkOrder = (data) => request.post('/api/order', data)
export const updateWorkOrder = (data) => request.put('/api/order', data)
export const deleteWorkOrder = (id) => request.delete(`/api/order/${id}`)

export const getOrderMessagePage = (params) => request.get('/api/message/page', { params })
export const addOrderMessage = (data) => request.post('/api/message', data)
export const updateOrderMessage = (data) => request.put('/api/message', data)
export const deleteOrderMessage = (id) => request.delete(`/api/message/${id}`)

export const getTicketAssignmentPage = (params) => request.get('/api/assignment/page', { params })
export const addTicketAssignment = (data) => request.post('/api/assignment', data)
export const updateTicketAssignment = (data) => request.put('/api/assignment', data)
export const deleteTicketAssignment = (id) => request.delete(`/api/assignment/${id}`)

export const getQualityRulePage = (params) => request.get('/api/rule/page', { params })
export const addQualityRule = (data) => request.post('/api/rule', data)
export const updateQualityRule = (data) => request.put('/api/rule', data)
export const deleteQualityRule = (id) => request.delete(`/api/rule/${id}`)

export const getQualityTaskPage = (params) => request.get('/api/quality-task/page', { params })
export const addQualityTask = (data) => request.post('/api/quality-task', data)
export const updateQualityTask = (data) => request.put('/api/quality-task', data)
export const deleteQualityTask = (id) => request.delete(`/api/quality-task/${id}`)

export const getQualityResultPage = (params) => request.get('/api/quality-result/page', { params })
export const addQualityResult = (data) => request.post('/api/quality-result', data)
export const updateQualityResult = (data) => request.put('/api/quality-result', data)
export const deleteQualityResult = (id) => request.delete(`/api/quality-result/${id}`)

export const getRecommendRecordPage = (params) => request.get('/api/recommend/page', { params })
export const addRecommendRecord = (data) => request.post('/api/recommend', data)
export const updateRecommendRecord = (data) => request.put('/api/recommend', data)
export const deleteRecommendRecord = (id) => request.delete(`/api/recommend/${id}`)

export const getAgentPerformancePage = (params) => request.get('/api/performance/page', { params })
export const addAgentPerformance = (data) => request.post('/api/performance', data)
export const updateAgentPerformance = (data) => request.put('/api/performance', data)
export const deleteAgentPerformance = (id) => request.delete(`/api/performance/${id}`)

export const publishArticle = (id) => request.put(`/api/article/publish/${id}`)
export const offlineArticle = (id) => request.put(`/api/article/offline/${id}`)
export const acceptOrder = (id) => request.put(`/api/order/accept/${id}`)
export const resolveOrder = (id) => request.put(`/api/order/resolve/${id}`)
export const closeOrder = (id) => request.put(`/api/order/close/${id}`)
export const runQualityTask = (id) => request.put(`/api/quality-task/run/${id}`)
export const finishQualityTask = (id) => request.put(`/api/quality-task/finish/${id}`)
export const rejectQualityTask = (id) => request.put(`/api/quality-task/reject/${id}`)
export const reviewQualityResult = (data) => request.put('/api/quality-result/review', data)
export const adoptRecommend = (id) => request.put(`/api/recommend/adopt/${id}`)
export const rejectRecommend = (id) => request.put(`/api/recommend/reject/${id}`)
export const getDashboard = () => request.get('/api/statistics/dashboard')
export const getLogPage = (params) => request.get('/api/log/page', { params })
