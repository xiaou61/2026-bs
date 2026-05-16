import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  LEGAL: '/template',
  APPLICANT: '/draft',
  APPROVER: '/approval'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'template', component: () => import('../views/ContractTemplate.vue'), meta: { roles: ['ADMIN', 'LEGAL'] } },
      { path: 'counterparty', component: () => import('../views/CounterpartyProfile.vue'), meta: { roles: ['ADMIN', 'LEGAL'] } },
      { path: 'signer', component: () => import('../views/SignerProfile.vue'), meta: { roles: ['ADMIN', 'LEGAL'] } },
      { path: 'draft', component: () => import('../views/ContractDraft.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
      { path: 'seal-apply', component: () => import('../views/SealApplication.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
      { path: 'approval', component: () => import('../views/ApprovalFlow.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPROVER'] } },
      { path: 'signing', component: () => import('../views/ContractSigning.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPROVER'] } },
      { path: 'seal-record', component: () => import('../views/SealRecord.vue'), meta: { roles: ['ADMIN', 'LEGAL'] } },
      { path: 'archive', component: () => import('../views/ArchiveRecord.vue'), meta: { roles: ['ADMIN', 'LEGAL'] } },
      { path: 'reminder', component: () => import('../views/ExpirationReminder.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
      { path: 'risk', component: () => import('../views/RiskClause.vue'), meta: { roles: ['ADMIN', 'LEGAL'] } },
      { path: 'notice', component: () => import('../views/ContractNotice.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
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






