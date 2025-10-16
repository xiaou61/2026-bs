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
    component: () => import('@/layout/MainLayout.vue'),
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'map',
        name: 'Map',
        component: () => import('@/views/Map.vue'),
        meta: { title: '地图' }
      },
      {
        path: 'idle',
        name: 'IdleList',
        component: () => import('@/views/idle/List.vue'),
        meta: { title: '闲置物品' }
      },
      {
        path: 'idle/:id',
        name: 'IdleDetail',
        component: () => import('@/views/idle/Detail.vue'),
        meta: { title: '物品详情' }
      },
      {
        path: 'idle/publish',
        name: 'IdlePublish',
        component: () => import('@/views/idle/Publish.vue'),
        meta: { title: '发布闲置物品', requiresAuth: true }
      },
      {
        path: 'skill',
        name: 'SkillList',
        component: () => import('@/views/skill/List.vue'),
        meta: { title: '技能服务' }
      },
      {
        path: 'skill/:id',
        name: 'SkillDetail',
        component: () => import('@/views/skill/Detail.vue'),
        meta: { title: '服务详情' }
      },
      {
        path: 'skill/publish',
        name: 'SkillPublish',
        component: () => import('@/views/skill/Publish.vue'),
        meta: { title: '发布技能服务', requiresAuth: true }
      },
      {
        path: 'order',
        name: 'OrderList',
        component: () => import('@/views/order/List.vue'),
        meta: { title: '我的订单', requiresAuth: true }
      },
      {
        path: 'order/:id',
        name: 'OrderDetail',
        component: () => import('@/views/order/Detail.vue'),
        meta: { title: '订单详情', requiresAuth: true }
      },
      {
        path: 'message',
        name: 'Message',
        component: () => import('@/views/Message.vue'),
        meta: { title: '消息通知', requiresAuth: true }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/Profile.vue'),
        meta: { title: '个人中心', requiresAuth: true }
      },
      {
        path: 'auth',
        name: 'Auth',
        component: () => import('@/views/Auth.vue'),
        meta: { title: '实名认证', requiresAuth: true }
      },
      {
        path: 'income',
        name: 'Income',
        component: () => import('@/views/Income.vue'),
        meta: { title: '收益中心', requiresAuth: true }
      },
      {
        path: 'my-publish',
        name: 'MyPublish',
        component: () => import('@/views/MyPublish.vue'),
        meta: { title: '我的发布', requiresAuth: true }
      },
      {
        path: 'favorite',
        name: 'Favorite',
        component: () => import('@/views/Favorite.vue'),
        meta: { title: '我的收藏', requiresAuth: true }
      },
      {
        path: 'credit',
        name: 'Credit',
        component: () => import('@/views/Credit.vue'),
        meta: { title: '信用中心', requiresAuth: true }
      },
      {
        path: 'wallet',
        name: 'Wallet',
        component: () => import('@/views/Wallet.vue'),
        meta: { title: '我的钱包', requiresAuth: true }
      }
    ]
  },
  {
    path: '/admin',
    component: () => import('@/layout/AdminLayout.vue'),
    redirect: '/admin/dashboard',
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/Dashboard.vue'),
        meta: { title: '数据概览' }
      },
      {
        path: 'users',
        name: 'AdminUsers',
        component: () => import('@/views/admin/Users.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'auth-list',
        name: 'AdminAuthList',
        component: () => import('@/views/admin/AuthList.vue'),
        meta: { title: '实名认证审核' }
      },
      {
        path: 'idle-audit',
        name: 'AdminIdleAudit',
        component: () => import('@/views/admin/IdleAudit.vue'),
        meta: { title: '物品审核' }
      },
      {
        path: 'shared-items',
        name: 'AdminSharedItems',
        component: () => import('@/views/admin/SharedItems.vue'),
        meta: { title: '共享物品管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  if (to.meta.title) {
    document.title = `${to.meta.title} - 校园共享经济平台`
  }

  if (to.meta.requiresAuth && !userStore.token) {
    next('/login')
  } else if (to.meta.requiresAdmin && userStore.userInfo?.role !== 'ADMIN') {
    next('/')
  } else {
    next()
  }
})

export default router

