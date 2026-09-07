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
      { path: 'location', component: () => import('../views/location/index.vue') },
      { path: 'machine', component: () => import('../views/machine/index.vue') },
      { path: 'category', component: () => import('../views/category/index.vue') },
      { path: 'product', component: () => import('../views/product/index.vue') },
      { path: 'slot', component: () => import('../views/slot/index.vue') },
      { path: 'replenish', component: () => import('../views/replenish/index.vue') },
      { path: 'order', component: () => import('../views/order/index.vue') },
      { path: 'payment', component: () => import('../views/payment/index.vue') },
      { path: 'shipment', component: () => import('../views/shipment/index.vue') },
      { path: 'fault', component: () => import('../views/fault/index.vue') },
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
