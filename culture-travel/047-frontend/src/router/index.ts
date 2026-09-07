import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: () => import('@/views/Login.vue') },
    { path: '/register', component: () => import('@/views/Register.vue') },
    {
      path: '/admin',
      component: () => import('@/layouts/AdminLayout.vue'),
      children: [
        { path: '', redirect: '/admin/dashboard' },
        { path: 'dashboard', component: () => import('@/views/admin/Dashboard.vue') },
        { path: 'user', component: () => import('@/views/admin/UserManage.vue') },
        { path: 'shop', component: () => import('@/views/admin/ShopManage.vue') },
        { path: 'script', component: () => import('@/views/admin/ScriptManage.vue') },
        { path: 'category', component: () => import('@/views/admin/CategoryManage.vue') },
        { path: 'notice', component: () => import('@/views/admin/NoticeManage.vue') }
      ]
    },
    {
      path: '/owner',
      component: () => import('@/layouts/OwnerLayout.vue'),
      children: [
        { path: '', redirect: '/owner/shop' },
        { path: 'shop', component: () => import('@/views/owner/ShopInfo.vue') },
        { path: 'room', component: () => import('@/views/owner/RoomManage.vue') },
        { path: 'script', component: () => import('@/views/owner/ScriptManage.vue') },
        { path: 'reservation', component: () => import('@/views/owner/ReservationManage.vue') }
      ]
    },
    {
      path: '/author',
      component: () => import('@/layouts/AuthorLayout.vue'),
      children: [
        { path: '', redirect: '/author/script' },
        { path: 'script', component: () => import('@/views/author/ScriptList.vue') },
        { path: 'create', component: () => import('@/views/author/CreateScript.vue') },
        { path: 'edit/:id', component: () => import('@/views/author/EditScript.vue') }
      ]
    },
    {
      path: '/user',
      component: () => import('@/layouts/UserLayout.vue'),
      children: [
        { path: '', redirect: '/user/script' },
        { path: 'script', component: () => import('@/views/user/ScriptList.vue') },
        { path: 'script/:id', component: () => import('@/views/user/ScriptDetail.vue') },
        { path: 'shop', component: () => import('@/views/user/ShopList.vue') },
        { path: 'shop/:id', component: () => import('@/views/user/ShopDetail.vue') },
        { path: 'reserve/:shopId', component: () => import('@/views/user/Reserve.vue') },
        { path: 'reservation', component: () => import('@/views/user/MyReservation.vue') },
        { path: 'favorite', component: () => import('@/views/user/Favorite.vue') },
        { path: 'notice', component: () => import('@/views/user/Notice.vue') }
      ]
    },
    { path: '/', redirect: '/login' }
  ]
})

export default router
