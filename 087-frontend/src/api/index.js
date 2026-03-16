import request from './request'

export const login = (data) => request.post('/auth/login', data)
export const logout = () => request.post('/auth/logout')
export const getUserInfo = () => request.get('/auth/info')
export const getUserOptions = (role) => request.get('/user/options', { params: { role } })

export const getDepartmentList = (params) => request.get('/department/list', { params })
export const getDepartmentEnabled = () => request.get('/department/enabled')
export const addDepartment = (data) => request.post('/department/add', data)
export const updateDepartment = (data) => request.put('/department/update', data)
export const deleteDepartment = (id) => request.delete(`/department/delete/${id}`)

export const getMajorList = (params) => request.get('/major/list', { params })
export const getMajorEnabled = () => request.get('/major/enabled')
export const addMajor = (data) => request.post('/major/add', data)
export const updateMajor = (data) => request.put('/major/update', data)
export const deleteMajor = (id) => request.delete(`/major/delete/${id}`)

export const getClassList = (params) => request.get('/class/list', { params })
export const getClassEnabled = () => request.get('/class/enabled')
export const addClass = (data) => request.post('/class/add', data)
export const updateClass = (data) => request.put('/class/update', data)
export const deleteClass = (id) => request.delete(`/class/delete/${id}`)

export const getTermList = (params) => request.get('/term/list', { params })
export const getTermEnabled = () => request.get('/term/enabled')
export const addTerm = (data) => request.post('/term/add', data)
export const updateTerm = (data) => request.put('/term/update', data)
export const deleteTerm = (id) => request.delete(`/term/delete/${id}`)

export const getCourseList = (params) => request.get('/course/list', { params })
export const getCourseEnabled = () => request.get('/course/enabled')
export const addCourse = (data) => request.post('/course/add', data)
export const updateCourse = (data) => request.put('/course/update', data)
export const deleteCourse = (id) => request.delete(`/course/delete/${id}`)

export const getScheduleList = (params) => request.get('/schedule/list', { params })
export const getTeacherSchedule = () => request.get('/schedule/teacher')
export const getStudentSchedule = () => request.get('/schedule/student')
export const addSchedule = (data) => request.post('/schedule/add', data)
export const updateSchedule = (data) => request.put('/schedule/update', data)
export const deleteSchedule = (id) => request.delete(`/schedule/delete/${id}`)

export const getSelectionList = (params) => request.get('/selection/list', { params })
export const selectCourse = (data) => request.post('/selection/select', data)

export const getResourceList = (params) => request.get('/resource/list', { params })
export const addResource = (data) => request.post('/resource/add', data)
export const updateResource = (data) => request.put('/resource/update', data)
export const deleteResource = (id) => request.delete(`/resource/delete/${id}`)

export const getAttendanceList = (params) => request.get('/attendance/list', { params })
export const addAttendance = (data) => request.post('/attendance/add', data)
export const updateAttendance = (data) => request.put('/attendance/update', data)

export const getScoreList = (params) => request.get('/score/list', { params })
export const saveScore = (data) => request.post('/score/save', data)

export const getEvaluationList = (params) => request.get('/evaluation/list', { params })
export const addEvaluation = (data) => request.post('/evaluation/add', data)

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const getPublicNotices = () => request.get('/notice/public/list')
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getDashboard = () => request.get('/statistics/dashboard')
