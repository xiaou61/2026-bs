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
      { path: 'source', component: () => import('../views/DataSourceConfig.vue') },
      { path: 'dataset', component: () => import('../views/DataSetCatalog.vue') },
      { path: 'rule', component: () => import('../views/SensitiveRule.vue') },
      { path: 'recognition-task', component: () => import('../views/RecognitionTask.vue') },
      { path: 'recognition-result', component: () => import('../views/RecognitionResult.vue') },
      { path: 'strategy', component: () => import('../views/MaskingStrategy.vue') },
      { path: 'masking-task', component: () => import('../views/MaskingTask.vue') },
      { path: 'masking-record', component: () => import('../views/MaskingRecord.vue') },
      { path: 'lineage', component: () => import('../views/FieldLineage.vue') },
      { path: 'access-request', component: () => import('../views/AccessRequest.vue') },
      { path: 'export-approval', component: () => import('../views/ExportApproval.vue') },
      { path: 'risk-alert', component: () => import('../views/RiskAlert.vue') },
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
