import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  TRAVELER: '/request',
  VOLUNTEER: '/task',
  DISPATCHER: '/route'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'route', component: () => import('../views/AccessibleRoute.vue'), meta: { roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] } },
      { path: 'facility', component: () => import('../views/FacilityPoint.vue'), meta: { roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] } },
      { path: 'traveler', component: () => import('../views/TravelerProfile.vue'), meta: { roles: ['ADMIN', 'TRAVELER', 'DISPATCHER'] } },
      { path: 'request', component: () => import('../views/AssistRequest.vue'), meta: { roles: ['ADMIN', 'TRAVELER', 'DISPATCHER'] } },
      { path: 'volunteer', component: () => import('../views/VolunteerProfile.vue'), meta: { roles: ['ADMIN', 'VOLUNTEER', 'DISPATCHER'] } },
      { path: 'plan', component: () => import('../views/RoutePlan.vue'), meta: { roles: ['ADMIN', 'DISPATCHER'] } },
      { path: 'task', component: () => import('../views/AssistTask.vue'), meta: { roles: ['ADMIN', 'VOLUNTEER', 'DISPATCHER'] } },
      { path: 'checkin', component: () => import('../views/ServiceCheckin.vue'), meta: { roles: ['ADMIN', 'VOLUNTEER', 'DISPATCHER'] } },
      { path: 'feedback', component: () => import('../views/FeedbackReview.vue'), meta: { roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] } },
      { path: 'contact', component: () => import('../views/EmergencyContact.vue'), meta: { roles: ['ADMIN', 'TRAVELER'] } },
      { path: 'trip', component: () => import('../views/TripRecord.vue'), meta: { roles: ['ADMIN', 'TRAVELER', 'DISPATCHER'] } },
      { path: 'notice', component: () => import('../views/MessageNotice.vue'), meta: { roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] } },
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
  if (to.path === '/' && userStore.token) {
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
