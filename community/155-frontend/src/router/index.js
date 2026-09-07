import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  SECRETARY: '/dashboard',
  ORGANIZER: '/activity',
  VOLUNTEER: '/task',
  RESIDENT: '/registration'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'branch', component: () => import('../views/PartyBranch.vue'), meta: { roles: ['ADMIN', 'SECRETARY'] } },
      { path: 'member', component: () => import('../views/MemberProfile.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'RESIDENT'] } },
      { path: 'activity', component: () => import('../views/PartyActivity.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'ORGANIZER'] } },
      { path: 'registration', component: () => import('../views/ActivityRegistration.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'attendance', component: () => import('../views/AttendanceRecord.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'VOLUNTEER'] } },
      { path: 'task', component: () => import('../views/VolunteerTask.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'VOLUNTEER'] } },
      { path: 'points', component: () => import('../views/PointRecord.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'exchange', component: () => import('../views/PointExchange.vue'), meta: { roles: ['ADMIN', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'transfer', component: () => import('../views/OrganizationTransfer.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'RESIDENT'] } },
      { path: 'ranking', component: () => import('../views/HonorRanking.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] } },
      { path: 'notice', component: () => import('../views/NoticeBoard.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'ORGANIZER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'SECRETARY'] } }
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
