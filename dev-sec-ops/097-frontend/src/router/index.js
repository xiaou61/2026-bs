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
      { path: 'team', component: () => import('../views/Team.vue') },
      { path: 'category', component: () => import('../views/Category.vue') },
      { path: 'asset', component: () => import('../views/Asset.vue') },
      { path: 'version', component: () => import('../views/Version.vue') },
      { path: 'case', component: () => import('../views/TestCase.vue') },
      { path: 'model', component: () => import('../views/ModelConfig.vue') },
      { path: 'rule', component: () => import('../views/ScoreRule.vue') },
      { path: 'evaluation', component: () => import('../views/Evaluation.vue') },
      { path: 'result', component: () => import('../views/EvaluationResult.vue') },
      { path: 'feedback', component: () => import('../views/Feedback.vue') },
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
  if (to.path !== '/login' && !userStore.token) {
    next('/login')
  } else if (to.path === '/login' && userStore.token) {
    next('/dashboard')
  } else {
    next()
  }
})

export default router
