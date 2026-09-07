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
      { path: 'area', component: () => import('../views/area/index.vue') },
      { path: 'shop', component: () => import('../views/shop/index.vue') },
      { path: 'pet', component: () => import('../views/pet/index.vue') },
      { path: 'category', component: () => import('../views/category/index.vue') },
      { path: 'menu', component: () => import('../views/menu/index.vue') },
      { path: 'seat', component: () => import('../views/seat/index.vue') },
      { path: 'reservation', component: () => import('../views/reservation/index.vue') },
      { path: 'order', component: () => import('../views/order/index.vue') },
      { path: 'payment', component: () => import('../views/payment/index.vue') },
      { path: 'review', component: () => import('../views/review/index.vue') },
      { path: 'notice', component: () => import('../views/notice/index.vue') },
      { path: 'statistics', component: () => import('../views/statistics/index.vue') },
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
  if (to.path !== '/login' && !token) {
    return next('/login')
  }
  if (to.path === '/login' && token) {
    return next('/dashboard')
  }
  next()
})

export default router
