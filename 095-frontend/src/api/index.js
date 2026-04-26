import request from './request'

export const login = data => request.post('/auth/login', data)
export const register = data => request.post('/auth/register', data)
export const getInfo = () => request.get('/auth/info')
export const updatePassword = data => request.put('/auth/password', data)
export const logout = () => request.post('/auth/logout')

export const getUserList = params => request.get('/user/list', { params })
export const getUserDetail = id => request.get(`/user/${id}`)
export const saveUser = data => request.post('/user/save', data)
export const updateUserStatus = (id, status) => request.put(`/user/status/${id}/${status}`)
export const deleteUser = id => request.delete(`/user/${id}`)
export const updateProfile = data => request.put('/user/profile', data)

export const getLeagueList = params => request.get('/league/list', { params })
export const getLeagueOptions = () => request.get('/league/all')
export const saveLeague = data => request.post('/league/save', data)
export const deleteLeague = id => request.delete(`/league/${id}`)

export const getSeasonList = params => request.get('/season/list', { params })
export const getSeasonOptions = params => request.get('/season/all', { params })
export const saveSeason = data => request.post('/season/save', data)
export const deleteSeason = id => request.delete(`/season/${id}`)

export const getClubList = params => request.get('/club/list', { params })
export const getClubOptions = () => request.get('/club/all')
export const getClubPublicList = () => request.get('/club/public/list')
export const saveClub = data => request.post('/club/save', data)
export const deleteClub = id => request.delete(`/club/${id}`)

export const getTeamList = params => request.get('/team/list', { params })
export const getTeamOptions = params => request.get('/team/all', { params })
export const getTeamPublicList = params => request.get('/team/public/list', { params })
export const saveTeam = data => request.post('/team/save', data)
export const deleteTeam = id => request.delete(`/team/${id}`)

export const getCoachList = params => request.get('/coach/list', { params })
export const saveCoach = data => request.post('/coach/save', data)
export const deleteCoach = id => request.delete(`/coach/${id}`)

export const getPlayerList = params => request.get('/player/list', { params })
export const getPlayerPublicList = () => request.get('/player/public/list')
export const savePlayer = data => request.post('/player/save', data)
export const deletePlayer = id => request.delete(`/player/${id}`)

export const getVenueList = params => request.get('/venue/list', { params })
export const getVenueOptions = () => request.get('/venue/all')
export const saveVenue = data => request.post('/venue/save', data)
export const deleteVenue = id => request.delete(`/venue/${id}`)

export const getMatchList = params => request.get('/match/list', { params })
export const getMatchPublicList = params => request.get('/match/public/list', { params })
export const saveMatch = data => request.post('/match/save', data)
export const updateMatchResult = (id, data) => request.put(`/match/result/${id}`, data)
export const deleteMatch = id => request.delete(`/match/${id}`)

export const getStandingList = params => request.get('/standing/list', { params })
export const refreshStanding = seasonId => request.put(`/standing/refresh/${seasonId}`)

export const getFollowList = params => request.get('/follow/list', { params })
export const toggleFollow = data => request.post('/follow/toggle', data)

export const getNewsList = params => request.get('/news/list', { params })
export const getPublicNewsList = () => request.get('/news/public/list')
export const saveNews = data => request.post('/news/save', data)
export const deleteNews = id => request.delete(`/news/${id}`)

export const getDashboardStats = () => request.get('/statistics/dashboard')
export const getMatchTrend = params => request.get('/statistics/match-trend', { params })
export const getStandingRank = params => request.get('/statistics/standing-rank', { params })
export const getGoalRank = () => request.get('/statistics/goal-rank')
