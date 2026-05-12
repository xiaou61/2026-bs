import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  RELEASE: '/release-order',
  OPS: '/deploy-task',
  AUDITOR: '/approval-record'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'AUDITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'environment', component: () => import('../views/DevopsEnvironment.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'service', component: () => import('../views/ApplicationService.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'pipeline', component: () => import('../views/DeployPipeline.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'release-plan', component: () => import('../views/ReleasePlan.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'release-order', component: () => import('../views/ReleaseOrder.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'approval-flow', component: () => import('../views/ApprovalFlow.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'AUDITOR'] } },
      { path: 'approval-record', component: () => import('../views/ApprovalRecord.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'AUDITOR'] } },
      { path: 'artifact', component: () => import('../views/VersionArtifact.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'deploy-task', component: () => import('../views/DeployTask.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'rollback-plan', component: () => import('../views/RollbackPlan.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'rollback-record', component: () => import('../views/RollbackRecord.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'checklist', component: () => import('../views/ChangeChecklist.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'RELEASE', 'AUDITOR'] } },
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
