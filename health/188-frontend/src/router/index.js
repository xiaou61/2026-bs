import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  EDU: '/program',
  INSTRUCTOR: '/class',
  ASSESSOR: '/assessment',
  NURSE: '/checkin',
  HR: '/certificate'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'ASSESSOR', 'NURSE', 'HR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'program', component: () => import('../views/TrainingProgram.vue'), meta: { roles: ['ADMIN', 'EDU', 'INSTRUCTOR'] } },
      { path: 'nurse', component: () => import('../views/NurseProfile.vue'), meta: { roles: ['ADMIN', 'EDU', 'HR'] } },
      { path: 'class', component: () => import('../views/TrainingClass.vue'), meta: { roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'NURSE'] } },
      { path: 'checkin', component: () => import('../views/TrainingCheckin.vue'), meta: { roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'NURSE'] } },
      { path: 'skill', component: () => import('../views/SkillItem.vue'), meta: { roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'ASSESSOR'] } },
      { path: 'assessment', component: () => import('../views/SkillAssessment.vue'), meta: { roles: ['ADMIN', 'EDU', 'ASSESSOR', 'NURSE'] } },
      { path: 'certificate', component: () => import('../views/CertificateArchive.vue'), meta: { roles: ['ADMIN', 'EDU', 'HR', 'NURSE'] } },
      { path: 'retraining', component: () => import('../views/RetrainingReminder.vue'), meta: { roles: ['ADMIN', 'EDU', 'HR', 'NURSE'] } },
      { path: 'practice', component: () => import('../views/PracticeRecord.vue'), meta: { roles: ['ADMIN', 'INSTRUCTOR', 'ASSESSOR', 'NURSE'] } },
      { path: 'appeal', component: () => import('../views/AssessmentAppeal.vue'), meta: { roles: ['ADMIN', 'EDU', 'ASSESSOR', 'NURSE'] } },
      { path: 'notice', component: () => import('../views/TrainingNotice.vue'), meta: { roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'ASSESSOR', 'NURSE', 'HR'] } },
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
