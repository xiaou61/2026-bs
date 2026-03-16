import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'train', component: () => import('../views/train/index.vue'), meta: { roles: ['ADMIN', 'DISPATCHER'] } },
      { path: 'station', component: () => import('../views/station/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'carriage', component: () => import('../views/carriage/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'schedule', component: () => import('../views/schedule/index.vue') },
      { path: 'seat', component: () => import('../views/seat/index.vue'), meta: { roles: ['USER'] } },
      { path: 'order', component: () => import('../views/order/index.vue'), meta: { roles: ['ADMIN', 'DISPATCHER'] } },
      { path: 'my-order', component: () => import('../views/my-order/index.vue'), meta: { roles: ['USER'] } },
      { path: 'payment', component: () => import('../views/payment/index.vue'), meta: { roles: ['ADMIN', 'DISPATCHER'] } },
      { path: 'ticket', component: () => import('../views/ticket/index.vue'), meta: { roles: ['ADMIN', 'DISPATCHER'] } },
      { path: 'my-ticket', component: () => import('../views/my-ticket/index.vue'), meta: { roles: ['USER'] } },
      { path: 'passenger', component: () => import('../views/passenger/index.vue'), meta: { roles: ['USER'] } },
      { path: 'after-sale', component: () => import('../views/after-sale/index.vue') },
      { path: 'notice', component: () => import('../views/notice/index.vue') },
      { path: 'profile', component: () => import('../views/profile/index.vue') },
      { path: 'statistics', component: () => import('../views/statistics/index.vue'), meta: { roles: ['ADMIN', 'DISPATCHER'] } }
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
  if (to.path !== '/login' && !token) {
    return next('/login')
  }
  if (to.path === '/login' && token) {
    return next('/dashboard')
  }
  const roles = to.meta?.roles
  if (roles && user && !roles.includes((user.role || '').toUpperCase())) {
    return next('/dashboard')
  }
  next()
})

export default router
