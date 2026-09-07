import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: () => import('@/views/layout/MainLayout.vue'),
      children: [
        { path: '', name: 'home', component: () => import('@/views/home/HomePage.vue') },
        { path: 'homestay/:id', name: 'homestayDetail', component: () => import('@/views/homestay/HomestayDetail.vue') },
        { path: 'search', name: 'search', component: () => import('@/views/homestay/SearchPage.vue') },
        { path: 'booking', name: 'myBooking', component: () => import('@/views/user/MyBooking.vue'), meta: { requireAuth: true } },
        { path: 'favorite', name: 'myFavorite', component: () => import('@/views/user/MyFavorite.vue'), meta: { requireAuth: true } },
        { path: 'profile', name: 'profile', component: () => import('@/views/user/UserProfile.vue'), meta: { requireAuth: true } }
      ]
    },
    {
      path: '/host',
      component: () => import('@/views/layout/HostLayout.vue'),
      meta: { requireAuth: true, requireHost: true },
      children: [
        { path: '', name: 'hostHome', component: () => import('@/views/host/HostHomestay.vue') },
        { path: 'homestay', name: 'hostHomestay', component: () => import('@/views/host/HostHomestay.vue') },
        { path: 'homestay/edit/:id?', name: 'homestayEdit', component: () => import('@/views/host/HomestayEdit.vue') },
        { path: 'booking', name: 'hostBooking', component: () => import('@/views/host/HostBooking.vue') }
      ]
    },
    { path: '/login', name: 'login', component: () => import('@/views/auth/LoginPage.vue') },
    { path: '/register', name: 'register', component: () => import('@/views/auth/RegisterPage.vue') }
  ]
})

router.beforeEach((to, _from, next) => {
  const userStore = useUserStore()
  if (to.meta.requireAuth && !userStore.isLogin) {
    next({ name: 'login', query: { redirect: to.fullPath } })
  } else if (to.meta.requireHost && !userStore.isHost && !userStore.isAdmin) {
    next({ name: 'home' })
  } else {
    next()
  }
})

export default router
