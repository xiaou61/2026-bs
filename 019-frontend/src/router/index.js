import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

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
        path: 'sport/record',
        name: 'SportRecord',
        component: () => import('@/views/sport/RecordList.vue'),
        meta: { title: '运动记录' }
      },
      {
        path: 'sport/create',
        name: 'SportCreate',
        component: () => import('@/views/sport/CreateRecord.vue'),
        meta: { title: '运动打卡' }
      },
      {
        path: 'plan/list',
        name: 'PlanList',
        component: () => import('@/views/plan/PlanList.vue'),
        meta: { title: '健身计划' }
      },
      {
        path: 'plan/create',
        name: 'PlanCreate',
        component: () => import('@/views/plan/CreatePlan.vue'),
        meta: { title: '创建计划' }
      },
      {
        path: 'activity/list',
        name: 'ActivityList',
        component: () => import('@/views/activity/ActivityList.vue'),
        meta: { title: '约球活动' }
      },
      {
        path: 'activity/create',
        name: 'ActivityCreate',
        component: () => import('@/views/activity/CreateActivity.vue'),
        meta: { title: '发起活动' }
      },
      {
        path: 'health/profile',
        name: 'HealthProfile',
        component: () => import('@/views/health/HealthProfile.vue'),
        meta: { title: '健康档案' }
      },
      {
        path: 'venue/list',
        name: 'VenueList',
        component: () => import('@/views/venue/VenueList.vue'),
        meta: { title: '场馆预约' }
      },
      {
        path: 'venue/booking',
        name: 'MyBooking',
        component: () => import('@/views/venue/MyBooking.vue'),
        meta: { title: '我的预约' }
      },
      {
        path: 'rank',
        name: 'Rank',
        component: () => import('@/views/Rank.vue'),
        meta: { title: '排行榜' }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/Profile.vue'),
        meta: { title: '个人中心' }
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
  if (to.path !== '/login' && to.path !== '/register' && !userStore.token) {
    next('/login')
  } else {
    next()
  }
})

export default router

