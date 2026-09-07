import request from './request'

export const login = (data) => request.post('/auth/login', data)
export const logout = () => request.post('/auth/logout')
export const getUserInfo = () => request.get('/auth/info')
export const getUserOptions = (role) => request.get('/user/options', { params: { role } })

export const getCampusList = (params) => request.get('/campus/list', { params })
export const getCampusEnabled = () => request.get('/campus/enabled')
export const addCampus = (data) => request.post('/campus/add', data)
export const updateCampus = (data) => request.put('/campus/update', data)
export const deleteCampus = (id) => request.delete(`/campus/delete/${id}`)

export const getGradeList = (params) => request.get('/grade/list', { params })
export const getGradeEnabled = () => request.get('/grade/enabled')
export const addGrade = (data) => request.post('/grade/add', data)
export const updateGrade = (data) => request.put('/grade/update', data)
export const deleteGrade = (id) => request.delete(`/grade/delete/${id}`)

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

export const getActivityList = (params) => request.get('/activity/list', { params })
export const getActivityEnabled = () => request.get('/activity/enabled')
export const addActivity = (data) => request.post('/activity/add', data)
export const updateActivity = (data) => request.put('/activity/update', data)
export const deleteActivity = (id) => request.delete(`/activity/delete/${id}`)

export const getScheduleList = (params) => request.get('/schedule/list', { params })
export const getTeacherSchedule = () => request.get('/schedule/teacher')
export const getParentSchedule = () => request.get('/schedule/parent')
export const addSchedule = (data) => request.post('/schedule/add', data)
export const updateSchedule = (data) => request.put('/schedule/update', data)
export const deleteSchedule = (id) => request.delete(`/schedule/delete/${id}`)

export const getChildList = (params) => request.get('/child/list', { params })
export const getMyChildren = () => request.get('/child/mine')
export const addChild = (data) => request.post('/child/add', data)
export const updateChild = (data) => request.put('/child/update', data)
export const deleteChild = (id) => request.delete(`/child/delete/${id}`)

export const getRecipeList = (params) => request.get('/recipe/list', { params })
export const addRecipe = (data) => request.post('/recipe/add', data)
export const updateRecipe = (data) => request.put('/recipe/update', data)
export const deleteRecipe = (id) => request.delete(`/recipe/delete/${id}`)

export const getAttendanceList = (params) => request.get('/attendance/list', { params })
export const addAttendance = (data) => request.post('/attendance/add', data)
export const updateAttendance = (data) => request.put('/attendance/update', data)

export const getHealthList = (params) => request.get('/health/list', { params })
export const saveHealth = (data) => request.post('/health/save', data)

export const getFeedbackList = (params) => request.get('/feedback/list', { params })
export const addFeedback = (data) => request.post('/feedback/add', data)
export const replyFeedback = (data) => request.put('/feedback/reply', data)

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const getPublicNotices = () => request.get('/notice/public/list')
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getDashboard = () => request.get('/statistics/dashboard')

export const getDepartmentList = getCampusList
export const getDepartmentEnabled = getCampusEnabled
export const addDepartment = addCampus
export const updateDepartment = updateCampus
export const deleteDepartment = deleteCampus
export const getMajorList = getGradeList
export const getMajorEnabled = getGradeEnabled
export const addMajor = addGrade
export const updateMajor = updateGrade
export const deleteMajor = deleteGrade
export const getCourseList = getActivityList
export const getCourseEnabled = getActivityEnabled
export const addCourse = addActivity
export const updateCourse = updateActivity
export const deleteCourse = deleteActivity
export const getSelectionList = getChildList
export const getStudentSchedule = getParentSchedule
export const getResourceList = getRecipeList
export const addResource = addRecipe
export const updateResource = updateRecipe
export const deleteResource = deleteRecipe
export const getScoreList = getHealthList
export const saveScore = saveHealth
export const getEvaluationList = getFeedbackList
export const addEvaluation = addFeedback
