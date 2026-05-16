import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  RESIDENT: '/booking',
  VOLUNTEER: '/checkin',
  MANAGER: '/project'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'project', component: () => import('../views/ServiceProject.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] } },
      { path: 'category', component: () => import('../views/ServiceCategory.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] } },
      { path: 'resident', component: () => import('../views/ResidentProfile.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } },
      { path: 'volunteer', component: () => import('../views/VolunteerProfile.vue'), meta: { roles: ['ADMIN', 'VOLUNTEER', 'MANAGER'] } },
      { path: 'booking', component: () => import('../views/ServiceBooking.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'MANAGER'] } },
      { path: 'checkin', component: () => import('../views/ServiceCheckin.vue'), meta: { roles: ['ADMIN', 'VOLUNTEER', 'MANAGER'] } },
      { path: 'account', component: () => import('../views/TimeAccount.vue'), meta: { roles: ['ADMIN', 'MANAGER'] } },
      { path: 'exchange', component: () => import('../views/TimeExchange.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] } },
      { path: 'feedback', component: () => import('../views/FeedbackReview.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] } },
      { path: 'activity', component: () => import('../views/CommunityActivity.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] } },
      { path: 'rule', component: () => import('../views/PointRule.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] } },
      { path: 'notice', component: () => import('../views/SiteNotice.vue'), meta: { roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] } },
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
