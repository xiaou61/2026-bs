import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  SAFETY: '/dashboard',
  APPROVER: '/approval',
  GUARDIAN: '/monitor',
  WORKER: '/work-ticket'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'area', component: () => import('../views/WorkArea.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'hazard', component: () => import('../views/HazardSource.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'worker', component: () => import('../views/WorkerProfile.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'work-ticket', component: () => import('../views/WorkPermit.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'approval', component: () => import('../views/PermitApproval.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'briefing', component: () => import('../views/SafetyBriefing.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'guardian', component: () => import('../views/GuardianAssignment.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'monitor', component: () => import('../views/MonitorRecord.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'danger', component: () => import('../views/HiddenDanger.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'gas', component: () => import('../views/GasDetection.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'plan', component: () => import('../views/EmergencyPlan.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] } }
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
