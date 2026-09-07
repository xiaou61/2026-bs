import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CITIZEN: '/complaint',
  OFFICER: '/task',
  SUPERVISOR: '/site'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CITIZEN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'complaint', component: () => import('../views/ComplaintTicket.vue'), meta: { roles: ['ADMIN', 'CITIZEN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'site', component: () => import('../views/MonitoringSite.vue'), meta: { roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'source', component: () => import('../views/NoiseSource.vue'), meta: { roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'officer', component: () => import('../views/OfficerProfile.vue'), meta: { roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'task', component: () => import('../views/HandlingTask.vue'), meta: { roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'patrol', component: () => import('../views/PatrolRecord.vue'), meta: { roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'rectify', component: () => import('../views/RectificationNotice.vue'), meta: { roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'retest', component: () => import('../views/RetestRecord.vue'), meta: { roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'penalty', component: () => import('../views/PenaltyDecision.vue'), meta: { roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] } },
      { path: 'feedback', component: () => import('../views/PublicFeedback.vue'), meta: { roles: ['ADMIN', 'CITIZEN', 'SUPERVISOR'] } },
      { path: 'rule', component: () => import('../views/WarningRule.vue'), meta: { roles: ['ADMIN', 'SUPERVISOR'] } },
      { path: 'notice', component: () => import('../views/PublicNotice.vue'), meta: { roles: ['ADMIN', 'CITIZEN', 'OFFICER', 'SUPERVISOR'] } },
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
