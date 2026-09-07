import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  PARK: '/enterprise',
  WAREHOUSE: '/inbound',
  TRANSPORT: '/transfer',
  INSPECTOR: '/audit',
  ENTERPRISE: '/inbound'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'TRANSPORT', 'INSPECTOR', 'ENTERPRISE'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'enterprise', component: () => import('../views/ParkEnterprise.vue'), meta: { roles: ['ADMIN', 'PARK', 'ENTERPRISE'] } },
      { path: 'waste', component: () => import('../views/WasteCategory.vue'), meta: { roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'ENTERPRISE'] } },
      { path: 'facility', component: () => import('../views/StorageFacility.vue'), meta: { roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'INSPECTOR'] } },
      { path: 'inbound', component: () => import('../views/StorageInbound.vue'), meta: { roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'ENTERPRISE'] } },
      { path: 'check', component: () => import('../views/StorageCheck.vue'), meta: { roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'INSPECTOR'] } },
      { path: 'transfer', component: () => import('../views/TransferOrder.vue'), meta: { roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'TRANSPORT', 'ENTERPRISE'] } },
      { path: 'dispatch', component: () => import('../views/VehicleDispatch.vue'), meta: { roles: ['ADMIN', 'PARK', 'TRANSPORT'] } },
      { path: 'weighing', component: () => import('../views/WeighingRecord.vue'), meta: { roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'TRANSPORT'] } },
      { path: 'handover', component: () => import('../views/DisposalHandover.vue'), meta: { roles: ['ADMIN', 'PARK', 'TRANSPORT', 'INSPECTOR'] } },
      { path: 'warning', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'INSPECTOR'] } },
      { path: 'audit', component: () => import('../views/LedgerAudit.vue'), meta: { roles: ['ADMIN', 'PARK', 'INSPECTOR'] } },
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
