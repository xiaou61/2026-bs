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
      { path: 'template', component: () => import('../views/ContractTemplate.vue') },
      { path: 'counterparty', component: () => import('../views/CounterpartyProfile.vue') },
      { path: 'signer', component: () => import('../views/SignerProfile.vue') },
      { path: 'draft', component: () => import('../views/ContractDraft.vue') },
      { path: 'seal-apply', component: () => import('../views/SealApplication.vue') },
      { path: 'approval', component: () => import('../views/ApprovalFlow.vue') },
      { path: 'signing', component: () => import('../views/ContractSigning.vue') },
      { path: 'seal-record', component: () => import('../views/SealRecord.vue') },
      { path: 'archive', component: () => import('../views/ArchiveRecord.vue') },
      { path: 'reminder', component: () => import('../views/ExpirationReminder.vue') },
      { path: 'risk', component: () => import('../views/RiskClause.vue') },
      { path: 'notice', component: () => import('../views/ContractNotice.vue') },
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






