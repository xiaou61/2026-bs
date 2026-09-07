import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  SECURITY: '/template',
  TRAINER: '/course',
  AUDITOR: '/send-record'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'employee', component: () => import('../views/EmployeeProfile.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] } },
      { path: 'department', component: () => import('../views/DepartmentGroup.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] } },
      { path: 'template', component: () => import('../views/MailTemplate.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'campaign', component: () => import('../views/PhishingCampaign.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'target', component: () => import('../views/CampaignTarget.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'AUDITOR'] } },
      { path: 'send-record', component: () => import('../views/MailSendRecord.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] } },
      { path: 'click', component: () => import('../views/ClickTracking.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] } },
      { path: 'course', component: () => import('../views/TrainingCourse.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'AUDITOR'] } },
      { path: 'exam', component: () => import('../views/TrainingExam.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'AUDITOR'] } },
      { path: 'question', component: () => import('../views/ExamQuestion.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'AUDITOR'] } },
      { path: 'attempt', component: () => import('../views/ExamAttempt.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] } },
      { path: 'risk-score', component: () => import('../views/RiskScore.vue'), meta: { roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'AUDITOR'] } }
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
