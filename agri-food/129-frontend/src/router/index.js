import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  BREEDER: '/plan',
  TECHNICIAN: '/reading',
  MANAGER: '/statistic'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'pond', component: () => import('../views/PondProfile.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'sensor', component: () => import('../views/SensorDevice.vue'), meta: { roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'reading', component: () => import('../views/WaterQualityReading.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'plan', component: () => import('../views/FeedingPlan.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'MANAGER'] } },
      { path: 'feeding', component: () => import('../views/FeedingRecord.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'MANAGER'] } },
      { path: 'batch', component: () => import('../views/FishBatch.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'MANAGER'] } },
      { path: 'sampling', component: () => import('../views/GrowthSampling.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'disease', component: () => import('../views/DiseaseWarning.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'medication', component: () => import('../views/MedicationRecord.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'equipment', component: () => import('../views/EquipmentDevice.vue'), meta: { roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'rule', component: () => import('../views/WaterAlertRule.vue'), meta: { roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] } },
      { path: 'statistic', component: () => import('../views/ProductionStatistic.vue'), meta: { roles: ['ADMIN', 'BREEDER', 'MANAGER'] } },
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
