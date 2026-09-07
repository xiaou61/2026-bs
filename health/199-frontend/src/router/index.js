import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  CENTER: '/center',
  ASSESSOR: '/assessment',
  COACH: '/plan',
  THERAPIST: '/feedback',
  MEMBER: '/checkin'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'COACH', 'THERAPIST', 'MEMBER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'center', component: () => import('../views/RehabCenter.vue'), meta: { roles: ['ADMIN', 'CENTER'] } },
      { path: 'member', component: () => import('../views/MemberProfile.vue'), meta: { roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'COACH', 'THERAPIST', 'MEMBER'] } },
      { path: 'coach', component: () => import('../views/CoachProfile.vue'), meta: { roles: ['ADMIN', 'CENTER', 'COACH'] } },
      { path: 'item', component: () => import('../views/AssessmentItem.vue'), meta: { roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST'] } },
      { path: 'assessment', component: () => import('../views/FitnessAssessment.vue'), meta: { roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'MEMBER'] } },
      { path: 'risk', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'COACH'] } },
      { path: 'plan', component: () => import('../views/TrainingPlan.vue'), meta: { roles: ['ADMIN', 'CENTER', 'COACH', 'THERAPIST', 'MEMBER'] } },
      { path: 'session', component: () => import('../views/TrainingSession.vue'), meta: { roles: ['ADMIN', 'CENTER', 'COACH', 'THERAPIST', 'MEMBER'] } },
      { path: 'checkin', component: () => import('../views/ExerciseCheckin.vue'), meta: { roles: ['ADMIN', 'CENTER', 'COACH', 'MEMBER'] } },
      { path: 'feedback', component: () => import('../views/RehabFeedback.vue'), meta: { roles: ['ADMIN', 'CENTER', 'THERAPIST', 'COACH', 'MEMBER'] } },
      { path: 'reassessment', component: () => import('../views/ReassessmentRecord.vue'), meta: { roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'MEMBER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
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
