import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  EDITOR: '/submission',
  REVIEWER: '/review',
  ESG_MANAGER: '/score'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'indicator', component: () => import('../views/IndicatorLibrary.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'template', component: () => import('../views/DisclosureTemplate.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'period', component: () => import('../views/ReportingPeriod.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'submission', component: () => import('../views/CompanySubmission.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'data', component: () => import('../views/IndicatorData.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'evidence', component: () => import('../views/EvidenceFile.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER'] } },
      { path: 'review', component: () => import('../views/ReviewTask.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'model', component: () => import('../views/ScoreModel.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'score', component: () => import('../views/EsgScore.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'improvement', component: () => import('../views/ImprovementTask.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'feedback', component: () => import('../views/StakeholderFeedback.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
      { path: 'export', component: () => import('../views/ReportExport.vue'), meta: { roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] } },
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
