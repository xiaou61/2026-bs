import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  PILOT: '/task',
  ENGINEER: '/drone',
  MANAGER: '/warning'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'PILOT', 'ENGINEER', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'drone', component: () => import('../views/DroneDevice.vue'), meta: { roles: ['ADMIN', 'PILOT', 'ENGINEER'] } },
      { path: 'pilot', component: () => import('../views/PilotProfile.vue'), meta: { roles: ['ADMIN', 'PILOT', 'ENGINEER'] } },
      { path: 'zone', component: () => import('../views/InspectionZone.vue'), meta: { roles: ['ADMIN', 'PILOT', 'ENGINEER'] } },
      { path: 'route', component: () => import('../views/FlightRoute.vue'), meta: { roles: ['ADMIN', 'PILOT', 'ENGINEER'] } },
      { path: 'task', component: () => import('../views/InspectionTask.vue'), meta: { roles: ['ADMIN', 'PILOT', 'ENGINEER'] } },
      { path: 'flight', component: () => import('../views/FlightRecord.vue'), meta: { roles: ['ADMIN', 'PILOT', 'ENGINEER'] } },
      { path: 'defect', component: () => import('../views/DefectReport.vue'), meta: { roles: ['ADMIN', 'PILOT', 'ENGINEER'] } },
      { path: 'image', component: () => import('../views/DefectImage.vue'), meta: { roles: ['ADMIN', 'PILOT', 'ENGINEER'] } },
      { path: 'rectify', component: () => import('../views/RectificationOrder.vue'), meta: { roles: ['ADMIN', 'ENGINEER', 'MANAGER'] } },
      { path: 'station', component: () => import('../views/BatteryStation.vue'), meta: { roles: ['ADMIN', 'ENGINEER', 'MANAGER'] } },
      { path: 'maintenance', component: () => import('../views/MaintenanceRecord.vue'), meta: { roles: ['ADMIN', 'ENGINEER', 'MANAGER'] } },
      { path: 'warning', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'ENGINEER', 'MANAGER'] } },
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
