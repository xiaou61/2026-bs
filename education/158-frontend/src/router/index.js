import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  PRINCIPAL: '/dashboard',
  ACADEMIC: '/schedule',
  TEACHER: '/attendance',
  FINANCE: '/refund',
  PARENT: '/consumption'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER', 'FINANCE', 'PARENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'branch', component: () => import('../views/CampusBranch.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL'] } },
      { path: 'course', component: () => import('../views/CourseCatalog.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'FINANCE'] } },
      { path: 'student', component: () => import('../views/StudentProfile.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'PARENT'] } },
      { path: 'teacher', component: () => import('../views/TeacherProfile.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER'] } },
      { path: 'classgroup', component: () => import('../views/ClassGroup.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER'] } },
      { path: 'schedule', component: () => import('../views/LessonSchedule.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER'] } },
      { path: 'attendance', component: () => import('../views/AttendanceRecord.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'PARENT'] } },
      { path: 'consumption', component: () => import('../views/ConsumptionRecord.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'FINANCE', 'PARENT'] } },
      { path: 'refund', component: () => import('../views/RefundApplication.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL', 'FINANCE', 'PARENT'] } },
      { path: 'approval', component: () => import('../views/RefundApproval.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL', 'FINANCE'] } },
      { path: 'ledger', component: () => import('../views/FinanceLedger.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL', 'FINANCE'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'PRINCIPAL'] } }
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
