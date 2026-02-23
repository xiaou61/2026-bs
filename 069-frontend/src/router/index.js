import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/dashboard/index.vue') },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'subject', component: () => import('../views/spot/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'class', component: () => import('../views/traveler/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'teacher', component: () => import('../views/favorite/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'indicator', component: () => import('../views/indicator/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'task', component: () => import('../views/order/index.vue') },
      { path: 'record', component: () => import('../views/review/index.vue') },
      { path: 'appeal', component: () => import('../views/complaint/index.vue') },
      { path: 'notice', component: () => import('../views/notice/index.vue') },
      { path: 'profile', component: () => import('../views/profile/index.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const user = JSON.parse(localStorage.getItem('user') || 'null')
  if (to.path === '/login' && token && user) {
    next('/dashboard')
    return
  }
  if (to.path !== '/login' && (!token || !user)) {
    next('/login')
    return
  }
  if (to.meta.roles && (!user || !to.meta.roles.includes(user.role))) {
    next('/dashboard')
    return
  }
  next()
})

export default router
