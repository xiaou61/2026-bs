import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  MANAGER: '/store',
  CLERK: '/batch',
  STOCK: '/warning',
  MARKETING: '/promotion',
  SUPPLIER: '/supplier'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'CLERK', 'STOCK', 'MARKETING', 'SUPPLIER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'store', component: () => import('../views/StoreProfile.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } },
      { path: 'supplier', component: () => import('../views/SupplierProfile.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'SUPPLIER'] } },
      { path: 'category', component: () => import('../views/FreshCategory.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'STOCK'] } },
      { path: 'batch', component: () => import('../views/ProductBatch.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'CLERK', 'STOCK'] } },
      { path: 'rule', component: () => import('../views/ShelfLifeRule.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'STOCK'] } },
      { path: 'warning', component: () => import('../views/ExpiryWarning.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'CLERK', 'STOCK'] } },
      { path: 'promotion', component: () => import('../views/PromotionStrategy.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'MARKETING'] } },
      { path: 'discount', component: () => import('../views/DiscountExecution.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'CLERK', 'MARKETING'] } },
      { path: 'loss', component: () => import('../views/LossReport.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'CLERK', 'STOCK'] } },
      { path: 'turnover', component: () => import('../views/InventoryTurnover.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'STOCK'] } },
      { path: 'analysis', component: () => import('../views/StoreAnalysis.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'MARKETING'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } }
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
