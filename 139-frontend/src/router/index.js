import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/SysUser.vue') },
      { path: 'program', component: () => import('../views/TrainingProgram.vue') },
      { path: 'course', component: () => import('../views/CourseCatalog.vue') },
      { path: 'learner', component: () => import('../views/LearnerProfile.vue') },
      { path: 'path', component: () => import('../views/LearningPathPlan.vue') },
      { path: 'task', component: () => import('../views/StudyTask.vue') },
      { path: 'enroll', component: () => import('../views/CourseEnrollment.vue') },
      { path: 'exam', component: () => import('../views/AssessmentExam.vue') },
      { path: 'score', component: () => import('../views/ExamScore.vue') },
      { path: 'skill', component: () => import('../views/SkillTag.vue') },
      { path: 'profile', component: () => import('../views/CompetencyProfile.vue') },
      { path: 'cert', component: () => import('../views/CertificationRecord.vue') },
      { path: 'reminder', component: () => import('../views/LearningReminder.vue') },
      { path: 'log', component: () => import('../views/OperationLog.vue') }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) next('/login')
  else if (to.path === '/login' && userStore.token) next('/dashboard')
  else next()
})

export default router






