import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  MANAGER: '/conference',
  REVIEWER: '/review',
  SECRETARY: '/agenda'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'REVIEWER', 'SECRETARY'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'conference', component: () => import('../views/ConferenceInfo.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'REVIEWER', 'SECRETARY'] } },
      { path: 'call', component: () => import('../views/CallForPaper.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'REVIEWER', 'SECRETARY'] } },
      { path: 'author', component: () => import('../views/AuthorProfile.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'REVIEWER', 'SECRETARY'] } },
      { path: 'paper', component: () => import('../views/PaperSubmission.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'REVIEWER'] } },
      { path: 'reviewer', component: () => import('../views/ReviewerProfile.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'REVIEWER'] } },
      { path: 'assignment', component: () => import('../views/ReviewAssignment.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } },
      { path: 'review', component: () => import('../views/BlindReview.vue'), meta: { roles: ['ADMIN', 'REVIEWER'] } },
      { path: 'notice', component: () => import('../views/AcceptanceNotice.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'SECRETARY'] } },
      { path: 'guest', component: () => import('../views/GuestRegistration.vue'), meta: { roles: ['ADMIN', 'MANAGER', 'SECRETARY'] } },
      { path: 'venue', component: () => import('../views/VenueRoom.vue'), meta: { roles: ['ADMIN', 'SECRETARY'] } },
      { path: 'agenda', component: () => import('../views/AgendaSchedule.vue'), meta: { roles: ['ADMIN', 'SECRETARY'] } },
      { path: 'checkin', component: () => import('../views/CheckinRecord.vue'), meta: { roles: ['ADMIN', 'SECRETARY'] } },
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


