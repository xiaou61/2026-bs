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
      { path: 'employee', component: () => import('../views/EmployeeProfile.vue') },
      { path: 'department', component: () => import('../views/DepartmentGroup.vue') },
      { path: 'template', component: () => import('../views/MailTemplate.vue') },
      { path: 'campaign', component: () => import('../views/PhishingCampaign.vue') },
      { path: 'target', component: () => import('../views/CampaignTarget.vue') },
      { path: 'send-record', component: () => import('../views/MailSendRecord.vue') },
      { path: 'click', component: () => import('../views/ClickTracking.vue') },
      { path: 'course', component: () => import('../views/TrainingCourse.vue') },
      { path: 'exam', component: () => import('../views/TrainingExam.vue') },
      { path: 'question', component: () => import('../views/ExamQuestion.vue') },
      { path: 'attempt', component: () => import('../views/ExamAttempt.vue') },
      { path: 'risk-score', component: () => import('../views/RiskScore.vue') },
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
