import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  GROWER: '/crop',
  TECHNICIAN: '/sensor',
  MANAGER: '/dashboard'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'greenhouse', component: () => import('../views/GreenhouseProfile.vue'), meta: { roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'crop', component: () => import('../views/CropBatch.vue'), meta: { roles: ['ADMIN', 'GROWER', 'MANAGER'] } },
      { path: 'sensor', component: () => import('../views/EnvironmentSensor.vue'), meta: { roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'reading', component: () => import('../views/EnvironmentReading.vue'), meta: { roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'irrigation', component: () => import('../views/IrrigationTask.vue'), meta: { roles: ['ADMIN', 'GROWER', 'MANAGER'] } },
      { path: 'fertilizer', component: () => import('../views/FertilizerPlan.vue'), meta: { roles: ['ADMIN', 'GROWER', 'MANAGER'] } },
      { path: 'pest', component: () => import('../views/PestWarning.vue'), meta: { roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'diagnosis', component: () => import('../views/DiseaseDiagnosis.vue'), meta: { roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'device', component: () => import('../views/ControlDevice.vue'), meta: { roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'command', component: () => import('../views/RemoteCommand.vue'), meta: { roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'harvest', component: () => import('../views/HarvestRecord.vue'), meta: { roles: ['ADMIN', 'GROWER', 'MANAGER'] } },
      { path: 'ticket', component: () => import('../views/MaintenanceTicket.vue'), meta: { roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] } },
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
