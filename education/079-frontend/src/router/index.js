import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/Index.vue') },
      { path: 'alumni', component: () => import('../views/alumni/Index.vue') },
      { path: 'alumni/:id', component: () => import('../views/alumni/Detail.vue') },
      { path: 'grade', component: () => import('../views/grade/Index.vue') },
      { path: 'news', component: () => import('../views/news/Index.vue') },
      { path: 'news/:id', component: () => import('../views/news/Detail.vue') },
      { path: 'activity', component: () => import('../views/activity/Index.vue') },
      { path: 'activity/:id', component: () => import('../views/activity/Detail.vue') },
      { path: 'donation', component: () => import('../views/donation/Index.vue') },
      { path: 'donation/:id', component: () => import('../views/donation/Detail.vue') },
      { path: 'company', component: () => import('../views/company/Index.vue') },
      { path: 'job', component: () => import('../views/job/Index.vue') },
      { path: 'forum', component: () => import('../views/forum/Index.vue') },
      { path: 'forum/:id', component: () => import('../views/forum/Detail.vue') },
      { path: 'banner', component: () => import('../views/system/Banner.vue') },
      { path: 'log', component: () => import('../views/system/Log.vue') },
      { path: 'profile', component: () => import('../views/Profile.vue') }
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
    next('/login')
  } else {
    next()
  }
})

export default router
