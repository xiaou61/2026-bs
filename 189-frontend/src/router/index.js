import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  STATION: '/station',
  DISPATCH: '/dispatch',
  DRIVER: '/workorder',
  MECHANIC: '/maintenance',
  FARMER: '/booking'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'STATION', 'DISPATCH', 'DRIVER', 'MECHANIC', 'FARMER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'station', component: () => import('../views/ServiceStation.vue'), meta: { roles: ['ADMIN', 'STATION'] } },
      { path: 'machine', component: () => import('../views/MachineAsset.vue'), meta: { roles: ['ADMIN', 'STATION', 'MECHANIC'] } },
      { path: 'farmer', component: () => import('../views/FarmerProfile.vue'), meta: { roles: ['ADMIN', 'STATION', 'DISPATCH', 'FARMER'] } },
      { path: 'driver', component: () => import('../views/DriverProfile.vue'), meta: { roles: ['ADMIN', 'STATION', 'DISPATCH', 'DRIVER'] } },
      { path: 'booking', component: () => import('../views/OperationBooking.vue'), meta: { roles: ['ADMIN', 'STATION', 'DISPATCH', 'FARMER'] } },
      { path: 'dispatch', component: () => import('../views/DispatchSchedule.vue'), meta: { roles: ['ADMIN', 'STATION', 'DISPATCH', 'DRIVER'] } },
      { path: 'workorder', component: () => import('../views/FieldWorkOrder.vue'), meta: { roles: ['ADMIN', 'STATION', 'DISPATCH', 'DRIVER', 'FARMER'] } },
      { path: 'completion', component: () => import('../views/WorkCompletion.vue'), meta: { roles: ['ADMIN', 'STATION', 'DRIVER', 'FARMER'] } },
      { path: 'maintenance', component: () => import('../views/MaintenanceRecord.vue'), meta: { roles: ['ADMIN', 'STATION', 'MECHANIC', 'DRIVER'] } },
      { path: 'repair', component: () => import('../views/RepairFollowup.vue'), meta: { roles: ['ADMIN', 'STATION', 'MECHANIC', 'DRIVER'] } },
      { path: 'season', component: () => import('../views/SeasonalStatistics.vue'), meta: { roles: ['ADMIN', 'STATION', 'DISPATCH'] } },
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
