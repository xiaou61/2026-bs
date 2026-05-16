import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  TEACHER: '/case',
  STUDENT: '/appointment',
  COUNSELOR: '/risk'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'COUNSELOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'case', component: () => import('../views/CounselCase.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] } },
      { path: 'room', component: () => import('../views/CounselRoom.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] } },
      { path: 'student', component: () => import('../views/StudentProfile.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] } },
      { path: 'duty', component: () => import('../views/DutySchedule.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] } },
      { path: 'appointment', component: () => import('../views/AppointmentRequest.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'COUNSELOR'] } },
      { path: 'record', component: () => import('../views/CounselRecord.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] } },
      { path: 'questionnaire', component: () => import('../views/AssessmentQuestionnaire.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT'] } },
      { path: 'risk', component: () => import('../views/RiskAssessment.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] } },
      { path: 'intervention', component: () => import('../views/CrisisIntervention.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] } },
      { path: 'family', component: () => import('../views/FamilyCommunication.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] } },
      { path: 'followup', component: () => import('../views/FollowUpPlan.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] } },
      { path: 'notice', component: () => import('../views/SystemNotice.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'COUNSELOR'] } },
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










