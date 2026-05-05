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
      { path: 'channel', component: () => import('../views/LiveChannel.vue') },
      { path: 'anchor', component: () => import('../views/AnchorProfile.vue') },
      { path: 'supplier', component: () => import('../views/SupplierBrand.vue') },
      { path: 'selection', component: () => import('../views/ProductSelection.vue') },
      { path: 'session', component: () => import('../views/LiveSession.vue') },
      { path: 'schedule', component: () => import('../views/SchedulePlan.vue') },
      { path: 'sample', component: () => import('../views/SampleRequest.vue') },
      { path: 'script', component: () => import('../views/PromotionScript.vue') },
      { path: 'order', component: () => import('../views/LiveOrder.vue') },
      { path: 'ticket', component: () => import('../views/AfterSaleTicket.vue') },
      { path: 'refund', component: () => import('../views/RefundRecord.vue') },
      { path: 'performance', component: () => import('../views/AnchorPerformance.vue') },
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
