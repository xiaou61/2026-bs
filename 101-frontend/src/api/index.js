import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
export const logout = () => request.post('/api/auth/logout')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getCandidatePage = (params) => request.get('/api/candidate/page', { params })
export const addCandidate = (data) => request.post('/api/candidate', data)
export const updateCandidate = (data) => request.put('/api/candidate', data)
export const deleteCandidate = (id) => request.delete(`/api/candidate/${id}`)

export const getResumePage = (params) => request.get('/api/resume/page', { params })
export const addResume = (data) => request.post('/api/resume', data)
export const updateResume = (data) => request.put('/api/resume', data)
export const deleteResume = (id) => request.delete(`/api/resume/${id}`)

export const getCertificatePage = (params) => request.get('/api/certificate/page', { params })
export const addCertificate = (data) => request.post('/api/certificate', data)
export const updateCertificate = (data) => request.put('/api/certificate', data)
export const verifyCertificate = (id, comment) => request.put(`/api/certificate/verify/${id}`, { comment })
export const rejectCertificate = (id, comment) => request.put(`/api/certificate/reject/${id}`, { comment })
export const deleteCertificate = (id) => request.delete(`/api/certificate/${id}`)

export const getJobPage = (params) => request.get('/api/job/page', { params })
export const addJob = (data) => request.post('/api/job', data)
export const updateJob = (data) => request.put('/api/job', data)
export const publishJob = (id) => request.put(`/api/job/publish/${id}`)
export const closeJob = (id) => request.put(`/api/job/close/${id}`)
export const deleteJob = (id) => request.delete(`/api/job/${id}`)

export const getRequirementPage = (params) => request.get('/api/requirement/page', { params })
export const addRequirement = (data) => request.post('/api/requirement', data)
export const updateRequirement = (data) => request.put('/api/requirement', data)
export const deleteRequirement = (id) => request.delete(`/api/requirement/${id}`)

export const getParseTaskPage = (params) => request.get('/api/parse-task/page', { params })
export const addParseTask = (data) => request.post('/api/parse-task', data)
export const updateParseTask = (data) => request.put('/api/parse-task', data)
export const runParseTask = (id) => request.put(`/api/parse-task/run/${id}`)
export const finishParseTask = (id) => request.put(`/api/parse-task/finish/${id}`)
export const rejectParseTask = (id) => request.put(`/api/parse-task/reject/${id}`)
export const deleteParseTask = (id) => request.delete(`/api/parse-task/${id}`)

export const getParseResultPage = (params) => request.get('/api/parse-result/page', { params })
export const reviewParseResult = (data) => request.put('/api/parse-result/review', data)

export const getMatchTaskPage = (params) => request.get('/api/match-task/page', { params })
export const addMatchTask = (data) => request.post('/api/match-task', data)
export const updateMatchTask = (data) => request.put('/api/match-task', data)
export const runMatchTask = (id) => request.put(`/api/match-task/run/${id}`)
export const finishMatchTask = (id) => request.put(`/api/match-task/finish/${id}`)
export const rejectMatchTask = (id) => request.put(`/api/match-task/reject/${id}`)
export const deleteMatchTask = (id) => request.delete(`/api/match-task/${id}`)

export const getMatchResultPage = (params) => request.get('/api/match-result/page', { params })
export const reviewMatchResult = (data) => request.put('/api/match-result/review', data)

export const getInterviewPage = (params) => request.get('/api/interview/page', { params })
export const addInterview = (data) => request.post('/api/interview', data)
export const updateInterview = (data) => request.put('/api/interview', data)
export const confirmInterview = (id) => request.put(`/api/interview/confirm/${id}`)
export const cancelInterview = (id) => request.put(`/api/interview/cancel/${id}`)
export const finishInterview = (id) => request.put(`/api/interview/finish/${id}`)
export const deleteInterview = (id) => request.delete(`/api/interview/${id}`)

export const getFeedbackPage = (params) => request.get('/api/feedback/page', { params })
export const addFeedback = (data) => request.post('/api/feedback', data)
export const updateFeedback = (data) => request.put('/api/feedback', data)
export const deleteFeedback = (id) => request.delete(`/api/feedback/${id}`)

export const getDashboard = () => request.get('/api/statistics/dashboard')
export const getLogPage = (params) => request.get('/api/log/page', { params })
