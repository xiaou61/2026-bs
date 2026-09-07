import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const register = (data) => request.post('/api/auth/register', data)
export const getUserInfo = () => request.get('/api/auth/info')
export const updatePassword = (data) => request.put('/api/auth/password', data)
export const logout = () => request.post('/api/auth/logout')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const updateUserStatus = (data) => request.put('/api/user/status', data)
export const updateProfile = (data) => request.put('/api/user/profile', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getCategoryList = () => request.get('/api/category/list')
export const getCategoryPage = (params) => request.get('/api/category/page', { params })
export const addCategory = (data) => request.post('/api/category', data)
export const updateCategory = (data) => request.put('/api/category', data)
export const deleteCategory = (id) => request.delete(`/api/category/${id}`)

export const getIngredientList = () => request.get('/api/ingredient/list')
export const getIngredientPage = (params) => request.get('/api/ingredient/page', { params })
export const addIngredient = (data) => request.post('/api/ingredient', data)
export const updateIngredient = (data) => request.put('/api/ingredient', data)
export const deleteIngredient = (id) => request.delete(`/api/ingredient/${id}`)

export const getFormulaList = () => request.get('/api/formula/list')
export const getFormulaPage = (params) => request.get('/api/formula/page', { params })
export const addFormula = (data) => request.post('/api/formula', data)
export const updateFormula = (data) => request.put('/api/formula', data)
export const deleteFormula = (id) => request.delete(`/api/formula/${id}`)

export const getPlanList = () => request.get('/api/plan/list')
export const getPlanPage = (params) => request.get('/api/plan/page', { params })
export const addPlan = (data) => request.post('/api/plan', data)
export const updatePlan = (data) => request.put('/api/plan', data)
export const deletePlan = (id) => request.delete(`/api/plan/${id}`)

export const getMyServicePage = (params) => request.get('/api/service/my-page', { params })
export const getServicePage = (params) => request.get('/api/service/page', { params })
export const addServiceOrder = (data) => request.post('/api/service', data)
export const updateServiceStatus = (data) => request.put('/api/service/status', data)
export const deleteServiceOrder = (id) => request.delete(`/api/service/${id}`)

export const getMyConstitutionPage = (params) => request.get('/api/constitution/my-page', { params })
export const getConstitutionPage = (params) => request.get('/api/constitution/page', { params })
export const addConstitution = (data) => request.post('/api/constitution', data)
export const replyConstitution = (data) => request.put('/api/constitution/reply', data)

export const getMyFavoritePage = (params) => request.get('/api/favorite/my-page', { params })
export const toggleFavorite = (data) => request.post('/api/favorite/toggle', data)

export const getAnnouncementList = () => request.get('/api/announcement/list')
export const getAnnouncementPage = (params) => request.get('/api/announcement/page', { params })
export const addAnnouncement = (data) => request.post('/api/announcement', data)
export const updateAnnouncement = (data) => request.put('/api/announcement', data)
export const deleteAnnouncement = (id) => request.delete(`/api/announcement/${id}`)

export const getDashboardStats = () => request.get('/api/dashboard/stats')
export const getDashboardOrderTrend = () => request.get('/api/dashboard/order-trend')
