import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'STAFF'] } },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'building', component: () => import('../views/building/index.vue'), meta: { roles: ['ADMIN', 'STAFF'] } },
      { path: 'house', component: () => import('../views/house/index.vue'), meta: { roles: ['ADMIN', 'STAFF'] } },
      { path: 'fee', component: () => import('../views/fee/index.vue') },
      { path: 'repair', component: () => import('../views/repair/index.vue') },
      { path: 'complaint', component: () => import('../views/complaint/index.vue') },
      { path: 'visitor', component: () => import('../views/visitor/index.vue') },
      { path: 'parking', component: () => import('../views/parking/index.vue'), meta: { roles: ['ADMIN', 'STAFF'] } },
      { path: 'announcement', component: () => import('../views/announcement/index.vue') },
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
    if (user.role === 'OWNER') {
      next('/fee')
    } else {
      next('/dashboard')
    }
    return
  }
  if (to.path !== '/login' && (!token || !user)) {
    next('/login')
    return
  }
  if (to.meta.roles && (!user || !to.meta.roles.includes(user.role))) {
    next('/fee')
    return
  }
  next()
})

export default router
