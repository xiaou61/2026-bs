import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    component: () => import('@/views/user/Login.vue')
  },
  {
    path: '/home',
    component: () => import('@/views/user/Home.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/report/submit',
    component: () => import('@/views/user/ReportSubmit.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/reports/my',
    component: () => import('@/views/user/MyReports.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/report/:id',
    component: () => import('@/views/user/ReportDetail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/admin/login',
    component: () => import('@/views/admin/AdminLogin.vue')
  },
  {
    path: '/admin/dashboard',
    component: () => import('@/views/admin/Dashboard.vue'),
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/audit',
    component: () => import('@/views/admin/AuditList.vue'),
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/audit/:id',
    component: () => import('@/views/admin/AuditDetail.vue'),
    meta: { requiresAuth: true, requiresAdmin: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  if (to.meta.requiresAuth && !userStore.token) {
    next('/login')
  } else if (to.meta.requiresAdmin && userStore.userInfo.role !== 'ADMIN') {
    next('/home')
  } else {
    next()
  }
})

export default router
