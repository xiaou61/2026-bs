import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  INVIGILATOR: '/session',
  CANDIDATE: '/appeal',
  REVIEWER: '/task'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'plan', component: () => import('../views/ExamPlan.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE'] } },
      { path: 'invigilator', component: () => import('../views/InvigilatorProfile.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR'] } },
      { path: 'candidate', component: () => import('../views/CandidateProfile.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] } },
      { path: 'session', component: () => import('../views/ExamSession.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] } },
      { path: 'behavior', component: () => import('../views/SuspiciousBehavior.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] } },
      { path: 'evidence', component: () => import('../views/EvidenceRecord.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] } },
      { path: 'task', component: () => import('../views/ReviewTask.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'REVIEWER'] } },
      { path: 'decision', component: () => import('../views/ReviewDecision.vue'), meta: { roles: ['ADMIN', 'REVIEWER'] } },
      { path: 'rule', component: () => import('../views/WarningRule.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'REVIEWER'] } },
      { path: 'device', component: () => import('../views/DeviceMonitor.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'REVIEWER'] } },
      { path: 'appeal', component: () => import('../views/ViolationAppeal.vue'), meta: { roles: ['ADMIN', 'CANDIDATE', 'REVIEWER'] } },
      { path: 'notice', component: () => import('../views/AlertNotification.vue'), meta: { roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] } },
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




