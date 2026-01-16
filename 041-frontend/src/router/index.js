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
    path: '/scales',
    component: () => import('@/views/user/ScaleList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/assessment/:id',
    component: () => import('@/views/user/Assessment.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/assessments/my',
    component: () => import('@/views/user/MyAssessments.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/counselors',
    component: () => import('@/views/user/CounselorList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/counselor/:id',
    component: () => import('@/views/user/CounselorDetail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/appointments/my',
    component: () => import('@/views/user/MyAppointments.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/articles',
    component: () => import('@/views/user/ArticleList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/article/:id',
    component: () => import('@/views/user/ArticleDetail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/counselor-login',
    component: () => import('@/views/counselor/CounselorLogin.vue')
  },
  {
    path: '/counselor/dashboard',
    component: () => import('@/views/counselor/CounselorDashboard.vue'),
    meta: { requiresAuth: true, requiresCounselor: true }
  },
  {
    path: '/admin/login',
    component: () => import('@/views/admin/AdminLogin.vue')
  },
  {
    path: '/admin/dashboard',
    component: () => import('@/views/admin/AdminDashboard.vue'),
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
  } else if (to.meta.requiresCounselor && userStore.userInfo.role !== 'COUNSELOR') {
    next('/home')
  } else {
    next()
  }
})

export default router
