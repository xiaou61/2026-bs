import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  OPERATOR: '/site',
  SITE: '/cabinet',
  INSPECTOR: '/inspection',
  FINANCE: '/settlement',
  MERCHANT: '/income'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR', 'FINANCE', 'MERCHANT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'site', component: () => import('../views/PlacementSite.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SITE', 'MERCHANT'] } },
      { path: 'cabinet', component: () => import('../views/CabinetProfile.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR'] } },
      { path: 'device', component: () => import('../views/PowerBankDevice.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR'] } },
      { path: 'plan', component: () => import('../views/PlacementPlan.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'SITE'] } },
      { path: 'inspection', component: () => import('../views/InspectionTask.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE'] } },
      { path: 'repair', component: () => import('../views/FaultRepair.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE'] } },
      { path: 'recycle', component: () => import('../views/AbnormalRecycle.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE'] } },
      { path: 'order', component: () => import('../views/LeaseOrder.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT'] } },
      { path: 'income', component: () => import('../views/MerchantIncome.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT'] } },
      { path: 'settlement', component: () => import('../views/SettlementRecord.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT'] } },
      { path: 'transfer', component: () => import('../views/InventoryTransfer.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'INSPECTOR'] } },
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
