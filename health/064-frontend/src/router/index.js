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
      { path: 'category', component: () => import('../views/category/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'ingredient', component: () => import('../views/ingredient/index.vue') },
      { path: 'formula', component: () => import('../views/formula/index.vue') },
      { path: 'plan', component: () => import('../views/plan/index.vue') },
      { path: 'service', component: () => import('../views/service/index.vue') },
      { path: 'constitution', component: () => import('../views/constitution/index.vue') },
      { path: 'favorite', component: () => import('../views/favorite/index.vue'), meta: { roles: ['USER'] } },
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
    next('/dashboard')
    return
  }
  next()
})

export default router
