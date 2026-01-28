import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

api.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 200) {
      return res
    } else {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(res)
    }
  },
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      router.push('/login')
    }
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export const login = data => api.post('/user/login', data)
export const getUserInfo = () => api.get('/user/info')
export const updatePassword = data => api.put('/user/password', data)
export const updateProfile = data => api.put('/user/profile', data)

export const getUserList = params => api.get('/user/list', { params })
export const getAllUsers = () => api.get('/user/all')
export const addUser = data => api.post('/user', data)
export const updateUser = data => api.put('/user', data)
export const deleteUser = id => api.delete(`/user/${id}`)

export const getDeptList = () => api.get('/department/list')
export const getDeptTree = () => api.get('/department/tree')
export const addDept = data => api.post('/department', data)
export const updateDept = data => api.put('/department', data)
export const deleteDept = id => api.delete(`/department/${id}`)

export const getAttendanceList = params => api.get('/attendance/list', { params })
export const getMyAttendance = params => api.get('/attendance/my', { params })
export const clockIn = () => api.post('/attendance/clock-in')
export const clockOut = () => api.post('/attendance/clock-out')
export const getTodayAttendance = () => api.get('/attendance/today')

export const getLeaveList = params => api.get('/leave/list', { params })
export const getMyLeaves = params => api.get('/leave/my', { params })
export const addLeave = data => api.post('/leave', data)
export const approveLeave = data => api.put('/leave/approve', data)
export const deleteLeave = id => api.delete(`/leave/${id}`)

export const getMeetingRoomList = params => api.get('/meeting-room/list', { params })
export const getAllMeetingRooms = () => api.get('/meeting-room/all')
export const addMeetingRoom = data => api.post('/meeting-room', data)
export const updateMeetingRoom = data => api.put('/meeting-room', data)
export const deleteMeetingRoom = id => api.delete(`/meeting-room/${id}`)

export const getMeetingList = params => api.get('/meeting/list', { params })
export const getMyMeetings = params => api.get('/meeting/my', { params })
export const addMeeting = data => api.post('/meeting', data)
export const updateMeeting = data => api.put('/meeting', data)
export const deleteMeeting = id => api.delete(`/meeting/${id}`)

export const getNoticeList = params => api.get('/notice/list', { params })
export const getPublishedNotices = params => api.get('/notice/published', { params })
export const addNotice = data => api.post('/notice', data)
export const updateNotice = data => api.put('/notice', data)
export const deleteNotice = id => api.delete(`/notice/${id}`)
export const publishNotice = id => api.put(`/notice/publish/${id}`)
export const readNotice = id => api.post(`/notice/read/${id}`)

export const getScheduleList = params => api.get('/schedule/list', { params })
export const addSchedule = data => api.post('/schedule', data)
export const updateSchedule = data => api.put('/schedule', data)
export const deleteSchedule = id => api.delete(`/schedule/${id}`)

export const getDocumentList = params => api.get('/document/list', { params })
export const uploadDocument = data => api.post('/document/upload', data)
export const updateDocument = data => api.put('/document', data)
export const deleteDocument = id => api.delete(`/document/${id}`)
export const downloadDocument = id => api.get(`/document/download/${id}`, { responseType: 'blob' })

export const getSalaryList = params => api.get('/salary/list', { params })
export const getMySalary = params => api.get('/salary/my', { params })
export const addSalary = data => api.post('/salary', data)
export const updateSalary = data => api.put('/salary', data)
export const deleteSalary = id => api.delete(`/salary/${id}`)

export const getWorkLogList = params => api.get('/work-log/list', { params })
export const getMyWorkLogs = params => api.get('/work-log/my', { params })
export const addWorkLog = data => api.post('/work-log', data)
export const updateWorkLog = data => api.put('/work-log', data)
export const deleteWorkLog = id => api.delete(`/work-log/${id}`)

export const getStatistics = () => api.get('/statistics')

export default api
