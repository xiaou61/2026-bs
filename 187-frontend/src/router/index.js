import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  AGENCY: '/agency',
  PLANNER: '/trip',
  FINANCE: '/share',
  LEADER: '/signup',
  PARTICIPANT: '/confirmation'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'PLANNER', 'FINANCE', 'LEADER', 'PARTICIPANT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'agency', component: () => import('../views/AgencyInfo.vue'), meta: { roles: ['ADMIN', 'AGENCY'] } },
      { path: 'team', component: () => import('../views/TeamProfile.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'LEADER'] } },
      { path: 'trip', component: () => import('../views/TripPlan.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'PLANNER', 'LEADER', 'PARTICIPANT'] } },
      { path: 'signup', component: () => import('../views/SignupRegistration.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'PLANNER', 'LEADER', 'PARTICIPANT'] } },
      { path: 'confirmation', component: () => import('../views/MemberConfirmation.vue'), meta: { roles: ['ADMIN', 'LEADER', 'PARTICIPANT'] } },
      { path: 'budget', component: () => import('../views/CostBudget.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'PLANNER', 'FINANCE', 'LEADER'] } },
      { path: 'share', component: () => import('../views/CostShare.vue'), meta: { roles: ['ADMIN', 'FINANCE', 'LEADER', 'PARTICIPANT'] } },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue'), meta: { roles: ['ADMIN', 'FINANCE', 'LEADER', 'PARTICIPANT'] } },
      { path: 'notice', component: () => import('../views/NoticeSync.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'PLANNER', 'LEADER', 'PARTICIPANT'] } },
      { path: 'feedback', component: () => import('../views/TravelFeedback.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'LEADER', 'PARTICIPANT'] } },
      { path: 'invoice', component: () => import('../views/InvoiceRecord.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'FINANCE', 'LEADER'] } },
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
