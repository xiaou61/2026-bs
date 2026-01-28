import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/UserList.vue') },
      { path: 'department', component: () => import('../views/department/DepartmentList.vue') },
      { path: 'attendance', component: () => import('../views/attendance/AttendanceList.vue') },
      { path: 'leave', component: () => import('../views/leave/LeaveList.vue') },
      { path: 'meeting-room', component: () => import('../views/meeting/MeetingRoomList.vue') },
      { path: 'meeting', component: () => import('../views/meeting/MeetingList.vue') },
      { path: 'notice', component: () => import('../views/notice/NoticeList.vue') },
      { path: 'schedule', component: () => import('../views/schedule/ScheduleList.vue') },
      { path: 'document', component: () => import('../views/document/DocumentList.vue') },
      { path: 'salary', component: () => import('../views/salary/SalaryList.vue') },
      { path: 'work-log', component: () => import('../views/worklog/WorkLogList.vue') },
      { path: 'profile', component: () => import('../views/Profile.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
