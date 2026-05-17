import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  AGENCY: '/station',
  DISPATCH: '/dispatch',
  WORKER: '/schedule',
  QUALITY: '/evaluation',
  RESIDENT: '/booking'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'QUALITY', 'RESIDENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'station', component: () => import('../views/ServiceStation.vue'), meta: { roles: ['ADMIN', 'AGENCY'] } },
      { path: 'resident', component: () => import('../views/ResidentProfile.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT'] } },
      { path: 'worker', component: () => import('../views/WorkerProfile.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'QUALITY'] } },
      { path: 'service', component: () => import('../views/ServiceCatalog.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT'] } },
      { path: 'booking', component: () => import('../views/ServiceBooking.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT'] } },
      { path: 'review', component: () => import('../views/BookingReview.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH'] } },
      { path: 'dispatch', component: () => import('../views/ScheduleDispatch.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER'] } },
      { path: 'record', component: () => import('../views/ServiceRecord.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'RESIDENT'] } },
      { path: 'evaluation', component: () => import('../views/CreditEvaluation.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'QUALITY', 'RESIDENT'] } },
      { path: 'complaint', component: () => import('../views/ComplaintHandling.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'QUALITY', 'RESIDENT'] } },
      { path: 'settlement', component: () => import('../views/SettlementRecord.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH'] } },
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
