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
      { path: 'device', component: () => import('../views/DeviceAsset.vue') },
      { path: 'employee', component: () => import('../views/EmployeeAccount.vue') },
      { path: 'idp', component: () => import('../views/IdentityProvider.vue') },
      { path: 'risk-model', component: () => import('../views/RiskModel.vue') },
      { path: 'assessment', component: () => import('../views/RiskAssessment.vue') },
      { path: 'policy', component: () => import('../views/AccessPolicy.vue') },
      { path: 'rule', component: () => import('../views/PolicyRule.vue') },
      { path: 'application', component: () => import('../views/AccessApplication.vue') },
      { path: 'session', component: () => import('../views/AccessSession.vue') },
      { path: 'segment', component: () => import('../views/NetworkSegment.vue') },
      { path: 'certificate', component: () => import('../views/DeviceCertificate.vue') },
      { path: 'audit-event', component: () => import('../views/AuditEvent.vue') },
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
