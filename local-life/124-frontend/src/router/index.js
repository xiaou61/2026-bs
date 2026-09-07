import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  OPERATOR: '/station',
  MAINTAINER: '/repair',
  OWNER: '/appointment'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MAINTAINER', 'OWNER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'station', component: () => import('../views/ChargingStation.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MAINTAINER', 'OWNER'] } },
      { path: 'pile', component: () => import('../views/ChargingPile.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MAINTAINER', 'OWNER'] } },
      { path: 'vehicle', component: () => import('../views/UserVehicle.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'OWNER'] } },
      { path: 'appointment', component: () => import('../views/AppointmentOrder.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'OWNER'] } },
      { path: 'session', component: () => import('../views/ChargingSession.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'OWNER'] } },
      { path: 'fault', component: () => import('../views/FaultReport.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MAINTAINER', 'OWNER'] } },
      { path: 'repair', component: () => import('../views/RepairWorkOrder.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MAINTAINER'] } },
      { path: 'plan', component: () => import('../views/MaintenancePlan.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MAINTAINER'] } },
      { path: 'price', component: () => import('../views/ElectricityPrice.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'OWNER'] } },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'OWNER'] } },
      { path: 'revenue', component: () => import('../views/RevenueStatistic.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'OWNER'] } },
      { path: 'energy', component: () => import('../views/EnergyMonitor.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'MAINTAINER'] } },
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
