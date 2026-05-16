import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  DISPATCHER: '/dashboard',
  GATEKEEPER: '/checkin',
  YARDMASTER: '/assignment',
  CARRIER: '/appointment'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'YARDMASTER', 'CARRIER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'carrier', component: () => import('../views/CarrierCompany.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'CARRIER'] } },
      { path: 'vehicle', component: () => import('../views/VehicleProfile.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'CARRIER'] } },
      { path: 'driver', component: () => import('../views/DriverProfile.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'CARRIER'] } },
      { path: 'appointment', component: () => import('../views/GateAppointment.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'CARRIER'] } },
      { path: 'slot', component: () => import('../views/TimeSlotPlan.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER'] } },
      { path: 'checkin', component: () => import('../views/GateCheckin.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER'] } },
      { path: 'queue', component: () => import('../views/QueueTicket.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'YARDMASTER', 'CARRIER'] } },
      { path: 'dock', component: () => import('../views/DockDoor.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER'] } },
      { path: 'assignment', component: () => import('../views/DockAssignment.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER', 'CARRIER'] } },
      { path: 'loading', component: () => import('../views/LoadingTask.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER', 'CARRIER'] } },
      { path: 'turnaround', component: () => import('../views/TurnaroundRecord.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'DISPATCHER'] } }
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
