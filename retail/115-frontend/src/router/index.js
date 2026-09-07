import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CUSTOMS: '/declaration',
  FINANCE: '/rate',
  OPERATOR: '/merchant'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CUSTOMS', 'FINANCE', 'OPERATOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'merchant', component: () => import('../views/MerchantStore.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
      { path: 'customer', component: () => import('../views/CustomerProfile.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
      { path: 'sku', component: () => import('../views/ProductSku.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
      { path: 'order', component: () => import('../views/CrossBorderOrder.vue'), meta: { roles: ['ADMIN', 'CUSTOMS', 'FINANCE', 'OPERATOR'] } },
      { path: 'declaration', component: () => import('../views/ClearanceDeclaration.vue'), meta: { roles: ['ADMIN', 'CUSTOMS', 'OPERATOR'] } },
      { path: 'document', component: () => import('../views/CustomsDocument.vue'), meta: { roles: ['ADMIN', 'CUSTOMS'] } },
      { path: 'tax', component: () => import('../views/TaxFeeRecord.vue'), meta: { roles: ['ADMIN', 'CUSTOMS', 'FINANCE'] } },
      { path: 'rate', component: () => import('../views/ExchangeRate.vue'), meta: { roles: ['ADMIN', 'FINANCE'] } },
      { path: 'settlement', component: () => import('../views/SettlementBill.vue'), meta: { roles: ['ADMIN', 'FINANCE'] } },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue'), meta: { roles: ['ADMIN', 'FINANCE'] } },
      { path: 'logistics', component: () => import('../views/LogisticsTrack.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
      { path: 'reconciliation', component: () => import('../views/ReconciliationTask.vue'), meta: { roles: ['ADMIN', 'FINANCE'] } },
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
