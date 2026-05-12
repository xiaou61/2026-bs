import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const getHomePath = (role) => {
  if (role === 'ADMIN') return '/dashboard'
  if (role === 'HR') return '/candidate'
  if (role === 'CANDIDATE') return '/resume'
  if (role === 'INTERVIEWER') return '/interview'
  return '/login'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'user', component: () => import('../views/User.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'candidate', component: () => import('../views/Candidate.vue'), meta: { roles: ['HR', 'CANDIDATE'] } },
      { path: 'resume', component: () => import('../views/Resume.vue'), meta: { roles: ['HR', 'CANDIDATE'] } },
      { path: 'certificate', component: () => import('../views/Certificate.vue'), meta: { roles: ['HR', 'CANDIDATE'] } },
      { path: 'job', component: () => import('../views/Job.vue'), meta: { roles: ['HR'] } },
      { path: 'requirement', component: () => import('../views/Requirement.vue'), meta: { roles: ['HR'] } },
      { path: 'parse-task', component: () => import('../views/ParsingTask.vue'), meta: { roles: ['HR'] } },
      { path: 'parse-result', component: () => import('../views/ParsingResult.vue'), meta: { roles: ['HR'] } },
      { path: 'match-task', component: () => import('../views/MatchTask.vue'), meta: { roles: ['HR'] } },
      { path: 'match-result', component: () => import('../views/MatchResult.vue'), meta: { roles: ['HR'] } },
      { path: 'interview', component: () => import('../views/InterviewPlan.vue'), meta: { roles: ['HR', 'INTERVIEWER'] } },
      { path: 'feedback', component: () => import('../views/InterviewFeedback.vue'), meta: { roles: ['HR', 'INTERVIEWER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) next('/login')
  else if (to.path === '/login' && userStore.token) next(getHomePath(userStore.user?.role))
  else if (to.meta?.roles?.length && !to.meta.roles.includes(userStore.user?.role)) next(getHomePath(userStore.user?.role))
  else next()
})

export default router
