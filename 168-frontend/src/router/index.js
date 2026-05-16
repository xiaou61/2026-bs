import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  TRAINING: '/course',
  INSTRUCTOR: '/progress',
  TRAINEE: '/enrollment',
  EXAMINER: '/exam',
  CERTIFIER: '/certificate'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE', 'EXAMINER', 'CERTIFIER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'course', component: () => import('../views/TrainingCourse.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE'] } },
      { path: 'trainee', component: () => import('../views/TraineeProfile.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE'] } },
      { path: 'instructor', component: () => import('../views/InstructorProfile.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR'] } },
      { path: 'enrollment', component: () => import('../views/CourseEnrollment.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'TRAINEE'] } },
      { path: 'progress', component: () => import('../views/LearningProgress.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE'] } },
      { path: 'exam', component: () => import('../views/ExamPlan.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'EXAMINER', 'INSTRUCTOR', 'TRAINEE'] } },
      { path: 'score', component: () => import('../views/ExamScore.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'EXAMINER', 'INSTRUCTOR', 'TRAINEE'] } },
      { path: 'certificate', component: () => import('../views/CertificateIssue.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'EXAMINER', 'CERTIFIER', 'TRAINEE'] } },
      { path: 'ledger', component: () => import('../views/CertificateLedger.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'CERTIFIER', 'TRAINEE'] } },
      { path: 'renewal', component: () => import('../views/RenewalApplication.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'CERTIFIER', 'TRAINEE'] } },
      { path: 'reminder', component: () => import('../views/ReminderNotice.vue'), meta: { roles: ['ADMIN', 'TRAINING', 'CERTIFIER', 'TRAINEE'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'TRAINING'] } }
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
