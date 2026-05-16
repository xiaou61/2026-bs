import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/SysUser.vue') },
      { path: 'record01', component: () => import('../views/BizRecord01.vue') },
      { path: 'record02', component: () => import('../views/BizRecord02.vue') },
      { path: 'record03', component: () => import('../views/BizRecord03.vue') },
      { path: 'record04', component: () => import('../views/BizRecord04.vue') },
      { path: 'record05', component: () => import('../views/BizRecord05.vue') },
      { path: 'record06', component: () => import('../views/BizRecord06.vue') },
      { path: 'record07', component: () => import('../views/BizRecord07.vue') },
      { path: 'record08', component: () => import('../views/BizRecord08.vue') },
      { path: 'record09', component: () => import('../views/BizRecord09.vue') },
      { path: 'record10', component: () => import('../views/BizRecord10.vue') },
      { path: 'record11', component: () => import('../views/BizRecord11.vue') },
      { path: 'record12', component: () => import('../views/BizRecord12.vue') }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) return next('/login')
  if (to.path === '/login' && userStore.token) return next('/dashboard')
  next()
})
export default router
