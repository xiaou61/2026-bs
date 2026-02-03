import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const getUserInfo = () => request.get('/api/user/info')

export const getUserList = (params) => request.get('/api/user/list', { params })
export const saveUser = (data) => request.post('/api/user/save', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)
export const updateUserStatus = (id, status) => request.put(`/api/user/status/${id}`, null, { params: { status } })
export const resetPassword = (id) => request.put(`/api/user/resetPassword/${id}`)
export const getJudges = () => request.get('/api/user/judges')

export const getCategoryList = (params) => request.get('/api/category/list', { params })
export const getAllCategories = () => request.get('/api/category/all')
export const saveCategory = (data) => request.post('/api/category/save', data)
export const deleteCategory = (id) => request.delete(`/api/category/${id}`)

export const getCompetitionList = (params) => request.get('/api/competition/list', { params })
export const getCompetition = (id) => request.get(`/api/competition/${id}`)
export const saveCompetition = (data) => request.post('/api/competition/save', data)
export const deleteCompetition = (id) => request.delete(`/api/competition/${id}`)
export const updateCompetitionStatus = (id, status) => request.put(`/api/competition/status/${id}`, null, { params: { status } })
export const publishResult = (id) => request.put(`/api/competition/publish/${id}`)

export const getWorkList = (params) => request.get('/api/work/list', { params })
export const getWork = (id) => request.get(`/api/work/${id}`)
export const auditWork = (data) => request.put('/api/work/audit', data)

export const getScoreStandards = (competitionId) => request.get(`/api/score-standard/list/${competitionId}`)
export const saveScoreStandards = (data) => request.post('/api/score-standard/save', data)

export const getJudgeAssignments = (competitionId) => request.get(`/api/judge/list/${competitionId}`)
export const assignJudges = (data) => request.post('/api/judge/assign', data)

export const getPendingWorks = (params) => request.get('/api/score/pending', { params })
export const submitScore = (data) => request.post('/api/score/submit', data)
export const getScoreDetail = (workId) => request.get(`/api/score/detail/${workId}`)
export const getMyScore = (workId) => request.get(`/api/score/my/${workId}`)

export const getNoticeList = (params) => request.get('/api/notice/list', { params })
export const getNotice = (id) => request.get(`/api/notice/${id}`)
export const saveNotice = (data) => request.post('/api/notice/save', data)
export const deleteNotice = (id) => request.delete(`/api/notice/${id}`)

export const getAwardList = (competitionId) => request.get(`/api/award/list/${competitionId}`)
export const generateAwards = (competitionId, data) => request.post(`/api/award/generate/${competitionId}`, data)

export const getDashboard = () => request.get('/api/stats/dashboard')
export const getCompetitionStats = (id) => request.get(`/api/stats/competition/${id}`)
export const getScoreProgress = (competitionId) => request.get(`/api/stats/score-progress/${competitionId}`)

export const uploadFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/api/file/upload', formData)
}
