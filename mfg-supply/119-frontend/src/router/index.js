import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  DEVICE_ADMIN: '/asset',
  MAINTAINER: '/plan',
  ANALYST: '/prediction'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER', 'ANALYST'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'asset', component: () => import('../views/EquipmentAsset.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN'] } },
      { path: 'catalog', component: () => import('../views/SparePartCatalog.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN'] } },
      { path: 'stock', component: () => import('../views/SparePartStock.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] } },
      { path: 'inbound', component: () => import('../views/SparePartInbound.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] } },
      { path: 'outbound', component: () => import('../views/SparePartOutbound.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] } },
      { path: 'usage', component: () => import('../views/UsageRecord.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] } },
      { path: 'metric', component: () => import('../views/SensorMetric.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'ANALYST'] } },
      { path: 'failure', component: () => import('../views/FailureRecord.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] } },
      { path: 'prediction', component: () => import('../views/LifePrediction.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'ANALYST'] } },
      { path: 'plan', component: () => import('../views/MaintenancePlan.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] } },
      { path: 'purchase', component: () => import('../views/ProcurementRequest.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'ANALYST'] } },
      { path: 'warning', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'DEVICE_ADMIN', 'ANALYST', 'MAINTAINER'] } },
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
