import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: () => import('@/views/Login.vue') },
    { path: '/register', component: () => import('@/views/Register.vue') },
    {
      path: '/admin', component: () => import('@/layouts/AdminLayout.vue'),
      children: [
        { path: '', redirect: '/admin/dashboard' },
        { path: 'dashboard', component: () => import('@/views/admin/Dashboard.vue') },
        { path: 'user', component: () => import('@/views/admin/UserManage.vue') },
        { path: 'relic', component: () => import('@/views/admin/RelicManage.vue') },
        { path: 'category', component: () => import('@/views/admin/CategoryManage.vue') },
        { path: 'dynasty', component: () => import('@/views/admin/DynastyManage.vue') },
        { path: 'hall', component: () => import('@/views/admin/HallManage.vue') },
        { path: 'exhibition', component: () => import('@/views/admin/ExhibitionManage.vue') },
        { path: 'research', component: () => import('@/views/admin/ResearchManage.vue') },
        { path: 'reservation', component: () => import('@/views/admin/ReservationManage.vue') },
        { path: 'notice', component: () => import('@/views/admin/NoticeManage.vue') },
      ]
    },
    {
      path: '/guide', component: () => import('@/layouts/GuideLayout.vue'),
      children: [
        { path: '', redirect: '/guide/booking' },
        { path: 'booking', component: () => import('@/views/guide/BookingManage.vue') },
      ]
    },
    {
      path: '/researcher', component: () => import('@/layouts/ResearcherLayout.vue'),
      children: [
        { path: '', redirect: '/researcher/research' },
        { path: 'research', component: () => import('@/views/researcher/ResearchList.vue') },
        { path: 'create', component: () => import('@/views/researcher/CreateResearch.vue') },
        { path: 'edit/:id', component: () => import('@/views/researcher/EditResearch.vue') },
      ]
    },
    {
      path: '/user', component: () => import('@/layouts/UserLayout.vue'),
      children: [
        { path: '', redirect: '/user/relic' },
        { path: 'relic', component: () => import('@/views/user/RelicList.vue') },
        { path: 'relic/:id', component: () => import('@/views/user/RelicDetail.vue') },
        { path: 'exhibition', component: () => import('@/views/user/ExhibitionList.vue') },
        { path: 'exhibition/:id', component: () => import('@/views/user/ExhibitionDetail.vue') },
        { path: 'research', component: () => import('@/views/user/ResearchList.vue') },
        { path: 'research/:id', component: () => import('@/views/user/ResearchDetail.vue') },
        { path: 'reserve/:exhibitionId', component: () => import('@/views/user/Reserve.vue') },
        { path: 'guide-booking', component: () => import('@/views/user/GuideBooking.vue') },
        { path: 'reservation', component: () => import('@/views/user/MyReservation.vue') },
        { path: 'favorite', component: () => import('@/views/user/Favorite.vue') },
        { path: 'notice', component: () => import('@/views/user/Notice.vue') },
      ]
    },
    { path: '/', redirect: '/login' }
  ]
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && to.path !== '/register' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
