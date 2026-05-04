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
      { path: 'subject', component: () => import('../views/DataSubject.vue') },
      { path: 'data-item', component: () => import('../views/PersonalDataItem.vue') },
      { path: 'purpose', component: () => import('../views/ConsentPurpose.vue') },
      { path: 'policy', component: () => import('../views/ConsentPolicy.vue') },
      { path: 'authorization', component: () => import('../views/AuthorizationRecord.vue') },
      { path: 'scope', component: () => import('../views/AuthorizationScope.vue') },
      { path: 'access-application', component: () => import('../views/AccessApplication.vue') },
      { path: 'grant', component: () => import('../views/AccessGrant.vue') },
      { path: 'access-log', component: () => import('../views/AccessLog.vue') },
      { path: 'revocation', component: () => import('../views/RevocationRequest.vue') },
      { path: 'risk-warning', component: () => import('../views/RiskWarning.vue') },
      { path: 'audit-report', component: () => import('../views/AuditReport.vue') },
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
