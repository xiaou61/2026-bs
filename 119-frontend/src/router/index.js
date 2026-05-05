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
      { path: 'asset', component: () => import('../views/EquipmentAsset.vue') },
      { path: 'catalog', component: () => import('../views/SparePartCatalog.vue') },
      { path: 'stock', component: () => import('../views/SparePartStock.vue') },
      { path: 'inbound', component: () => import('../views/SparePartInbound.vue') },
      { path: 'outbound', component: () => import('../views/SparePartOutbound.vue') },
      { path: 'usage', component: () => import('../views/UsageRecord.vue') },
      { path: 'metric', component: () => import('../views/SensorMetric.vue') },
      { path: 'failure', component: () => import('../views/FailureRecord.vue') },
      { path: 'prediction', component: () => import('../views/LifePrediction.vue') },
      { path: 'plan', component: () => import('../views/MaintenancePlan.vue') },
      { path: 'purchase', component: () => import('../views/ProcurementRequest.vue') },
      { path: 'warning', component: () => import('../views/RiskWarning.vue') },
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
