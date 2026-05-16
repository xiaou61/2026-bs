import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  LAB: '/room',
  BREEDER: '/schedule',
  RESEARCHER: '/ethics',
  ETHICS: '/review',
  VET: '/health'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER', 'ETHICS', 'VET'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'room', component: () => import('../views/AnimalRoom.vue'), meta: { roles: ['ADMIN', 'LAB', 'BREEDER', 'VET'] } },
      { path: 'animal', component: () => import('../views/AnimalProfile.vue'), meta: { roles: ['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER', 'VET'] } },
      { path: 'schedule', component: () => import('../views/FeedingSchedule.vue'), meta: { roles: ['ADMIN', 'LAB', 'BREEDER'] } },
      { path: 'feeding', component: () => import('../views/FeedingRecord.vue'), meta: { roles: ['ADMIN', 'LAB', 'BREEDER', 'VET'] } },
      { path: 'ethics', component: () => import('../views/EthicsApplication.vue'), meta: { roles: ['ADMIN', 'LAB', 'RESEARCHER', 'ETHICS'] } },
      { path: 'review', component: () => import('../views/EthicsReview.vue'), meta: { roles: ['ADMIN', 'ETHICS', 'RESEARCHER'] } },
      { path: 'experiment', component: () => import('../views/ExperimentRegistration.vue'), meta: { roles: ['ADMIN', 'LAB', 'RESEARCHER', 'ETHICS'] } },
      { path: 'health', component: () => import('../views/HealthInspection.vue'), meta: { roles: ['ADMIN', 'LAB', 'BREEDER', 'VET'] } },
      { path: 'alert', component: () => import('../views/AbnormalAlert.vue'), meta: { roles: ['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER', 'VET'] } },
      { path: 'treatment', component: () => import('../views/VeterinaryTreatment.vue'), meta: { roles: ['ADMIN', 'LAB', 'BREEDER', 'VET'] } },
      { path: 'material', component: () => import('../views/MaterialUsage.vue'), meta: { roles: ['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER'] } },
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
