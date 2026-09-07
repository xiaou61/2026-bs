import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  LOGISTICS: '/dashboard',
  COUNSELOR: '/alert',
  ENERGY: '/meter',
  STUDENT: '/ranking'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'building', component: () => import('../views/DormBuilding.vue'), meta: { roles: ['ADMIN', 'LOGISTICS'] } },
      { path: 'room', component: () => import('../views/DormRoom.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'STUDENT'] } },
      { path: 'meter', component: () => import('../views/MeterDevice.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'ENERGY'] } },
      { path: 'reading', component: () => import('../views/EnergyReading.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'ENERGY', 'STUDENT'] } },
      { path: 'bill', component: () => import('../views/ConsumptionBill.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'ENERGY', 'STUDENT'] } },
      { path: 'rule', component: () => import('../views/AlertRule.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'ENERGY'] } },
      { path: 'alert', component: () => import('../views/AbnormalAlert.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY'] } },
      { path: 'task', component: () => import('../views/SavingTask.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT'] } },
      { path: 'ranking', component: () => import('../views/SavingRanking.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT'] } },
      { path: 'inspection', component: () => import('../views/InspectionRecord.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY'] } },
      { path: 'notice', component: () => import('../views/EnergyNotice.vue'), meta: { roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'LOGISTICS'] } }
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
