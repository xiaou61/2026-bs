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
      { path: 'plan', component: () => import('../views/ExamPlan.vue') },
      { path: 'invigilator', component: () => import('../views/InvigilatorProfile.vue') },
      { path: 'candidate', component: () => import('../views/CandidateProfile.vue') },
      { path: 'session', component: () => import('../views/ExamSession.vue') },
      { path: 'behavior', component: () => import('../views/SuspiciousBehavior.vue') },
      { path: 'evidence', component: () => import('../views/EvidenceRecord.vue') },
      { path: 'task', component: () => import('../views/ReviewTask.vue') },
      { path: 'decision', component: () => import('../views/ReviewDecision.vue') },
      { path: 'rule', component: () => import('../views/WarningRule.vue') },
      { path: 'device', component: () => import('../views/DeviceMonitor.vue') },
      { path: 'appeal', component: () => import('../views/ViolationAppeal.vue') },
      { path: 'notice', component: () => import('../views/AlertNotification.vue') },
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




