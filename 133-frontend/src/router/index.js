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
      { path: 'catalog', component: () => import('../views/ConsumableCatalog.vue') },
      { path: 'supplier', component: () => import('../views/SupplierProfile.vue') },
      { path: 'lab', component: () => import('../views/LabRoom.vue') },
      { path: 'stock', component: () => import('../views/StockItem.vue') },
      { path: 'request', component: () => import('../views/PurchaseRequest.vue') },
      { path: 'approval', component: () => import('../views/PurchaseApproval.vue') },
      { path: 'order', component: () => import('../views/PurchaseOrder.vue') },
      { path: 'inbound', component: () => import('../views/InboundRecord.vue') },
      { path: 'outbound', component: () => import('../views/OutboundRecord.vue') },
      { path: 'check', component: () => import('../views/InventoryCheck.vue') },
      { path: 'rule', component: () => import('../views/WarningRule.vue') },
      { path: 'warning', component: () => import('../views/StockWarning.vue') },
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
