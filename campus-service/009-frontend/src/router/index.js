import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
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
      path: '/',
      redirect: '/student/packages'
    },
    {
      path: '/student',
      component: () => import('@/layout/StudentLayout.vue'),
      redirect: '/student/packages',
      children: [
        {
          path: 'packages',
          name: 'StudentPackages',
          component: () => import('@/views/student/MyPackages.vue'),
          meta: { title: '我的快递' }
        },
        {
          path: 'history',
          name: 'StudentHistory',
          component: () => import('@/views/student/PickupHistory.vue'),
          meta: { title: '取件历史' }
        },
        {
          path: 'notifications',
          name: 'StudentNotifications',
          component: () => import('@/views/student/Notifications.vue'),
          meta: { title: '消息通知' }
        },
        {
          path: 'profile',
          name: 'StudentProfile',
          component: () => import('@/views/student/Profile.vue'),
          meta: { title: '个人中心' }
        }
      ]
    },
    {
      path: '/courier',
      component: () => import('@/layout/CourierLayout.vue'),
      redirect: '/courier/express-in',
      children: [
        {
          path: 'express-in',
          name: 'ExpressIn',
          component: () => import('@/views/courier/ExpressIn.vue'),
          meta: { title: '快递入库' }
        },
        {
          path: 'batch-import',
          name: 'CourierBatchImport',
          component: () => import('@/views/admin/BatchImport.vue'),
          meta: { title: '批量导入' }
        },
        {
          path: 'pickup',
          name: 'CourierPickup',
          component: () => import('@/views/courier/Pickup.vue'),
          meta: { title: '取件核销' }
        },
        {
          path: 'express-list',
          name: 'CourierExpressList',
          component: () => import('@/views/courier/ExpressList.vue'),
          meta: { title: '快递管理' }
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
          component: () => import('@/views/admin/Dashboard.vue'),
          meta: { title: '数据统计' }
        },
        {
          path: 'express',
          name: 'AdminExpress',
          component: () => import('@/views/admin/ExpressManage.vue'),
          meta: { title: '快递管理' }
        },
        {
          path: 'station',
          name: 'StationManage',
          component: () => import('@/views/admin/StationManage.vue'),
          meta: { title: '代收点管理' }
        },
        {
          path: 'company',
          name: 'CompanyManage',
          component: () => import('@/views/admin/CompanyManage.vue'),
          meta: { title: '快递公司管理' }
        },
        {
          path: 'user',
          name: 'UserManage',
          component: () => import('@/views/admin/UserManage.vue'),
          meta: { title: '用户管理' }
        },
        {
          path: 'pickup',
          name: 'AdminPickup',
          component: () => import('@/views/admin/Pickup.vue'),
          meta: { title: '取件核销' }
        },
        {
          path: 'batch-import',
          name: 'BatchImport',
          component: () => import('@/views/admin/BatchImport.vue'),
          meta: { title: '批量导入' }
        },
        {
          path: 'operation-log',
          name: 'OperationLog',
          component: () => import('@/views/admin/OperationLog.vue'),
          meta: { title: '操作日志' }
        },
        {
          path: 'config',
          name: 'SystemConfig',
          component: () => import('@/views/admin/SystemConfig.vue'),
          meta: { title: '系统配置' }
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  if (to.path === '/login' || to.path === '/register') {
    next()
    return
  }

  if (!userStore.isLoggedIn) {
    next('/login')
    return
  }

  const role = userStore.userInfo?.role
  
  if (to.path.startsWith('/student') && role !== 'STUDENT') {
    next('/login')
    return
  }
  
  if (to.path.startsWith('/courier') && !['COURIER', 'STATION_ADMIN', 'ADMIN'].includes(role)) {
    next('/login')
    return
  }
  
  if (to.path.startsWith('/admin') && !['ADMIN', 'STATION_ADMIN'].includes(role)) {
    next('/login')
    return
  }

  next()
})

export default router

