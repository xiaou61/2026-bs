import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  ASSET_ADMIN: '/asset',
  BORROWER: '/borrow-apply',
  AUDITOR: '/inventory-record'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'asset', component: () => import('../views/AssetInfo.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN'] } },
      { path: 'category', component: () => import('../views/AssetCategory.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN'] } },
      { path: 'tag', component: () => import('../views/RfidTag.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN'] } },
      { path: 'location', component: () => import('../views/StorageLocation.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN'] } },
      { path: 'inventory-task', component: () => import('../views/InventoryTask.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'] } },
      { path: 'inventory-record', component: () => import('../views/InventoryRecord.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'] } },
      { path: 'borrow-apply', component: () => import('../views/BorrowApplication.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] } },
      { path: 'return-record', component: () => import('../views/ReturnRecord.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] } },
      { path: 'repair', component: () => import('../views/RepairRecord.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] } },
      { path: 'depreciation', component: () => import('../views/DepreciationRecord.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'] } },
      { path: 'disposal', component: () => import('../views/DisposalRecord.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'] } },
      { path: 'notice', component: () => import('../views/WarningNotice.vue'), meta: { roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] } },
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
  if (to.path === '/' && userStore.token) {
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







