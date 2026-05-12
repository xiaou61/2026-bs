import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  COMPLIANCE: '/dashboard',
  DEVELOPER: '/repository',
  AUDITOR: '/report'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'team', component: () => import('../views/OrganizationTeam.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER'] } },
      { path: 'repository', component: () => import('../views/ProjectRepository.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] } },
      { path: 'branch', component: () => import('../views/RepositoryBranch.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] } },
      { path: 'dependency', component: () => import('../views/DependencyPackage.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] } },
      { path: 'policy', component: () => import('../views/LicensePolicy.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] } },
      { path: 'baseline', component: () => import('../views/ComplianceBaseline.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] } },
      { path: 'scan-task', component: () => import('../views/ScanTask.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] } },
      { path: 'scan-result', component: () => import('../views/ScanResult.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] } },
      { path: 'issue', component: () => import('../views/RiskIssue.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] } },
      { path: 'rectification', component: () => import('../views/RectificationTask.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] } },
      { path: 'report', component: () => import('../views/AuditReport.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] } },
      { path: 'approval', component: () => import('../views/ApprovalRecord.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] } },
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
