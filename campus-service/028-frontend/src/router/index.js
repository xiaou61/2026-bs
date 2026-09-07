import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue'),
    meta: { title: '注册' }
  },
  {
    path: '/',
    component: () => import('@/layouts/UserLayout.vue'),
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/user/Home.vue'),
        meta: { title: '首页', requiresAuth: true }
      },
      {
        path: 'stations',
        name: 'Stations',
        component: () => import('@/views/user/Stations.vue'),
        meta: { title: '停车点', requiresAuth: true }
      },
      {
        path: 'orders',
        name: 'Orders',
        component: () => import('@/views/user/Orders.vue'),
        meta: { title: '我的订单', requiresAuth: true }
      },
      {
        path: 'wallet',
        name: 'Wallet',
        component: () => import('@/views/user/Wallet.vue'),
        meta: { title: '我的钱包', requiresAuth: true }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/user/Profile.vue'),
        meta: { title: '个人中心', requiresAuth: true }
      }
    ]
  },
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('@/views/admin/AdminLogin.vue'),
    meta: { title: '管理员登录' }
  },
  {
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    redirect: '/admin/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/admin/Dashboard.vue'),
        meta: { title: '控制台', requiresAdmin: true }
      },
      {
        path: 'users',
        name: 'UserManage',
        component: () => import('@/views/admin/UserManage.vue'),
        meta: { title: '用户管理', requiresAdmin: true }
      },
      {
        path: 'bikes',
        name: 'BikeManage',
        component: () => import('@/views/admin/BikeManage.vue'),
        meta: { title: '车辆管理', requiresAdmin: true }
      },
      {
        path: 'stations',
        name: 'StationManage',
        component: () => import('@/views/admin/StationManage.vue'),
        meta: { title: '停车点管理', requiresAdmin: true }
      },
      {
        path: 'orders',
        name: 'OrderManage',
        component: () => import('@/views/admin/OrderManage.vue'),
        meta: { title: '订单管理', requiresAdmin: true }
      },
      {
        path: 'faults',
        name: 'FaultManage',
        component: () => import('@/views/admin/FaultManage.vue'),
        meta: { title: '故障管理', requiresAdmin: true }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  document.title = to.meta.title ? `${to.meta.title} - 校园共享自行车` : '校园共享自行车租赁系统'
  
  const userStore = useUserStore()
  
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next('/login')
  } else if (to.meta.requiresAdmin && (!userStore.isLoggedIn || userStore.userType !== 'admin')) {
    next('/admin/login')
  } else {
    next()
  }
})

export default router
