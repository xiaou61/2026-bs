import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  LEGAL: '/policy',
  APPLICANT: '/claim',
  APPROVER: '/review'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'policy', component: () => import('../views/InsurancePolicy.vue'), meta: { roles: ['ADMIN', 'LEGAL'] } },
      { path: 'vehicle', component: () => import('../views/VehicleProfile.vue'), meta: { roles: ['ADMIN', 'LEGAL'] } },
      { path: 'customer', component: () => import('../views/CustomerProfile.vue'), meta: { roles: ['ADMIN', 'LEGAL'] } },
      { path: 'claim', component: () => import('../views/ClaimApplication.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
      { path: 'accident', component: () => import('../views/AccidentReport.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
      { path: 'material', component: () => import('../views/MaterialChecklist.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
      { path: 'review', component: () => import('../views/MaterialReview.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPROVER'] } },
      { path: 'assessment', component: () => import('../views/LossAssessment.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPROVER'] } },
      { path: 'compensation', component: () => import('../views/CompensationRecord.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPROVER'] } },
      { path: 'progress', component: () => import('../views/ProgressTrack.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
      { path: 'followup', component: () => import('../views/FollowUpRecord.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPROVER'] } },
      { path: 'notice', component: () => import('../views/ClaimNotice.vue'), meta: { roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] } },
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

