import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  OPERATOR: '/lot',
  GUARD: '/record',
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
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'lot', component: () => import('../views/ParkingLot.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
      { path: 'area', component: () => import('../views/ParkingArea.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
      { path: 'space', component: () => import('../views/ParkingSpace.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
      { path: 'sensor', component: () => import('../views/SpaceSensor.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
      { path: 'vehicle', component: () => import('../views/VehicleProfile.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD'] } },
      { path: 'reservation', component: () => import('../views/ReservationOrder.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD'] } },
      { path: 'record', component: () => import('../views/ParkingRecord.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
      { path: 'payment', component: () => import('../views/PaymentRecord.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
      { path: 'prediction', component: () => import('../views/VacancyPrediction.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'ANALYST'] } },
      { path: 'screen', component: () => import('../views/GuidanceScreen.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
      { path: 'route', component: () => import('../views/NavigationRoute.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
      { path: 'fault', component: () => import('../views/FaultReport.vue'), meta: { roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] } },
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
