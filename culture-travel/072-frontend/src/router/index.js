import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'spots', name: 'SpotList', component: () => import('../views/SpotList.vue') },
      { path: 'spots/:id', name: 'SpotDetail', component: () => import('../views/SpotDetail.vue') },
      { path: 'routes', name: 'RouteList', component: () => import('../views/RouteList.vue') },
      { path: 'routes/:id', name: 'RouteDetail', component: () => import('../views/RouteDetail.vue') },
      { path: 'ticket/order', name: 'TicketOrder', component: () => import('../views/TicketOrder.vue') },
      { path: 'ticket/orders', name: 'MyOrders', component: () => import('../views/MyOrders.vue') },
      { path: 'hotels', name: 'HotelList', component: () => import('../views/HotelList.vue') },
      { path: 'restaurants', name: 'RestaurantList', component: () => import('../views/RestaurantList.vue') },
      { path: 'activities', name: 'ActivityList', component: () => import('../views/ActivityList.vue') },
      { path: 'notes', name: 'NoteList', component: () => import('../views/NoteList.vue') },
      { path: 'notes/:id', name: 'NoteDetail', component: () => import('../views/NoteDetail.vue') },
      { path: 'notes/edit/:id?', name: 'NoteEdit', component: () => import('../views/NoteEdit.vue') },
      { path: 'reviews', name: 'ReviewList', component: () => import('../views/ReviewList.vue'), meta: { admin: true } },
      { path: 'profile', name: 'Profile', component: () => import('../views/Profile.vue') },
      { path: 'favorites', name: 'MyFavorites', component: () => import('../views/MyFavorites.vue') },
      { path: 'wallet', name: 'MyWallet', component: () => import('../views/Profile.vue') },
      { path: 'announcements', name: 'AnnouncementList', component: () => import('../views/AnnouncementList.vue') },
      { path: 'admin/users', name: 'UserList', component: () => import('../views/AdminPage.vue'), meta: { admin: true } },
      { path: 'admin/spots', name: 'AdminSpotList', component: () => import('../views/AdminPage.vue'), meta: { admin: true } },
      { path: 'admin/orders', name: 'AdminOrders', component: () => import('../views/AdminPage.vue'), meta: { admin: true } }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userStr = localStorage.getItem('user')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else if (to.meta.admin) {
    const user = userStr ? JSON.parse(userStr) : null
    if (user && user.role === 'admin') {
      next()
    } else {
      next('/dashboard')
    }
  } else {
    next()
  }
})

export default router
