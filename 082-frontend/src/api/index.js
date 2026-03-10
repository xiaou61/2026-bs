import request from './request'

export const login = (data) => request.post('/user/login', data)
export const register = (data) => request.post('/user/register', data)
export const getUserInfo = () => request.get('/user/info')
export const getUserList = (params) => request.get('/user/list', { params })
export const addUser = (data) => request.post('/user/add', data)
export const updateUser = (data) => request.put('/user/update', data)
export const deleteUser = (id) => request.delete(`/user/delete/${id}`)

export const getSubjectList = (params) => request.get('/subject/list', { params })
export const getSubjectPublicList = () => request.get('/subject/public/list')
export const addSubject = (data) => request.post('/subject/add', data)
export const updateSubject = (data) => request.put('/subject/update', data)
export const deleteSubject = (id) => request.delete(`/subject/delete/${id}`)

export const getCourseList = (params) => request.get('/course/list', { params })
export const getCoursePublicList = (params) => request.get('/course/public/list', { params })
export const addCourse = (data) => request.post('/course/add', data)
export const updateCourse = (data) => request.put('/course/update', data)
export const deleteCourse = (id) => request.delete(`/course/delete/${id}`)

export const getBankList = (params) => request.get('/bank/list', { params })
export const addBank = (data) => request.post('/bank/add', data)
export const updateBank = (data) => request.put('/bank/update', data)
export const deleteBank = (id) => request.delete(`/bank/delete/${id}`)

export const getQuestionList = (params) => request.get('/question/list', { params })
export const addQuestion = (data) => request.post('/question/add', data)
export const updateQuestion = (data) => request.put('/question/update', data)
export const deleteQuestion = (id) => request.delete(`/question/delete/${id}`)

export const getPaperList = (params) => request.get('/paper/list', { params })
export const addPaper = (data) => request.post('/paper/add', data)
export const updatePaper = (data) => request.put('/paper/update', data)
export const publishPaper = (id, status) => request.put('/paper/publish', null, { params: { id, status } })
export const deletePaper = (id) => request.delete(`/paper/delete/${id}`)

export const getExamRecordList = (params) => request.get('/exam-record/list', { params })
export const addExamRecord = (data) => request.post('/exam-record/add', data)
export const updateExamRecord = (data) => request.put('/exam-record/update', data)
export const deleteExamRecord = (id) => request.delete(`/exam-record/delete/${id}`)

export const getPlanList = (params) => request.get('/plan/list', { params })
export const addPlan = (data) => request.post('/plan/add', data)
export const updatePlan = (data) => request.put('/plan/update', data)
export const deletePlan = (id) => request.delete(`/plan/delete/${id}`)

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const getNoticePublicList = () => request.get('/notice/public/list')
export const getNoticePublicDetail = (id) => request.get(`/notice/public/detail/${id}`)
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getDashboard = () => request.get('/statistics/dashboard')
