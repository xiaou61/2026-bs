import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  MERCHANT: '/merchant',
  CASHIER: '/verification',
  FINANCE: '/settlement'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'MERCHANT', 'CASHIER', 'FINANCE'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'merchant', component: () => import('../views/MerchantInfo.vue'), meta: { roles: ['ADMIN', 'MERCHANT'] } },
      { path: 'store', component: () => import('../views/StoreBranch.vue'), meta: { roles: ['ADMIN', 'MERCHANT'] } },
      { path: 'consumer', component: () => import('../views/ConsumerProfile.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'template', component: () => import('../views/CouponTemplate.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'activity', component: () => import('../views/CouponActivity.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'coupon', component: () => import('../views/UserCoupon.vue'), meta: { roles: ['ADMIN', 'MERCHANT'] } },
      { path: 'verification', component: () => import('../views/VerificationRecord.vue'), meta: { roles: ['ADMIN', 'MERCHANT', 'CASHIER'] } },
      { path: 'settlement', component: () => import('../views/MerchantSettlement.vue'), meta: { roles: ['ADMIN', 'MERCHANT', 'FINANCE'] } },
      { path: 'detail', component: () => import('../views/SettlementDetail.vue'), meta: { roles: ['ADMIN', 'MERCHANT', 'FINANCE'] } },
      { path: 'transfer', component: () => import('../views/PaymentTransfer.vue'), meta: { roles: ['ADMIN', 'MERCHANT', 'FINANCE'] } },
      { path: 'complaint', component: () => import('../views/ComplaintTicket.vue'), meta: { roles: ['ADMIN', 'MERCHANT', 'FINANCE'] } },
      { path: 'statRecord', component: () => import('../views/ActivityStatRecord.vue'), meta: { roles: ['ADMIN', 'FINANCE'] } },
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
