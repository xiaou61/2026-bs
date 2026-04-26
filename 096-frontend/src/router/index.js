import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'department', component: () => import('../views/department/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'doctor', component: () => import('../views/doctor/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'doctor-market', component: () => import('../views/doctor/Market.vue'), meta: { roles: ['PATIENT', 'ADMIN', 'DOCTOR'] } },
      { path: 'schedule', component: () => import('../views/schedule/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'my-schedule', component: () => import('../views/schedule/My.vue'), meta: { roles: ['DOCTOR'] } },
      { path: 'appointment', component: () => import('../views/appointment/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'my-appointment', component: () => import('../views/appointment/My.vue'), meta: { roles: ['PATIENT'] } },
      { path: 'doctor-appointment', component: () => import('../views/appointment/Doctor.vue'), meta: { roles: ['DOCTOR'] } },
      { path: 'order', component: () => import('../views/order/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'my-order', component: () => import('../views/order/My.vue'), meta: { roles: ['PATIENT'] } },
      { path: 'card', component: () => import('../views/card/index.vue'), meta: { roles: ['PATIENT'] } },
      { path: 'review', component: () => import('../views/review/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'banner', component: () => import('../views/banner/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'statistics', component: () => import('../views/statistics/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'notice', component: () => import('../views/notice/index.vue') },
      { path: 'profile', component: () => import('../views/profile/index.vue') },
      { path: 'notice-center', redirect: '/notice' }
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
    next('/login')
    return
  }
  if (to.path === '/login' && token) {
    next('/dashboard')
    return
  }
  if (to.meta.roles && !to.meta.roles.includes(user?.role)) {
    next('/dashboard')
    return
  }
  next()
})

export default router
