import request from './request'

export const login = (data) => request.post('/auth/login', data)
export const logout = () => request.post('/auth/logout')
export const getUserInfo = () => request.get('/auth/info')
export const getUserOptions = (role) => request.get('/user/options', { params: { role } })

export const getDepartmentList = (params) => request.get('/category/list', { params })
export const getDepartmentEnabled = () => request.get('/category/enabled')
export const addDepartment = (data) => request.post('/category/add', data)
export const updateDepartment = (data) => request.put('/category/update', data)
export const deleteDepartment = (id) => request.delete(`/category/delete/${id}`)

export const getMajorList = (params) => request.get('/artist/list', { params })
export const getMajorEnabled = () => request.get('/artist/enabled')
export const addMajor = (data) => request.post('/artist/add', data)
export const updateMajor = (data) => request.put('/artist/update', data)
export const deleteMajor = (id) => request.delete(`/artist/delete/${id}`)

export const getClassList = (params) => request.get('/venue/list', { params })
export const getClassEnabled = () => request.get('/venue/enabled')
export const addClass = (data) => request.post('/venue/add', data)
export const updateClass = (data) => request.put('/venue/update', data)
export const deleteClass = (id) => request.delete(`/venue/delete/${id}`)

export const getTermList = (params) => request.get('/season/list', { params })
export const getTermEnabled = () => request.get('/season/enabled')
export const addTerm = (data) => request.post('/season/add', data)
export const updateTerm = (data) => request.put('/season/update', data)
export const deleteTerm = (id) => request.delete(`/season/delete/${id}`)

export const getCourseList = (params) => request.get('/repertoire/list', { params })
export const getCourseEnabled = () => request.get('/repertoire/enabled')
export const addCourse = (data) => request.post('/repertoire/add', data)
export const updateCourse = (data) => request.put('/repertoire/update', data)
export const deleteCourse = (id) => request.delete(`/repertoire/delete/${id}`)

export const getScheduleList = (params) => request.get('/performance/list', { params })
export const getArtistSchedule = () => request.get('/performance/artist')
export const getMemberSchedule = () => request.get('/performance/member')
export const addSchedule = (data) => request.post('/performance/add', data)
export const updateSchedule = (data) => request.put('/performance/update', data)
export const deleteSchedule = (id) => request.delete(`/performance/delete/${id}`)

export const getSelectionList = (params) => request.get('/booking/list', { params })
export const selectCourse = (data) => request.post('/booking/select', data)

export const getResourceList = (params) => request.get('/resource/list', { params })
export const addResource = (data) => request.post('/resource/add', data)
export const updateResource = (data) => request.put('/resource/update', data)
export const deleteResource = (id) => request.delete(`/resource/delete/${id}`)

export const getAttendanceList = (params) => request.get('/checkin/list', { params })
export const addAttendance = (data) => request.post('/checkin/add', data)
export const updateAttendance = (data) => request.put('/checkin/update', data)

export const getScoreList = (params) => request.get('/review/list', { params })
export const saveScore = (data) => request.post('/review/save', data)

export const getEvaluationList = (params) => request.get('/comment/list', { params })
export const addEvaluation = (data) => request.post('/comment/add', data)

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const getPublicNotices = () => request.get('/notice/public/list')
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getDashboard = () => request.get('/statistics/dashboard')


