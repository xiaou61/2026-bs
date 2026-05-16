import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  MANAGER: '/dashboard',
  GUIDE: '/schedule',
  CHECKER: '/verification',
  VISITOR: '/ticket-order'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'venue', component: () => import('../views/VenueInfo.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'ticket', component: () => import('../views/TicketProduct.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'ticket-order', component: () => import('../views/TicketOrder.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'guide', component: () => import('../views/GuideProfile.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'schedule', component: () => import('../views/GuideSchedule.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'booking', component: () => import('../views/GuideBooking.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'verification', component: () => import('../views/TicketVerification.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'crowd-flow', component: () => import('../views/CrowdFlowRecord.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'feedback', component: () => import('../views/VisitorFeedback.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'activity', component: () => import('../views/MarketingActivity.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'notice', component: () => import('../views/VenueNotice.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] } }
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
