import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CENTER: '/item',
  WINDOW: '/queue',
  REVIEW: '/review',
  SUPERVISE: '/complaint',
  CITIZEN: '/appointment'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CENTER', 'WINDOW', 'REVIEW', 'SUPERVISE', 'CITIZEN'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'item', component: () => import('../views/ServiceItem.vue'), meta: { roles: ['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN'] } },
      { path: 'window', component: () => import('../views/WindowProfile.vue'), meta: { roles: ['ADMIN', 'CENTER', 'WINDOW'] } },
      { path: 'roster', component: () => import('../views/ClerkRoster.vue'), meta: { roles: ['ADMIN', 'CENTER', 'WINDOW'] } },
      { path: 'appointment', component: () => import('../views/AppointmentBooking.vue'), meta: { roles: ['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN'] } },
      { path: 'queue', component: () => import('../views/QueueCall.vue'), meta: { roles: ['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN'] } },
      { path: 'review', component: () => import('../views/MaterialReview.vue'), meta: { roles: ['ADMIN', 'CENTER', 'REVIEW', 'WINDOW'] } },
      { path: 'progress', component: () => import('../views/ProcessProgress.vue'), meta: { roles: ['ADMIN', 'CENTER', 'WINDOW', 'REVIEW', 'CITIZEN'] } },
      { path: 'notice', component: () => import('../views/MessageNotice.vue'), meta: { roles: ['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN'] } },
      { path: 'evaluation', component: () => import('../views/ServiceEvaluation.vue'), meta: { roles: ['ADMIN', 'CENTER', 'SUPERVISE', 'CITIZEN'] } },
      { path: 'complaint', component: () => import('../views/ComplaintHandling.vue'), meta: { roles: ['ADMIN', 'CENTER', 'SUPERVISE', 'CITIZEN'] } },
      { path: 'performance', component: () => import('../views/PerformanceArchive.vue'), meta: { roles: ['ADMIN', 'CENTER', 'SUPERVISE'] } },
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
