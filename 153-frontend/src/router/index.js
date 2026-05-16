import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  OPERATOR: '/dashboard',
  SELLER: '/consignment',
  BUYER: '/order',
  ARBITER: '/complaint'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'category', component: () => import('../views/ItemCategory.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'student', component: () => import('../views/StudentProfile.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'consignment', component: () => import('../views/ConsignmentItem.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'audit', component: () => import('../views/ItemAudit.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'order', component: () => import('../views/EscrowOrder.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'handover', component: () => import('../views/HandoverRecord.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'credit', component: () => import('../views/CreditEvaluation.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'breach', component: () => import('../views/BreachRecord.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'complaint', component: () => import('../views/ComplaintTicket.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'notice', component: () => import('../views/PlatformNotice.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'
  if (to.path !== '/login' && !userStore.token) return next('/login')
  if (to.path === '/login' && userStore.token) return next(home)
  if (to.meta?.roles && !to.meta.roles.includes(role)) return next(home)
  next()
})
export default router
