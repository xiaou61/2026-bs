import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  ACADEMIC: '/teaching',
  TEACHER: '/attendance',
  INSPECTOR: '/inspection',
  COUNSELOR: '/appeal',
  STUDENT: '/appeal'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'INSPECTOR', 'COUNSELOR', 'STUDENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'teaching', component: () => import('../views/TeachingClass.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR'] } },
      { path: 'student', component: () => import('../views/StudentProfile.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'COUNSELOR', 'TEACHER', 'STUDENT'] } },
      { path: 'teacher', component: () => import('../views/TeacherProfile.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'TEACHER'] } },
      { path: 'schedule', component: () => import('../views/CourseSchedule.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'STUDENT'] } },
      { path: 'attendance', component: () => import('../views/AttendanceRecord.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR', 'STUDENT'] } },
      { path: 'appeal', component: () => import('../views/ExceptionAppeal.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR', 'STUDENT'] } },
      { path: 'review', component: () => import('../views/AppealReview.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR'] } },
      { path: 'inspection', component: () => import('../views/ClassroomInspection.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'INSPECTOR', 'TEACHER'] } },
      { path: 'issue', component: () => import('../views/InspectionIssue.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'INSPECTOR', 'COUNSELOR', 'TEACHER'] } },
      { path: 'rectification', component: () => import('../views/RectificationTask.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'INSPECTOR', 'COUNSELOR', 'TEACHER'] } },
      { path: 'feedback', component: () => import('../views/RectificationFeedback.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'INSPECTOR', 'COUNSELOR', 'TEACHER', 'STUDENT'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'
  if (to.path !== '/login' && !userStore.token) return next('/login')
  if (to.path === '/login' && userStore.token) return next(home)
  if (to.meta?.roles && !to.meta.roles.includes(role)) return next(home)
  next()
})
export default router
