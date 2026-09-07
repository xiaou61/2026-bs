import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  INSPECTOR: '/task',
  ENGINEER: '/device',
  MANAGER: '/building'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'ENGINEER', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'building', component: () => import('../views/ParkBuilding.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } },
      { path: 'device', component: () => import('../views/TwinDevice.vue'), meta: { roles: ['ADMIN', 'ENGINEER', 'MANAGER'] } },
      { path: 'route', component: () => import('../views/InspectionRoute.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] } },
      { path: 'point', component: () => import('../views/InspectionPoint.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] } },
      { path: 'task', component: () => import('../views/InspectionTask.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] } },
      { path: 'record', component: () => import('../views/InspectionRecord.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] } },
      { path: 'defect', component: () => import('../views/DefectReport.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] } },
      { path: 'workOrder', component: () => import('../views/WorkOrder.vue'), meta: { roles: ['ADMIN', 'ENGINEER', 'MANAGER'] } },
      { path: 'sensor', component: () => import('../views/SensorData.vue'), meta: { roles: ['ADMIN', 'ENGINEER', 'MANAGER'] } },
      { path: 'model', component: () => import('../views/TwinModel.vue'), meta: { roles: ['ADMIN', 'ENGINEER', 'MANAGER'] } },
      { path: 'energy', component: () => import('../views/EnergyMonitor.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } },
      { path: 'schedule', component: () => import('../views/MaintenanceSchedule.vue'), meta: { roles: ['ADMIN', 'ENGINEER', 'MANAGER'] } },
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
