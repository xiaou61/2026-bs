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
      { path: 'case', component: () => import('../views/CounselCase.vue') },
      { path: 'room', component: () => import('../views/CounselRoom.vue') },
      { path: 'student', component: () => import('../views/StudentProfile.vue') },
      { path: 'duty', component: () => import('../views/DutySchedule.vue') },
      { path: 'appointment', component: () => import('../views/AppointmentRequest.vue') },
      { path: 'record', component: () => import('../views/CounselRecord.vue') },
      { path: 'questionnaire', component: () => import('../views/AssessmentQuestionnaire.vue') },
      { path: 'risk', component: () => import('../views/RiskAssessment.vue') },
      { path: 'intervention', component: () => import('../views/CrisisIntervention.vue') },
      { path: 'family', component: () => import('../views/FamilyCommunication.vue') },
      { path: 'followup', component: () => import('../views/FollowUpPlan.vue') },
      { path: 'notice', component: () => import('../views/SystemNotice.vue') },
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










