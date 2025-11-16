import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/student',
    component: () => import('@/layouts/StudentLayout.vue'),
    meta: { requiresAuth: true, role: 'student' },
    children: [
      {
        path: '',
        redirect: '/student/home'
      },
      {
        path: 'home',
        name: 'StudentHome',
        component: () => import('@/views/student/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'rooms',
        name: 'StudyRooms',
        component: () => import('@/views/student/StudyRooms.vue'),
        meta: { title: '自习室列表' }
      },
      {
        path: 'room/:id',
        name: 'RoomDetail',
        component: () => import('@/views/student/RoomDetail.vue'),
        meta: { title: '座位选择' }
      },
      {
        path: 'reservations',
        name: 'MyReservations',
        component: () => import('@/views/student/MyReservations.vue'),
        meta: { title: '我的预约' }
      },
      {
        path: 'reservation/:id',
        name: 'ReservationDetail',
        component: () => import('@/views/student/ReservationDetail.vue'),
        meta: { title: '预约详情' }
      },
      {
        path: 'profile',
        name: 'StudentProfile',
        component: () => import('@/views/student/Profile.vue'),
        meta: { title: '个人中心' }
      },
      {
        path: 'credit',
        name: 'CreditHistory',
        component: () => import('@/views/student/CreditHistory.vue'),
        meta: { title: '信用记录' }
      }
    ]
  },
  {
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    meta: { requiresAuth: true, role: 'admin' },
    children: [
      {
        path: '',
        redirect: '/admin/dashboard'
      },
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/admin/Dashboard.vue'),
        meta: { title: '仪表盘' }
      },
      {
        path: 'rooms',
        name: 'RoomManagement',
        component: () => import('@/views/admin/RoomManagement.vue'),
        meta: { title: '自习室管理' }
      },
      {
        path: 'seats',
        name: 'SeatManagement',
        component: () => import('@/views/admin/SeatManagement.vue'),
        meta: { title: '座位管理' }
      },
      {
        path: 'reservations',
        name: 'ReservationManagement',
        component: () => import('@/views/admin/ReservationManagement.vue'),
        meta: { title: '预约管理' }
      },
      {
        path: 'users',
        name: 'UserManagement',
        component: () => import('@/views/admin/UserManagement.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'statistics',
        name: 'Statistics',
        component: () => import('@/views/admin/Statistics.vue'),
        meta: { title: '数据统计' }
      },
      {
        path: 'system',
        name: 'SystemConfig',
        component: () => import('@/views/admin/SystemConfig.vue'),
        meta: { title: '系统配置' }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  if (to.meta.requiresAuth) {
    if (!userStore.isLoggedIn) {
      ElMessage.warning('请先登录')
      next('/login')
    } else if (to.meta.role && userStore.userInfo.role !== to.meta.role) {
      ElMessage.error('无权访问该页面')
      next(from.path)
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router