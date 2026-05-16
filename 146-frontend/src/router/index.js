import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  INSPECTOR: '/task',
  MERCHANT: '/recheck',
  REVIEWER: '/warning'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'plan', component: () => import('../views/InspectionPlan.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] } },
      { path: 'food', component: () => import('../views/FoodItem.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] } },
      { path: 'merchant', component: () => import('../views/MerchantProfile.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] } },
      { path: 'task', component: () => import('../views/SamplingTask.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] } },
      { path: 'agency', component: () => import('../views/AgencyProfile.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] } },
      { path: 'sample', component: () => import('../views/SampleRecord.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] } },
      { path: 'result', component: () => import('../views/TestResult.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] } },
      { path: 'recheck', component: () => import('../views/RecheckApplication.vue'), meta: { roles: ['ADMIN', 'MERCHANT', 'REVIEWER'] } },
      { path: 'disposal', component: () => import('../views/DisposalRecord.vue'), meta: { roles: ['ADMIN', 'REVIEWER'] } },
      { path: 'report', component: () => import('../views/PublicReport.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] } },
      { path: 'warning', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'REVIEWER'] } },
      { path: 'notice', component: () => import('../views/InspectionNotice.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'

  if (to.path !== '/login' && !userStore.token) {
    next('/login')
    return
  }
  if (to.path === '/login' && userStore.token) {
    next(home)
    return
  }
  if (to.path === '/' && userStore.token) {
    next(home)
    return
  }
  if (to.meta?.roles && !canAccess(role, to.meta.roles)) {
    next(home)
    return
  }
  next()
})

export default router
