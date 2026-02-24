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
      { path: 'user', component: () => import('../views/user/index.vue') },
      { path: 'movie', component: () => import('../views/movie/index.vue') },
      { path: 'cinema', component: () => import('../views/cinema/index.vue') },
      { path: 'hall', component: () => import('../views/hall/index.vue') },
      { path: 'showtime', component: () => import('../views/showtime/index.vue') },
      { path: 'seat', component: () => import('../views/seat/index.vue') },
      { path: 'order', component: () => import('../views/order/index.vue') },
      { path: 'my-order', component: () => import('../views/my-order/index.vue') },
      { path: 'payment', component: () => import('../views/payment/index.vue') },
      { path: 'ticket', component: () => import('../views/ticket/index.vue') },
      { path: 'my-ticket', component: () => import('../views/my-ticket/index.vue') },
      { path: 'coupon', component: () => import('../views/coupon/index.vue') },
      { path: 'my-coupon', component: () => import('../views/my-coupon/index.vue') },
      { path: 'comment', component: () => import('../views/comment/index.vue') },
      { path: 'profile', component: () => import('../views/profile/index.vue') },
      { path: 'statistics', component: () => import('../views/statistics/index.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    return next('/login')
  }
  if (to.path === '/login' && token) {
    return next('/dashboard')
  }
  next()
})

export default router
