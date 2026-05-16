import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  OPERATOR: '/channel',
  SERVICE: '/ticket',
  MERCHANT: '/supplier'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SERVICE', 'MERCHANT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'channel', component: () => import('../views/LiveChannel.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
      { path: 'anchor', component: () => import('../views/AnchorProfile.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
      { path: 'supplier', component: () => import('../views/SupplierBrand.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MERCHANT'] } },
      { path: 'selection', component: () => import('../views/ProductSelection.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MERCHANT'] } },
      { path: 'session', component: () => import('../views/LiveSession.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
      { path: 'schedule', component: () => import('../views/SchedulePlan.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
      { path: 'sample', component: () => import('../views/SampleRequest.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MERCHANT'] } },
      { path: 'script', component: () => import('../views/PromotionScript.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
      { path: 'order', component: () => import('../views/LiveOrder.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SERVICE'] } },
      { path: 'ticket', component: () => import('../views/AfterSaleTicket.vue'), meta: { roles: ['ADMIN', 'SERVICE'] } },
      { path: 'refund', component: () => import('../views/RefundRecord.vue'), meta: { roles: ['ADMIN', 'SERVICE'] } },
      { path: 'performance', component: () => import('../views/AnchorPerformance.vue'), meta: { roles: ['ADMIN', 'OPERATOR'] } },
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
