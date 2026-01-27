import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', name: 'User', component: () => import('../views/User.vue') },
      { path: 'category', name: 'Category', component: () => import('../views/Category.vue') },
      { path: 'course', name: 'Course', component: () => import('../views/Course.vue') },
      { path: 'comment', name: 'Comment', component: () => import('../views/Comment.vue') },
      { path: 'banner', name: 'Banner', component: () => import('../views/Banner.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('adminToken')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
