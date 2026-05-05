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
      { path: 'merchant', component: () => import('../views/MerchantStore.vue') },
      { path: 'customer', component: () => import('../views/CustomerProfile.vue') },
      { path: 'sku', component: () => import('../views/ProductSku.vue') },
      { path: 'order', component: () => import('../views/CrossBorderOrder.vue') },
      { path: 'declaration', component: () => import('../views/ClearanceDeclaration.vue') },
      { path: 'document', component: () => import('../views/CustomsDocument.vue') },
      { path: 'tax', component: () => import('../views/TaxFeeRecord.vue') },
      { path: 'rate', component: () => import('../views/ExchangeRate.vue') },
      { path: 'settlement', component: () => import('../views/SettlementBill.vue') },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue') },
      { path: 'logistics', component: () => import('../views/LogisticsTrack.vue') },
      { path: 'reconciliation', component: () => import('../views/ReconciliationTask.vue') },
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
