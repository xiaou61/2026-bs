import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  MONITOR: '/waterdata',
  DISPATCHER: '/task',
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
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'MONITOR', 'DISPATCHER', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'point', component: () => import('../views/WaterLevelPoint.vue'), meta: { roles: ['ADMIN', 'MONITOR', 'MANAGER'] } },
      { path: 'rainstation', component: () => import('../views/RainGaugeStation.vue'), meta: { roles: ['ADMIN', 'MONITOR', 'MANAGER'] } },
      { path: 'pump', component: () => import('../views/DrainagePump.vue'), meta: { roles: ['ADMIN', 'MONITOR', 'DISPATCHER', 'MANAGER'] } },
      { path: 'waterdata', component: () => import('../views/WaterLevelData.vue'), meta: { roles: ['ADMIN', 'MONITOR', 'MANAGER'] } },
      { path: 'raindata', component: () => import('../views/RainfallData.vue'), meta: { roles: ['ADMIN', 'MONITOR', 'MANAGER'] } },
      { path: 'rule', component: () => import('../views/WarningRule.vue'), meta: { roles: ['ADMIN', 'MONITOR', 'MANAGER'] } },
      { path: 'warning', component: () => import('../views/FloodWarning.vue'), meta: { roles: ['ADMIN', 'MONITOR', 'DISPATCHER', 'MANAGER'] } },
      { path: 'plan', component: () => import('../views/EmergencyPlan.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] } },
      { path: 'task', component: () => import('../views/DispatchTask.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] } },
      { path: 'team', component: () => import('../views/RescueTeam.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] } },
      { path: 'material', component: () => import('../views/MaterialReserve.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] } },
      { path: 'shelter', component: () => import('../views/ShelterSite.vue'), meta: { roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] } },
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
