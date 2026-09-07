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
      { path: 'spot', component: () => import('../views/spot/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'traveler', component: () => import('../views/traveler/index.vue'), meta: { roles: ['USER'] } },
      { path: 'favorite', component: () => import('../views/favorite/index.vue'), meta: { roles: ['USER'] } },
      { path: 'order', component: () => import('../views/order/index.vue') },
      { path: 'review', component: () => import('../views/review/index.vue') },
      { path: 'complaint', component: () => import('../views/complaint/index.vue') },
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
