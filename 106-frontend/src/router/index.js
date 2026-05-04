import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/SysUser.vue') },
  { path: 'environment', component: () => import('../views/DevopsEnvironment.vue') },
  { path: 'service', component: () => import('../views/ApplicationService.vue') },
  { path: 'pipeline', component: () => import('../views/DeployPipeline.vue') },
  { path: 'release-plan', component: () => import('../views/ReleasePlan.vue') },
  { path: 'release-order', component: () => import('../views/ReleaseOrder.vue') },
  { path: 'approval-flow', component: () => import('../views/ApprovalFlow.vue') },
  { path: 'approval-record', component: () => import('../views/ApprovalRecord.vue') },
  { path: 'artifact', component: () => import('../views/VersionArtifact.vue') },
  { path: 'deploy-task', component: () => import('../views/DeployTask.vue') },
  { path: 'rollback-plan', component: () => import('../views/RollbackPlan.vue') },
  { path: 'rollback-record', component: () => import('../views/RollbackRecord.vue') },
  { path: 'checklist', component: () => import('../views/ChangeChecklist.vue') },
  { path: 'log', component: () => import('../views/OperationLog.vue') },
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) next('/login')
  else if (to.path === '/login' && userStore.token) next('/dashboard')
  else next()
})

export default router
