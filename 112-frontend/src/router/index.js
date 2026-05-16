import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  SECURITY: '/risk-model',
  NETWORK: '/application',
  AUDITOR: '/audit-event'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'NETWORK', 'AUDITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'device', component: () => import('../views/DeviceAsset.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'NETWORK', 'AUDITOR'] } },
      { path: 'employee', component: () => import('../views/EmployeeAccount.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'NETWORK', 'AUDITOR'] } },
      { path: 'idp', component: () => import('../views/IdentityProvider.vue'), meta: { roles: ['ADMIN', 'NETWORK'] } },
      { path: 'risk-model', component: () => import('../views/RiskModel.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'assessment', component: () => import('../views/RiskAssessment.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'policy', component: () => import('../views/AccessPolicy.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'rule', component: () => import('../views/PolicyRule.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'application', component: () => import('../views/AccessApplication.vue'), meta: { roles: ['ADMIN', 'NETWORK', 'AUDITOR'] } },
      { path: 'session', component: () => import('../views/AccessSession.vue'), meta: { roles: ['ADMIN', 'NETWORK', 'AUDITOR'] } },
      { path: 'segment', component: () => import('../views/NetworkSegment.vue'), meta: { roles: ['ADMIN', 'NETWORK'] } },
      { path: 'certificate', component: () => import('../views/DeviceCertificate.vue'), meta: { roles: ['ADMIN', 'NETWORK'] } },
      { path: 'audit-event', component: () => import('../views/AuditEvent.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'NETWORK', 'AUDITOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'AUDITOR'] } }
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
