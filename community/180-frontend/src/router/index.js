import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  PROPERTY: '/area',
  OWNER: '/repair',
  DISPATCH: '/dispatch',
  WORKER: '/service',
  FINANCE: '/fee'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'OWNER', 'DISPATCH', 'WORKER', 'FINANCE'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'area', component: () => import('../views/CommunityArea.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH'] } },
      { path: 'owner', component: () => import('../views/OwnerProfile.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'OWNER', 'FINANCE'] } },
      { path: 'category', component: () => import('../views/RepairCategory.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'WORKER'] } },
      { path: 'repair', component: () => import('../views/RepairOrder.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'OWNER', 'DISPATCH', 'WORKER'] } },
      { path: 'dispatch', component: () => import('../views/DispatchAssignment.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'WORKER'] } },
      { path: 'service', component: () => import('../views/DoorService.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'WORKER', 'OWNER'] } },
      { path: 'material', component: () => import('../views/MaterialUsage.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'WORKER', 'FINANCE'] } },
      { path: 'fee', component: () => import('../views/FeeRegistration.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'FINANCE', 'WORKER', 'OWNER'] } },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'FINANCE', 'OWNER'] } },
      { path: 'review', component: () => import('../views/SatisfactionReview.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'OWNER', 'WORKER', 'FINANCE'] } },
      { path: 'complaint', component: () => import('../views/ComplaintFollowup.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'FINANCE', 'OWNER', 'WORKER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
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
