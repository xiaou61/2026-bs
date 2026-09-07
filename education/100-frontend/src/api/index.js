import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const getUserInfo = () => request.get('/api/auth/info')
export const logout = () => request.post('/api/auth/logout')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getCoursePage = (params) => request.get('/api/course/page', { params })
export const addCourse = (data) => request.post('/api/course', data)
export const updateCourse = (data) => request.put('/api/course', data)
export const deleteCourse = (id) => request.delete(`/api/course/${id}`)

export const getClassPage = (params) => request.get('/api/class/page', { params })
export const addClass = (data) => request.post('/api/class', data)
export const updateClass = (data) => request.put('/api/class', data)
export const deleteClass = (id) => request.delete(`/api/class/${id}`)

export const getStudentPage = (params) => request.get('/api/student/page', { params })
export const addStudent = (data) => request.post('/api/student', data)
export const updateStudent = (data) => request.put('/api/student', data)
export const deleteStudent = (id) => request.delete(`/api/student/${id}`)

export const getAssignmentPage = (params) => request.get('/api/assignment/page', { params })
export const addAssignment = (data) => request.post('/api/assignment', data)
export const updateAssignment = (data) => request.put('/api/assignment', data)
export const deleteAssignment = (id) => request.delete(`/api/assignment/${id}`)

export const getSubmissionPage = (params) => request.get('/api/submission/page', { params })
export const addSubmission = (data) => request.post('/api/submission', data)
export const updateSubmission = (data) => request.put('/api/submission', data)
export const deleteSubmission = (id) => request.delete(`/api/submission/${id}`)

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

export const getWarningPage = (params) => request.get('/api/warning/page', { params })
export const handleWarning = (data) => request.put('/api/warning/handle', data)
export const deleteWarning = (id) => request.delete(`/api/warning/${id}`)

export const getRectificationPage = (params) => request.get('/api/rectification/page', { params })
export const addRectification = (data) => request.post('/api/rectification', data)
export const updateRectification = (data) => request.put('/api/rectification', data)
export const reviewRectification = (data) => request.put('/api/rectification/review', data)
export const deleteRectification = (id) => request.delete(`/api/rectification/${id}`)

export const getAppealPage = (params) => request.get('/api/appeal/page', { params })
export const addAppeal = (data) => request.post('/api/appeal', data)
export const updateAppeal = (data) => request.put('/api/appeal', data)
export const handleAppeal = (data) => request.put('/api/appeal/handle', data)
export const deleteAppeal = (id) => request.delete(`/api/appeal/${id}`)

export const getDashboard = () => request.get('/api/statistics/dashboard')
export const getLogPage = (params) => request.get('/api/log/page', { params })
