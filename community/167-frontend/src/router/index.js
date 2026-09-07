import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  COMMUNITY: '/area',
  SUPERVISOR: '/supervision',
  RESIDENT: '/checkin',
  VOLUNTEER: '/correction',
  EXCHANGE: '/exchange'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT', 'VOLUNTEER', 'EXCHANGE'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'area', component: () => import('../views/CommunityArea.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR'] } },
      { path: 'building', component: () => import('../views/BuildingProfile.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER'] } },
      { path: 'resident', component: () => import('../views/ResidentProfile.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT'] } },
      { path: 'category', component: () => import('../views/SortingCategory.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'checkin', component: () => import('../views/SortingCheckin.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT', 'VOLUNTEER'] } },
      { path: 'supervision', component: () => import('../views/SupervisionRecord.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER'] } },
      { path: 'correction', component: () => import('../views/CorrectionTask.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'points', component: () => import('../views/PointsLedger.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT', 'EXCHANGE'] } },
      { path: 'reward', component: () => import('../views/RewardCatalog.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'EXCHANGE', 'RESIDENT'] } },
      { path: 'exchange', component: () => import('../views/ExchangeOrder.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'EXCHANGE', 'RESIDENT'] } },
      { path: 'ranking', component: () => import('../views/BuildingRanking.vue'), meta: { roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'COMMUNITY'] } }
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
