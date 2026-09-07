import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  SECURITY: '/rule',
  OWNER: '/access-request',
  AUDITOR: '/recognition-result'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'source', component: () => import('../views/DataSourceConfig.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] } },
      { path: 'dataset', component: () => import('../views/DataSetCatalog.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] } },
      { path: 'rule', component: () => import('../views/SensitiveRule.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'recognition-task', component: () => import('../views/RecognitionTask.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'recognition-result', component: () => import('../views/RecognitionResult.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] } },
      { path: 'strategy', component: () => import('../views/MaskingStrategy.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'masking-task', component: () => import('../views/MaskingTask.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'masking-record', component: () => import('../views/MaskingRecord.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] } },
      { path: 'lineage', component: () => import('../views/FieldLineage.vue'), meta: { roles: ['ADMIN', 'OWNER', 'AUDITOR'] } },
      { path: 'access-request', component: () => import('../views/AccessRequest.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] } },
      { path: 'export-approval', component: () => import('../views/ExportApproval.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] } },
      { path: 'risk-alert', component: () => import('../views/RiskAlert.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] } },
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
