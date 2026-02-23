import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/blog',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'category', component: () => import('../views/category/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'tag', component: () => import('../views/tag/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'post', component: () => import('../views/post/index.vue') },
      { path: 'comment', component: () => import('../views/comment/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'notice', component: () => import('../views/notice/index.vue') },
      { path: 'blog', component: () => import('../views/blog/index.vue') },
      { path: 'blog-detail/:id', component: () => import('../views/blog/detail.vue') },
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
    next(user.role === 'ADMIN' ? '/dashboard' : '/blog')
    return
  }
  if (to.path !== '/login' && (!token || !user)) {
    next('/login')
    return
  }
  if (to.meta.roles && (!user || !to.meta.roles.includes(user.role))) {
    next('/blog')
    return
  }
  next()
})

export default router
