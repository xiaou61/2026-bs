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
      { path: 'article', name: 'Article', component: () => import('../views/Article.vue') },
      { path: 'question', name: 'Question', component: () => import('../views/Question.vue') },
      { path: 'news', name: 'News', component: () => import('../views/News.vue') },
      { path: 'qa', name: 'Qa', component: () => import('../views/Qa.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('admin_token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
