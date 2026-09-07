import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  ACCOUNTANT: '/company',
  AUDITOR: '/report',
  MANAGER: '/task'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'company', component: () => import('../views/CompanyProfile.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] } },
      { path: 'factor', component: () => import('../views/EmissionFactor.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR'] } },
      { path: 'period', component: () => import('../views/AccountingPeriod.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] } },
      { path: 'consumption', component: () => import('../views/EnergyConsumption.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR'] } },
      { path: 'emission', component: () => import('../views/EmissionRecord.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] } },
      { path: 'task', component: () => import('../views/ReductionTask.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] } },
      { path: 'measure', component: () => import('../views/ReductionMeasure.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] } },
      { path: 'quota', component: () => import('../views/CarbonQuota.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] } },
      { path: 'report', component: () => import('../views/VerificationReport.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] } },
      { path: 'attachment', component: () => import('../views/DataAttachment.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR'] } },
      { path: 'rule', component: () => import('../views/AlertRule.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'MANAGER'] } },
      { path: 'warning', component: () => import('../views/CarbonWarning.vue'), meta: { roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] } },
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
  if (to.meta?.roles && !canAccess(role, to.meta.roles)) {
    next(home)
    return
  }
  next()
})

export default router
