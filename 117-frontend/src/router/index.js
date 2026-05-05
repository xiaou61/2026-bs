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
      { path: 'merchant', component: () => import('../views/MerchantInfo.vue') },
      { path: 'store', component: () => import('../views/StoreBranch.vue') },
      { path: 'consumer', component: () => import('../views/ConsumerProfile.vue') },
      { path: 'template', component: () => import('../views/CouponTemplate.vue') },
      { path: 'activity', component: () => import('../views/CouponActivity.vue') },
      { path: 'coupon', component: () => import('../views/UserCoupon.vue') },
      { path: 'verification', component: () => import('../views/VerificationRecord.vue') },
      { path: 'settlement', component: () => import('../views/MerchantSettlement.vue') },
      { path: 'detail', component: () => import('../views/SettlementDetail.vue') },
      { path: 'transfer', component: () => import('../views/PaymentTransfer.vue') },
      { path: 'complaint', component: () => import('../views/ComplaintTicket.vue') },
      { path: 'statRecord', component: () => import('../views/ActivityStatRecord.vue') },
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
