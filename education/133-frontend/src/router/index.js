import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  KEEPER: '/stock',
  TEACHER: '/request',
  APPROVER: '/approval'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'catalog', component: () => import('../views/ConsumableCatalog.vue'), meta: { roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] } },
      { path: 'supplier', component: () => import('../views/SupplierProfile.vue'), meta: { roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] } },
      { path: 'lab', component: () => import('../views/LabRoom.vue'), meta: { roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] } },
      { path: 'stock', component: () => import('../views/StockItem.vue'), meta: { roles: ['ADMIN', 'KEEPER', 'APPROVER'] } },
      { path: 'request', component: () => import('../views/PurchaseRequest.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'APPROVER'] } },
      { path: 'approval', component: () => import('../views/PurchaseApproval.vue'), meta: { roles: ['ADMIN', 'APPROVER'] } },
      { path: 'order', component: () => import('../views/PurchaseOrder.vue'), meta: { roles: ['ADMIN', 'KEEPER', 'APPROVER'] } },
      { path: 'inbound', component: () => import('../views/InboundRecord.vue'), meta: { roles: ['ADMIN', 'KEEPER', 'APPROVER'] } },
      { path: 'outbound', component: () => import('../views/OutboundRecord.vue'), meta: { roles: ['ADMIN', 'KEEPER', 'APPROVER'] } },
      { path: 'check', component: () => import('../views/InventoryCheck.vue'), meta: { roles: ['ADMIN', 'KEEPER'] } },
      { path: 'rule', component: () => import('../views/WarningRule.vue'), meta: { roles: ['ADMIN', 'KEEPER'] } },
      { path: 'warning', component: () => import('../views/StockWarning.vue'), meta: { roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] } },
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
