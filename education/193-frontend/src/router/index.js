import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  ACADEMIC: '/program',
  REVIEWER: '/review',
  MENTOR: '/mentor',
  COUNSELOR: '/tracking',
  STUDENT: '/application'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'REVIEWER', 'MENTOR', 'COUNSELOR', 'STUDENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'program', component: () => import('../views/InnovationProgram.vue'), meta: { roles: ['ADMIN', 'ACADEMIC'] } },
      { path: 'student', component: () => import('../views/StudentProfile.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'COUNSELOR', 'STUDENT'] } },
      { path: 'mentor', component: () => import('../views/MentorProfile.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'MENTOR'] } },
      { path: 'notice', component: () => import('../views/SelectionNotice.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'REVIEWER', 'STUDENT'] } },
      { path: 'application', component: () => import('../views/ApplicationRegistration.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'REVIEWER', 'COUNSELOR', 'STUDENT'] } },
      { path: 'review', component: () => import('../views/SelectionReview.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'REVIEWER'] } },
      { path: 'interview', component: () => import('../views/InterviewAssessment.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'REVIEWER'] } },
      { path: 'match', component: () => import('../views/MentorMatch.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'MENTOR', 'COUNSELOR', 'STUDENT'] } },
      { path: 'plan', component: () => import('../views/TrainingPlan.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'MENTOR', 'COUNSELOR', 'STUDENT'] } },
      { path: 'tracking', component: () => import('../views/ProcessTracking.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'MENTOR', 'COUNSELOR'] } },
      { path: 'achievement', component: () => import('../views/AchievementArchive.vue'), meta: { roles: ['ADMIN', 'ACADEMIC', 'MENTOR', 'STUDENT'] } },
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
