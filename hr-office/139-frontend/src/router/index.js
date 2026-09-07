import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  TRAINER: '/program',
  EMPLOYEE: '/task',
  MANAGER: '/profile'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'program', component: () => import('../views/TrainingProgram.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'course', component: () => import('../views/CourseCatalog.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'learner', component: () => import('../views/LearnerProfile.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'path', component: () => import('../views/LearningPathPlan.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'task', component: () => import('../views/StudyTask.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'enroll', component: () => import('../views/CourseEnrollment.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'exam', component: () => import('../views/AssessmentExam.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'score', component: () => import('../views/ExamScore.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'skill', component: () => import('../views/SkillTag.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'profile', component: () => import('../views/CompetencyProfile.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'cert', component: () => import('../views/CertificationRecord.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
      { path: 'reminder', component: () => import('../views/LearningReminder.vue'), meta: { roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] } },
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






