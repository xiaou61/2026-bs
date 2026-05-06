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
      { path: 'asset', component: () => import('../views/AssetInfo.vue') },
      { path: 'category', component: () => import('../views/AssetCategory.vue') },
      { path: 'tag', component: () => import('../views/RfidTag.vue') },
      { path: 'location', component: () => import('../views/StorageLocation.vue') },
      { path: 'inventory-task', component: () => import('../views/InventoryTask.vue') },
      { path: 'inventory-record', component: () => import('../views/InventoryRecord.vue') },
      { path: 'borrow-apply', component: () => import('../views/BorrowApplication.vue') },
      { path: 'return-record', component: () => import('../views/ReturnRecord.vue') },
      { path: 'repair', component: () => import('../views/RepairRecord.vue') },
      { path: 'depreciation', component: () => import('../views/DepreciationRecord.vue') },
      { path: 'disposal', component: () => import('../views/DisposalRecord.vue') },
      { path: 'notice', component: () => import('../views/WarningNotice.vue') },
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







