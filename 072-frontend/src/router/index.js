import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'spots', name: 'SpotList', component: () => import('../views/Spot/SpotList.vue') },
      { path: 'spots/:id', name: 'SpotDetail', component: () => import('../views/Spot/SpotDetail.vue') },
      { path: 'routes', name: 'RouteList', component: () => import('../views/Route/RouteList.vue') },
      { path: 'routes/:id', name: 'RouteDetail', component: () => import('../views/Route/RouteDetail.vue') },
      { path: 'ticket/order', name: 'TicketOrder', component: () => import('../views/Ticket/TicketOrder.vue') },
      { path: 'ticket/orders', name: 'MyOrders', component: () => import('../views/Ticket/MyOrders.vue') },
      { path: 'hotels', name: 'HotelList', component: () => import('../views/Hotel/HotelList.vue') },
      { path: 'restaurants', name: 'RestaurantList', component: () => import('../views/Restaurant/RestaurantList.vue') },
      { path: 'activities', name: 'ActivityList', component: () => import('../views/Activity/ActivityList.vue') },
      { path: 'notes', name: 'NoteList', component: () => import('../views/Note/NoteList.vue') },
      { path: 'notes/:id', name: 'NoteDetail', component: () => import('../views/Note/NoteDetail.vue') },
      { path: 'notes/edit/:id?', name: 'NoteEdit', component: () => import('../views/Note/NoteEdit.vue') },
      { path: 'reviews', name: 'ReviewList', component: () => import('../views/Review/ReviewList.vue'), meta: { admin: true } },
      { path: 'profile', name: 'Profile', component: () => import('../views/User/Profile.vue') },
      { path: 'favorites', name: 'MyFavorites', component: () => import('../views/User/MyFavorites.vue') },
      { path: 'wallet', name: 'MyWallet', component: () => import('../views/User/MyWallet.vue') },
      { path: 'announcements', name: 'AnnouncementList', component: () => import('../views/Announcement/AnnouncementList.vue') },
      { path: 'admin/users', name: 'UserList', component: () => import('../views/Admin/UserList.vue'), meta: { admin: true } },
      { path: 'admin/spots', name: 'AdminSpotList', component: () => import('../views/Admin/SpotManage.vue'), meta: { admin: true } },
      { path: 'admin/orders', name: 'AdminOrders', component: () => import('../views/Admin/OrderManage.vue'), meta: { admin: true } }
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
