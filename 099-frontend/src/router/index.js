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
      { path: 'asset', component: () => import('../views/Asset.vue') },
      { path: 'rule', component: () => import('../views/Rule.vue') },
      { path: 'task', component: () => import('../views/Task.vue') },
      { path: 'result', component: () => import('../views/AuditResult.vue') },
      { path: 'tag', component: () => import('../views/Tag.vue') },
      { path: 'register', component: () => import('../views/Register.vue') },
      { path: 'evidence', component: () => import('../views/Evidence.vue') },
      { path: 'license', component: () => import('../views/License.vue') },
      { path: 'clue', component: () => import('../views/Clue.vue') },
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
