import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user/profile', component: () => import('../views/User/Profile.vue') },
      { path: 'user/list', component: () => import('../views/User/UserList.vue'), meta: { roles: ['admin'] } },
      { path: 'bike/list', component: () => import('../views/Bike/BikeList.vue'), meta: { roles: ['admin', 'operator'] } },
      { path: 'station/list', component: () => import('../views/Station/StationList.vue'), meta: { roles: ['admin', 'operator'] } },
      { path: 'ride/start', component: () => import('../views/Ride/StartRide.vue'), meta: { roles: ['user'] } },
      { path: 'ride/status', component: () => import('../views/Ride/RideStatus.vue'), meta: { roles: ['user'] } },
      { path: 'ride/history', component: () => import('../views/Ride/RideHistory.vue'), meta: { roles: ['user'] } },
      { path: 'ride/orders', component: () => import('../views/Ride/OrderList.vue'), meta: { roles: ['admin', 'operator'] } },
      { path: 'wallet/my', component: () => import('../views/Wallet/MyWallet.vue'), meta: { roles: ['user'] } },
      { path: 'wallet/records', component: () => import('../views/Wallet/WalletRecords.vue'), meta: { roles: ['user'] } },
      { path: 'fault/report', component: () => import('../views/Fault/FaultReport.vue'), meta: { roles: ['user'] } },
      { path: 'fault/list', component: () => import('../views/Fault/FaultList.vue'), meta: { roles: ['admin', 'operator'] } },
      { path: 'pricing/list', component: () => import('../views/Pricing/PricingList.vue'), meta: { roles: ['admin'] } },
      { path: 'credit/info', component: () => import('../views/Credit/CreditInfo.vue'), meta: { roles: ['user'] } },
      { path: 'announcement/list', component: () => import('../views/Announcement/AnnouncementList.vue') },
      { path: 'feedback/list', component: () => import('../views/Feedback/FeedbackList.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    next()
  } else if (!token) {
    next('/login')
  } else {
    const roles = to.meta.roles
    if (roles) {
      const user = JSON.parse(localStorage.getItem('user') || '{}')
      if (roles.includes(user.role)) {
        next()
      } else {
        next('/dashboard')
      }
    } else {
      next()
    }
  }
})

export default router
