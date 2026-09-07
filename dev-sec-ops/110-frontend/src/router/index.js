import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  PRIVACY: '/purpose',
  DATAUSER: '/access-application',
  AUDITOR: '/access-log'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'subject', component: () => import('../views/DataSubject.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] } },
      { path: 'data-item', component: () => import('../views/PersonalDataItem.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] } },
      { path: 'purpose', component: () => import('../views/ConsentPurpose.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] } },
      { path: 'policy', component: () => import('../views/ConsentPolicy.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] } },
      { path: 'authorization', component: () => import('../views/AuthorizationRecord.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] } },
      { path: 'scope', component: () => import('../views/AuthorizationScope.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] } },
      { path: 'access-application', component: () => import('../views/AccessApplication.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] } },
      { path: 'grant', component: () => import('../views/AccessGrant.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] } },
      { path: 'access-log', component: () => import('../views/AccessLog.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] } },
      { path: 'revocation', component: () => import('../views/RevocationRequest.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] } },
      { path: 'risk-warning', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] } },
      { path: 'audit-report', component: () => import('../views/AuditReport.vue'), meta: { roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] } },
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
