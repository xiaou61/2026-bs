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
      { path: 'customer', component: () => import('../views/Customer.vue') },
      { path: 'channel', component: () => import('../views/Channel.vue') },
      { path: 'category', component: () => import('../views/Category.vue') },
      { path: 'article', component: () => import('../views/Article.vue') },
      { path: 'order', component: () => import('../views/Order.vue') },
      { path: 'message', component: () => import('../views/Message.vue') },
      { path: 'assignment', component: () => import('../views/Assignment.vue') },
      { path: 'rule', component: () => import('../views/Rule.vue') },
      { path: 'quality-task', component: () => import('../views/QualityTask.vue') },
      { path: 'quality-result', component: () => import('../views/QualityResult.vue') },
      { path: 'recommend', component: () => import('../views/Recommend.vue') },
      { path: 'performance', component: () => import('../views/Performance.vue') },
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
