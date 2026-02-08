import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { role: 'admin' } },
      { path: 'movie/category', component: () => import('../views/movie/Category.vue'), meta: { role: 'admin' } },
      { path: 'movie', component: () => import('../views/movie/index.vue') },
      { path: 'movie/:id', component: () => import('../views/movie/Detail.vue') },
      { path: 'cinema', component: () => import('../views/cinema/index.vue'), meta: { role: 'admin' } },
      { path: 'hall', component: () => import('../views/cinema/Hall.vue'), meta: { role: 'admin' } },
      { path: 'showtime', component: () => import('../views/showtime/index.vue'), meta: { role: 'admin' } },
      { path: 'order', component: () => import('../views/order/index.vue'), meta: { role: 'admin' } },
      { path: 'my-order', component: () => import('../views/order/MyOrder.vue') },
      { path: 'review', component: () => import('../views/review/index.vue'), meta: { role: 'admin' } },
      { path: 'favorite', component: () => import('../views/favorite/index.vue') },
      { path: 'announcement', component: () => import('../views/announcement/index.vue') },
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
