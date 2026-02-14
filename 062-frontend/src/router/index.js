import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'ANALYST'] } },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'log', component: () => import('../views/log/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'blacklist', component: () => import('../views/blacklist/index.vue'), meta: { roles: ['ADMIN', 'ANALYST'] } },
      { path: 'rule', component: () => import('../views/rule/index.vue'), meta: { roles: ['ADMIN', 'ANALYST'] } },
      { path: 'event', component: () => import('../views/event/index.vue') },
      { path: 'my-alert', component: () => import('../views/alert/MyAlert.vue'), meta: { roles: ['USER'] } },
      { path: 'alert', component: () => import('../views/alert/index.vue'), meta: { roles: ['ADMIN', 'ANALYST'] } },
      { path: 'case', component: () => import('../views/case/index.vue'), meta: { roles: ['ADMIN', 'ANALYST'] } },
      { path: 'appeal', component: () => import('../views/appeal/index.vue'), meta: { roles: ['ADMIN', 'ANALYST'] } },
      { path: 'my-appeal', component: () => import('../views/appeal/MyAppeal.vue') },
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
  if (to.path !== '/login' && !token) {
    next('/login')
    return
  }
  if (to.meta.roles && (!user || !to.meta.roles.includes(user.role))) {
    next('/event')
    return
  }
  next()
})

export default router
