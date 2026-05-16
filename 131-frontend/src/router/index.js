import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  REPORTER: '/report',
  REVIEWER: '/review',
  DOCTOR: '/followup'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'patient', component: () => import('../views/PatientProfile.vue'), meta: { roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] } },
      { path: 'drug', component: () => import('../views/DrugCatalog.vue'), meta: { roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] } },
      { path: 'reporter', component: () => import('../views/ReporterProfile.vue'), meta: { roles: ['ADMIN', 'REPORTER'] } },
      { path: 'report', component: () => import('../views/AdverseReport.vue'), meta: { roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] } },
      { path: 'symptom', component: () => import('../views/ReactionSymptom.vue'), meta: { roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] } },
      { path: 'risk', component: () => import('../views/RiskAssessment.vue'), meta: { roles: ['ADMIN', 'REVIEWER', 'DOCTOR'] } },
      { path: 'plan', component: () => import('../views/FollowupPlan.vue'), meta: { roles: ['ADMIN', 'DOCTOR'] } },
      { path: 'followup', component: () => import('../views/FollowupRecord.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'REVIEWER'] } },
      { path: 'review', component: () => import('../views/CaseReview.vue'), meta: { roles: ['ADMIN', 'REVIEWER'] } },
      { path: 'advice', component: () => import('../views/TreatmentAdvice.vue'), meta: { roles: ['ADMIN', 'DOCTOR'] } },
      { path: 'department', component: () => import('../views/DepartmentInfo.vue'), meta: { roles: ['ADMIN', 'REVIEWER'] } },
      { path: 'statistic', component: () => import('../views/StatisticsReport.vue'), meta: { roles: ['ADMIN', 'REVIEWER', 'DOCTOR'] } },
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
