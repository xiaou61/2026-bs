import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/layout/index.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('@/views/home/index.vue'),
        meta: { title: '首页' }
      },
      {
        path: '/product/:id',
        name: 'ProductDetail',
        component: () => import('@/views/product/detail.vue'),
        meta: { title: '商品详情' }
      },
      {
        path: '/publish',
        name: 'Publish',
        component: () => import('@/views/product/publish.vue'),
        meta: { title: '发布商品', requiresAuth: true }
      },
      {
        path: '/my-products',
        name: 'MyProducts',
        component: () => import('@/views/product/my.vue'),
        meta: { title: '我的发布', requiresAuth: true }
      },
      {
        path: '/favorites',
        name: 'Favorites',
        component: () => import('@/views/favorites/index.vue'),
        meta: { title: '我的收藏', requiresAuth: true }
      },
      {
        path: '/chat',
        name: 'ChatList',
        component: () => import('@/views/chat/list.vue'),
        meta: { title: '消息列表', requiresAuth: true }
      },
      {
        path: '/chat/:userId/:productId',
        name: 'ChatDetail',
        component: () => import('@/views/chat/detail.vue'),
        meta: { title: '聊天详情', requiresAuth: true }
      },
      {
        path: '/profile',
        name: 'Profile',
        component: () => import('@/views/user/profile.vue'),
        meta: { title: '个人中心', requiresAuth: true }
      },
      {
        path: '/user/:id',
        name: 'UserProfile',
        component: () => import('@/views/user/public-profile.vue'),
        meta: { title: '用户资料' }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/auth/register.vue'),
    meta: { title: '注册' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()

  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - 校园二手交易平台` : '校园二手交易平台'

  // 检查是否需要登录
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next('/login')
    return
  }

  next()
})

export default router