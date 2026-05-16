import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  PARKING: '/lot',
  FINANCE: '/payment',
  PATROL: '/check',
  TENANT: '/contract',
  SERVICE: '/ticket'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'PARKING', 'FINANCE', 'PATROL', 'TENANT', 'SERVICE'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'lot', component: () => import('../views/ParkingLot.vue'), meta: { roles: ['ADMIN', 'PARKING', 'PATROL'] } },
      { path: 'space', component: () => import('../views/ParkingSpace.vue'), meta: { roles: ['ADMIN', 'PARKING', 'PATROL'] } },
      { path: 'tenant', component: () => import('../views/TenantProfile.vue'), meta: { roles: ['ADMIN', 'PARKING', 'TENANT', 'SERVICE'] } },
      { path: 'contract', component: () => import('../views/LeaseContract.vue'), meta: { roles: ['ADMIN', 'PARKING', 'FINANCE', 'TENANT', 'SERVICE'] } },
      { path: 'reminder', component: () => import('../views/RenewalReminder.vue'), meta: { roles: ['ADMIN', 'PARKING', 'FINANCE', 'TENANT', 'SERVICE'] } },
      { path: 'payment', component: () => import('../views/RenewalPayment.vue'), meta: { roles: ['ADMIN', 'PARKING', 'FINANCE', 'TENANT'] } },
      { path: 'vehicle', component: () => import('../views/VehicleBind.vue'), meta: { roles: ['ADMIN', 'PARKING', 'PATROL', 'TENANT'] } },
      { path: 'check', component: () => import('../views/OccupancyCheck.vue'), meta: { roles: ['ADMIN', 'PARKING', 'PATROL'] } },
      { path: 'abnormal', component: () => import('../views/AbnormalOccupancy.vue'), meta: { roles: ['ADMIN', 'PARKING', 'PATROL', 'TENANT', 'SERVICE'] } },
      { path: 'handling', component: () => import('../views/ViolationHandling.vue'), meta: { roles: ['ADMIN', 'PARKING', 'PATROL', 'TENANT', 'SERVICE'] } },
      { path: 'ticket', component: () => import('../views/ServiceTicket.vue'), meta: { roles: ['ADMIN', 'PARKING', 'TENANT', 'SERVICE'] } },
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
