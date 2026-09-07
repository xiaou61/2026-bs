import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/volunteer',
    component: () => import('@/layout/VolunteerLayout.vue'),
    redirect: '/volunteer/activities',
    children: [
      {
        path: 'activities',
        name: 'VolunteerActivities',
        component: () => import('@/views/volunteer/Activities.vue')
      },
      {
        path: 'activities/:id',
        name: 'ActivityDetail',
        component: () => import('@/views/volunteer/ActivityDetail.vue')
      },
      {
        path: 'my-activities',
        name: 'MyActivities',
        component: () => import('@/views/volunteer/MyActivities.vue')
      },
      {
        path: 'my-points',
        name: 'MyPoints',
        component: () => import('@/views/volunteer/MyPoints.vue')
      },
      {
        path: 'rewards',
        name: 'Rewards',
        component: () => import('@/views/volunteer/Rewards.vue')
      },
      {
        path: 'my-exchanges',
        name: 'MyExchanges',
        component: () => import('@/views/volunteer/MyExchanges.vue')
      },
      {
        path: 'ranking',
        name: 'Ranking',
        component: () => import('@/views/volunteer/Ranking.vue')
      },
      {
        path: 'profile',
        name: 'VolunteerProfile',
        component: () => import('@/views/Profile.vue')
      }
    ]
  },
  {
    path: '/admin',
    component: () => import('@/layout/AdminLayout.vue'),
    redirect: '/admin/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/admin/Dashboard.vue')
      },
      {
        path: 'activities',
        name: 'ActivityManage',
        component: () => import('@/views/admin/ActivityManage.vue')
      },
      {
        path: 'enrollments',
        name: 'EnrollmentManage',
        component: () => import('@/views/admin/EnrollmentManage.vue')
      },
      {
        path: 'attendances',
        name: 'AttendanceManage',
        component: () => import('@/views/admin/AttendanceManage.vue')
      },
      {
        path: 'users',
        name: 'UserManage',
        component: () => import('@/views/admin/UserManage.vue')
      },
      {
        path: 'rewards',
        name: 'RewardManage',
        component: () => import('@/views/admin/RewardManage.vue')
      },
      {
        path: 'exchanges',
        name: 'ExchangeManage',
        component: () => import('@/views/admin/ExchangeManage.vue')
      },
      {
        path: 'points',
        name: 'PointsManage',
        component: () => import('@/views/admin/PointsManage.vue')
      },
      {
        path: 'profile',
        name: 'AdminProfile',
        component: () => import('@/views/Profile.vue')
      }
    ]
  },
  {
    path: '/',
    redirect: '/login'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const user = JSON.parse(localStorage.getItem('user') || '{}')

  if (to.path === '/login' || to.path === '/register') {
    next()
  } else if (!token) {
    next('/login')
  } else {
    if (to.path.startsWith('/admin') && user.role !== 'ADMIN') {
      next('/volunteer/activities')
    } else if (to.path.startsWith('/volunteer') && user.role !== 'VOLUNTEER') {
      next('/admin/dashboard')
    } else {
      next()
    }
  }
})

export default router

