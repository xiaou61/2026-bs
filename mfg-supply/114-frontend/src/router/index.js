import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  DISPATCHER: '/order',
  CARRIER: '/device',
  SUPERVISOR: '/responsibility'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'warehouse', component: () => import('../views/WarehouseNode.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] } },
      { path: 'carrier', component: () => import('../views/CarrierCompany.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'SUPERVISOR'] } },
      { path: 'device', component: () => import('../views/ColdDevice.vue'), meta: { roles: ['ADMIN', 'CARRIER', 'SUPERVISOR'] } },
      { path: 'cargo', component: () => import('../views/ProductCargo.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] } },
      { path: 'order', component: () => import('../views/TransportOrder.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] } },
      { path: 'temperature', component: () => import('../views/TemperatureRecord.vue'), meta: { roles: ['ADMIN', 'CARRIER', 'SUPERVISOR'] } },
      { path: 'track', component: () => import('../views/GpsTrack.vue'), meta: { roles: ['ADMIN', 'CARRIER', 'SUPERVISOR'] } },
      { path: 'rule', component: () => import('../views/AlertRule.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'SUPERVISOR'] } },
      { path: 'alert', component: () => import('../views/ExceptionAlert.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] } },
      { path: 'task', component: () => import('../views/DisposalTask.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'SUPERVISOR'] } },
      { path: 'responsibility', component: () => import('../views/ResponsibilityTrace.vue'), meta: { roles: ['ADMIN', 'SUPERVISOR'] } },
      { path: 'maintenance', component: () => import('../views/MaintenanceRecord.vue'), meta: { roles: ['ADMIN', 'CARRIER', 'SUPERVISOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'SUPERVISOR'] } }
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
