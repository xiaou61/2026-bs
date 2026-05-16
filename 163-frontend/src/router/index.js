import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  SECRETARY: '/rotation',
  TEACHER: '/case',
  STUDENT: '/student',
  EXAMINER: '/exam',
  DIRECTOR: '/department'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT', 'EXAMINER', 'DIRECTOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'department', component: () => import('../views/DepartmentProfile.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'DIRECTOR'] } },
      { path: 'student', component: () => import('../views/StudentProfile.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT'] } },
      { path: 'teacher', component: () => import('../views/TeacherProfile.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER'] } },
      { path: 'rotation', component: () => import('../views/RotationPlan.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER', 'STUDENT'] } },
      { path: 'case', component: () => import('../views/CaseLibrary.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT'] } },
      { path: 'study', component: () => import('../views/StudyRecord.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT'] } },
      { path: 'round', component: () => import('../views/TeachingRound.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER', 'STUDENT'] } },
      { path: 'skill', component: () => import('../views/SkillTraining.vue'), meta: { roles: ['ADMIN', 'TEACHER', 'STUDENT', 'EXAMINER'] } },
      { path: 'score', component: () => import('../views/TeachingScore.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT', 'DIRECTOR'] } },
      { path: 'exam', component: () => import('../views/ExitExam.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'EXAMINER', 'TEACHER', 'STUDENT', 'DIRECTOR'] } },
      { path: 'feedback', component: () => import('../views/FeedbackReview.vue'), meta: { roles: ['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER', 'STUDENT'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'SECRETARY'] } }
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
