import request from '../utils/request'

export const wxLogin = (data) => request({ url: '/api/auth/wxLogin', method: 'POST', data })
export const register = (data) => request({ url: '/api/auth/register', method: 'POST', data })
export const login = (data) => request({ url: '/api/auth/login', method: 'POST', data })
export const getUserInfo = () => request({ url: '/api/user/info' })
export const updateUserInfo = (data) => request({ url: '/api/user/updateInfo', method: 'PUT', data })

export const getCategories = () => request({ url: '/api/category/all' })

export const getCompetitionList = (categoryId) => request({ url: '/api/competition/public/list', data: { categoryId } })
export const getCompetition = (id) => request({ url: `/api/competition/public/${id}` })

export const submitWork = (data) => request({ url: '/api/work/submit', method: 'POST', data })
export const updateWork = (data) => request({ url: '/api/work/update', method: 'PUT', data })
export const getMyWorks = (params) => request({ url: '/api/work/my', data: params })
export const getWork = (id) => request({ url: `/api/work/${id}` })
export const withdrawWork = (id) => request({ url: `/api/work/withdraw/${id}`, method: 'PUT' })

export const getNoticeList = (params) => request({ url: '/api/notice/public/list', data: params })
export const getNotice = (id) => request({ url: `/api/notice/public/${id}` })

export const getAwardList = (competitionId) => request({ url: `/api/award/list/${competitionId}` })
export const getMyAwards = () => request({ url: '/api/award/my' })
