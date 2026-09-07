import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/course',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'TEACHER'] } },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'course', component: () => import('../views/course/index.vue') },
      { path: 'enroll', component: () => import('../views/enroll/index.vue') },
      { path: 'activity', component: () => import('../views/activity/index.vue') },
      { path: 'signup', component: () => import('../views/signup/index.vue') },
      { path: 'job', component: () => import('../views/job/index.vue') },
      { path: 'apply', component: () => import('../views/apply/index.vue') },
      { path: 'lost', component: () => import('../views/lost/index.vue') },
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
    if (user.role === 'ADMIN' || user.role === 'TEACHER') {
      next('/dashboard')
    } else {
      next('/course')
    }
    return
  }
  if (to.path !== '/login' && (!token || !user)) {
    next('/login')
    return
  }
  if (to.meta.roles && (!user || !to.meta.roles.includes(user.role))) {
    next('/course')
    return
  }
  next()
})

export default router
