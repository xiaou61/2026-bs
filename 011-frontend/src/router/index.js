import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
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
      component: () => import('@/layout/MainLayout.vue'),
      children: [
        {
          path: '',
          name: 'Home',
          component: () => import('@/views/Home.vue')
        },
        {
          path: 'following',
          name: 'Following',
          component: () => import('@/views/Following.vue')
        },
        {
          path: 'publish',
          name: 'Publish',
          component: () => import('@/views/Publish.vue')
        },
        {
          path: 'notification',
          name: 'Notification',
          component: () => import('@/views/Notification.vue')
        },
        {
          path: 'profile',
          name: 'Profile',
          component: () => import('@/views/Profile.vue')
        },
        {
          path: 'user/:id',
          name: 'User',
          component: () => import('@/views/User.vue')
        },
        {
          path: 'video/:id',
          name: 'VideoDetail',
          component: () => import('@/views/VideoDetail.vue')
        },
        {
          path: 'topic/:id',
          name: 'Topic',
          component: () => import('@/views/Topic.vue')
        },
        {
          path: 'search',
          name: 'Search',
          component: () => import('@/views/Search.vue')
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  
  if (!token && to.path !== '/login' && to.path !== '/register') {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
})

export default router

