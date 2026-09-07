import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  WAREHOUSE: '/warehouse',
  CHECKER: '/check',
  APPLICANT: '/requisition',
  DISPATCH: '/dispatch',
  AUDITOR: '/approval'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'APPLICANT', 'DISPATCH', 'AUDITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'warehouse', component: () => import('../views/ReserveWarehouse.vue'), meta: { roles: ['ADMIN', 'WAREHOUSE', 'DISPATCH', 'AUDITOR'] } },
      { path: 'category', component: () => import('../views/MaterialCategory.vue'), meta: { roles: ['ADMIN', 'WAREHOUSE', 'DISPATCH', 'AUDITOR'] } },
      { path: 'material', component: () => import('../views/MaterialLedger.vue'), meta: { roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'DISPATCH', 'APPLICANT', 'AUDITOR'] } },
      { path: 'batch', component: () => import('../views/StockBatch.vue'), meta: { roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'DISPATCH', 'AUDITOR'] } },
      { path: 'check', component: () => import('../views/InventoryCheck.vue'), meta: { roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'AUDITOR'] } },
      { path: 'difference', component: () => import('../views/CheckDifference.vue'), meta: { roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'AUDITOR'] } },
      { path: 'requisition', component: () => import('../views/RequisitionOrder.vue'), meta: { roles: ['ADMIN', 'APPLICANT', 'DISPATCH', 'AUDITOR', 'WAREHOUSE'] } },
      { path: 'approval', component: () => import('../views/AllocationApproval.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'AUDITOR', 'WAREHOUSE', 'APPLICANT'] } },
      { path: 'dispatch', component: () => import('../views/DispatchTask.vue'), meta: { roles: ['ADMIN', 'DISPATCH', 'WAREHOUSE', 'APPLICANT', 'AUDITOR'] } },
      { path: 'outbound', component: () => import('../views/OutboundRecord.vue'), meta: { roles: ['ADMIN', 'WAREHOUSE', 'DISPATCH', 'AUDITOR'] } },
      { path: 'warning', component: () => import('../views/StockWarning.vue'), meta: { roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'DISPATCH', 'AUDITOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'AUDITOR'] } }
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
