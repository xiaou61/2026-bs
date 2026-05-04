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
      { path: 'user', component: () => import('../views/User.vue') },
      { path: 'candidate', component: () => import('../views/Candidate.vue') },
      { path: 'resume', component: () => import('../views/Resume.vue') },
      { path: 'certificate', component: () => import('../views/Certificate.vue') },
      { path: 'job', component: () => import('../views/Job.vue') },
      { path: 'requirement', component: () => import('../views/Requirement.vue') },
      { path: 'parse-task', component: () => import('../views/ParsingTask.vue') },
      { path: 'parse-result', component: () => import('../views/ParsingResult.vue') },
      { path: 'match-task', component: () => import('../views/MatchTask.vue') },
      { path: 'match-result', component: () => import('../views/MatchResult.vue') },
      { path: 'interview', component: () => import('../views/InterviewPlan.vue') },
      { path: 'feedback', component: () => import('../views/InterviewFeedback.vue') },
      { path: 'log', component: () => import('../views/OperationLog.vue') }
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
  else if (to.path === '/login' && userStore.token) next('/dashboard')
  else next()
})

export default router
