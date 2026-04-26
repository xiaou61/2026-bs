import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/index.vue') },
      { path: 'league', component: () => import('../views/league/index.vue') },
      { path: 'season', component: () => import('../views/season/index.vue') },
      { path: 'club', component: () => import('../views/club/index.vue') },
      { path: 'team', component: () => import('../views/team/index.vue') },
      { path: 'coach', component: () => import('../views/coach/index.vue') },
      { path: 'player', component: () => import('../views/player/index.vue') },
      { path: 'venue', component: () => import('../views/venue/index.vue') },
      { path: 'match', component: () => import('../views/match/index.vue') },
      { path: 'standing', component: () => import('../views/standing/index.vue') },
      { path: 'follow', component: () => import('../views/follow/index.vue') },
      { path: 'news', component: () => import('../views/news/index.vue') },
      { path: 'statistics', component: () => import('../views/statistics/index.vue') },
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
  if (to.path !== '/login' && !token) {
    return next('/login')
  }
  if (to.path === '/login' && token) {
    return next('/dashboard')
  }
  next()
})

export default router
