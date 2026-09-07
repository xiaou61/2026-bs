import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CENTER: '/classroom',
  TEACHER: '/checkin',
  PARENT: '/pickup',
  SECURITY: '/alert',
  NURSE: '/health'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'SECURITY', 'NURSE'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'classroom', component: () => import('../views/CareClass.vue'), meta: { roles: ['ADMIN', 'CENTER', 'TEACHER'] } },
      { path: 'child', component: () => import('../views/ChildProfile.vue'), meta: { roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'NURSE'] } },
      { path: 'guardian', component: () => import('../views/GuardianProfile.vue'), meta: { roles: ['ADMIN', 'CENTER', 'PARENT', 'SECURITY'] } },
      { path: 'checkin', component: () => import('../views/AttendanceCheckin.vue'), meta: { roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT'] } },
      { path: 'authorization', component: () => import('../views/PickupAuthorization.vue'), meta: { roles: ['ADMIN', 'CENTER', 'PARENT', 'SECURITY'] } },
      { path: 'pickup', component: () => import('../views/PickupRecord.vue'), meta: { roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'SECURITY'] } },
      { path: 'alert', component: () => import('../views/SafetyAlert.vue'), meta: { roles: ['ADMIN', 'CENTER', 'SECURITY', 'TEACHER', 'PARENT'] } },
      { path: 'health', component: () => import('../views/HealthCheck.vue'), meta: { roles: ['ADMIN', 'CENTER', 'NURSE', 'TEACHER', 'PARENT'] } },
      { path: 'activity', component: () => import('../views/ActivitySchedule.vue'), meta: { roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT'] } },
      { path: 'notice', component: () => import('../views/ParentNotice.vue'), meta: { roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'SECURITY', 'NURSE'] } },
      { path: 'incident', component: () => import('../views/IncidentFollowup.vue'), meta: { roles: ['ADMIN', 'CENTER', 'SECURITY', 'TEACHER', 'PARENT'] } },
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
