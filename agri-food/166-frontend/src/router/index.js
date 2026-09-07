import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  MARKET: '/stall',
  INSPECTOR: '/inspection',
  VENDOR: '/product',
  SAMPLER: '/sample',
  REGULATOR: '/alert'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'SAMPLER', 'REGULATOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'area', component: () => import('../views/MarketArea.vue'), meta: { roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'REGULATOR'] } },
      { path: 'stall', component: () => import('../views/StallProfile.vue'), meta: { roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR'] } },
      { path: 'vendor', component: () => import('../views/VendorProfile.vue'), meta: { roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR'] } },
      { path: 'product', component: () => import('../views/ProductTrace.vue'), meta: { roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR'] } },
      { path: 'inspection', component: () => import('../views/InspectionTask.vue'), meta: { roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'REGULATOR'] } },
      { path: 'rectification', component: () => import('../views/IssueRectification.vue'), meta: { roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR'] } },
      { path: 'sample', component: () => import('../views/SampleRecord.vue'), meta: { roles: ['ADMIN', 'MARKET', 'SAMPLER', 'REGULATOR'] } },
      { path: 'test', component: () => import('../views/TestResult.vue'), meta: { roles: ['ADMIN', 'MARKET', 'SAMPLER', 'REGULATOR', 'VENDOR'] } },
      { path: 'source', component: () => import('../views/SourceLedger.vue'), meta: { roles: ['ADMIN', 'MARKET', 'VENDOR', 'INSPECTOR'] } },
      { path: 'disposal', component: () => import('../views/DisposalRecord.vue'), meta: { roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR'] } },
      { path: 'alert', component: () => import('../views/RiskAlert.vue'), meta: { roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'SAMPLER', 'REGULATOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'REGULATOR'] } }
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
