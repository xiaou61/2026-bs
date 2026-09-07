import request from './request'

export const login = (data) => request.post('/auth/login', data)
export const logout = () => request.post('/auth/logout')
export const getUserInfo = () => request.get('/auth/info')

export const getCategoryList = (params) => request.get('/category/list', { params })
export const getCategoryEnabled = () => request.get('/category/enabled')
export const addCategory = (data) => request.post('/category/add', data)
export const updateCategory = (data) => request.put('/category/update', data)
export const deleteCategory = (id) => request.delete(`/category/delete/${id}`)

export const getCourseList = (params) => request.get('/course/list', { params })
export const getCourseEnabled = () => request.get('/course/enabled')
export const getCourseDetail = (id) => request.get(`/course/detail/${id}`)
export const addCourse = (data) => request.post('/course/add', data)
export const updateCourse = (data) => request.put('/course/update', data)
export const deleteCourse = (id) => request.delete(`/course/delete/${id}`)

export const getIndicatorList = (params) => request.get('/indicator/list', { params })
export const getIndicatorEnabled = () => request.get('/indicator/enabled')
export const addIndicator = (data) => request.post('/indicator/add', data)
export const updateIndicator = (data) => request.put('/indicator/update', data)
export const deleteIndicator = (id) => request.delete(`/indicator/delete/${id}`)

export const getTaskList = (params) => request.get('/task/list', { params })
export const getActiveTaskList = () => request.get('/task/active')
export const addTask = (data) => request.post('/task/add', data)
export const updateTask = (data) => request.put('/task/update', data)
export const updateTaskStatus = (id, status) => request.put('/task/status', null, { params: { id, status } })
export const deleteTask = (id) => request.delete(`/task/delete/${id}`)

export const getMyEvaluationList = (params) => request.get('/evaluation/my/list', { params })
export const getEvaluationDetail = (recordId) => request.get(`/evaluation/detail/${recordId}`)
export const submitEvaluation = (data) => request.post('/evaluation/submit', data)
export const getTaskEvaluationRecords = (params) => request.get('/evaluation/task/records', { params })
export const getTaskEvaluationSummary = (params) => request.get('/evaluation/task/summary', { params })

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getDashboard = () => request.get('/statistics/dashboard')
