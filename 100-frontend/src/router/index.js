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
      { path: 'course', component: () => import('../views/Course.vue') },
      { path: 'class', component: () => import('../views/ClassInfo.vue') },
      { path: 'student', component: () => import('../views/Student.vue') },
      { path: 'assignment', component: () => import('../views/Assignment.vue') },
      { path: 'submission', component: () => import('../views/Submission.vue') },
      { path: 'rule', component: () => import('../views/Rule.vue') },
      { path: 'task', component: () => import('../views/Task.vue') },
      { path: 'result', component: () => import('../views/DetectionResult.vue') },
      { path: 'warning', component: () => import('../views/Warning.vue') },
      { path: 'rectification', component: () => import('../views/Rectification.vue') },
      { path: 'appeal', component: () => import('../views/Appeal.vue') },
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
