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
      { path: 'indicator', component: () => import('../views/IndicatorLibrary.vue') },
      { path: 'template', component: () => import('../views/DisclosureTemplate.vue') },
      { path: 'period', component: () => import('../views/ReportingPeriod.vue') },
      { path: 'submission', component: () => import('../views/CompanySubmission.vue') },
      { path: 'data', component: () => import('../views/IndicatorData.vue') },
      { path: 'evidence', component: () => import('../views/EvidenceFile.vue') },
      { path: 'review', component: () => import('../views/ReviewTask.vue') },
      { path: 'model', component: () => import('../views/ScoreModel.vue') },
      { path: 'score', component: () => import('../views/EsgScore.vue') },
      { path: 'improvement', component: () => import('../views/ImprovementTask.vue') },
      { path: 'feedback', component: () => import('../views/StakeholderFeedback.vue') },
      { path: 'export', component: () => import('../views/ReportExport.vue') },
      { path: 'log', component: () => import('../views/OperationLog.vue') }
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
