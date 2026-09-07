import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  BASE: '/studio',
  ANCHOR: '/schedule',
  SELECTOR: '/review',
  SAMPLE: '/sample',
  MERCHANT: '/product'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'SAMPLE', 'MERCHANT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'studio', component: () => import('../views/LiveStudio.vue'), meta: { roles: ['ADMIN', 'BASE', 'ANCHOR'] } },
      { path: 'anchor', component: () => import('../views/AnchorProfile.vue'), meta: { roles: ['ADMIN', 'BASE', 'ANCHOR'] } },
      { path: 'merchant', component: () => import('../views/MerchantProfile.vue'), meta: { roles: ['ADMIN', 'BASE', 'SELECTOR', 'MERCHANT'] } },
      { path: 'product', component: () => import('../views/ProductCatalog.vue'), meta: { roles: ['ADMIN', 'BASE', 'SELECTOR', 'MERCHANT', 'ANCHOR'] } },
      { path: 'sample', component: () => import('../views/SampleLedger.vue'), meta: { roles: ['ADMIN', 'BASE', 'SELECTOR', 'SAMPLE', 'MERCHANT'] } },
      { path: 'loan', component: () => import('../views/SampleLoan.vue'), meta: { roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'SAMPLE', 'MERCHANT'] } },
      { path: 'schedule', component: () => import('../views/AnchorSchedule.vue'), meta: { roles: ['ADMIN', 'BASE', 'ANCHOR'] } },
      { path: 'review', component: () => import('../views/SelectionReview.vue'), meta: { roles: ['ADMIN', 'BASE', 'SELECTOR', 'ANCHOR', 'MERCHANT'] } },
      { path: 'plan', component: () => import('../views/LivePlan.vue'), meta: { roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'MERCHANT'] } },
      { path: 'session', component: () => import('../views/LiveSession.vue'), meta: { roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR'] } },
      { path: 'replay', component: () => import('../views/LiveReplay.vue'), meta: { roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'MERCHANT'] } },
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
