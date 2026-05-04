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
  { path: 'team', component: () => import('../views/OrganizationTeam.vue') },
  { path: 'repository', component: () => import('../views/ProjectRepository.vue') },
  { path: 'branch', component: () => import('../views/RepositoryBranch.vue') },
  { path: 'dependency', component: () => import('../views/DependencyPackage.vue') },
  { path: 'policy', component: () => import('../views/LicensePolicy.vue') },
  { path: 'baseline', component: () => import('../views/ComplianceBaseline.vue') },
  { path: 'scan-task', component: () => import('../views/ScanTask.vue') },
  { path: 'scan-result', component: () => import('../views/ScanResult.vue') },
  { path: 'issue', component: () => import('../views/RiskIssue.vue') },
  { path: 'rectification', component: () => import('../views/RectificationTask.vue') },
  { path: 'report', component: () => import('../views/AuditReport.vue') },
  { path: 'approval', component: () => import('../views/ApprovalRecord.vue') },
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
