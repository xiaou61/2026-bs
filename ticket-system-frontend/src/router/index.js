import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue')
      },
      {
        path: '/user/profile',
        name: 'Profile',
        component: () => import('../views/User/Profile.vue')
      },
      {
        path: '/user/list',
        name: 'UserList',
        component: () => import('../views/User/UserList.vue')
      },
      {
        path: '/movie/list',
        name: 'MovieList',
        component: () => import('../views/Movie/MovieList.vue')
      },
      {
        path: '/movie/:id',
        name: 'MovieDetail',
        component: () => import('../views/Movie/MovieDetail.vue')
      },
      {
        path: '/cinema/list',
        name: 'CinemaList',
        component: () => import('../views/Cinema/CinemaList.vue')
      },
      {
        path: '/hall/list',
        name: 'HallList',
        component: () => import('../views/Cinema/HallList.vue')
      },
      {
        path: '/showtime/list',
        name: 'ShowtimeList',
        component: () => import('../views/Showtime/ShowtimeList.vue')
      },
      {
        path: '/showtime/:id/seat',
        name: 'SeatSelection',
        component: () => import('../views/Showtime/SeatSelection.vue')
      },
      {
        path: '/order/list',
        name: 'OrderList',
        component: () => import('../views/Order/OrderList.vue')
      },
      {
        path: '/order/my',
        name: 'MyOrders',
        component: () => import('../views/Order/MyOrders.vue')
      },
      {
        path: '/order/:id',
        name: 'OrderDetail',
        component: () => import('../views/Order/OrderDetail.vue')
      },
      {
        path: '/ticket/my',
        name: 'MyTickets',
        component: () => import('../views/Ticket/MyTickets.vue')
      },
      {
        path: '/ticket/:id',
        name: 'TicketDetail',
        component: () => import('../views/Ticket/TicketDetail.vue')
      },
      {
        path: '/comment/list',
        name: 'CommentList',
        component: () => import('../views/Comment/CommentList.vue')
      },
      {
        path: '/coupon/list',
        name: 'CouponList',
        component: () => import('../views/Coupon/CouponList.vue')
      },
      {
        path: '/coupon/my',
        name: 'MyCoupons',
        component: () => import('../views/Coupon/MyCoupons.vue')
      },
      {
        path: '/statistics',
        name: 'Statistics',
        component: () => import('../views/Statistics/Dashboard.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else if (to.path === '/login' && token) {
    next('/')
  } else {
    next()
  }
})

export default router
