import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/',
    component: () => import('../layout/UserLayout.vue'),
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('../views/Home.vue')
      },
      {
        path: 'publish',
        name: 'Publish',
        component: () => import('../views/Publish.vue')
      },
      {
        path: 'order-detail/:id',
        name: 'OrderDetail',
        component: () => import('../views/OrderDetail.vue')
      },
      {
        path: 'my-orders',
        name: 'MyOrders',
        component: () => import('../views/MyOrders.vue')
      },
      {
        path: 'wallet',
        name: 'Wallet',
        component: () => import('../views/Wallet.vue')
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('../views/Profile.vue')
      },
      {
        path: 'notifications',
        name: 'Notifications',
        component: () => import('../views/Notifications.vue')
      }
    ]
  },
  {
    path: '/admin',
    redirect: '/admin/login'
  },
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('../views/admin/Login.vue')
  },
  {
    path: '/admin',
    component: () => import('../layout/AdminLayout.vue'),
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('../views/admin/Dashboard.vue')
      },
      {
        path: 'users',
        name: 'AdminUsers',
        component: () => import('../views/admin/Users.vue')
      },
      {
        path: 'orders',
        name: 'AdminOrders',
        component: () => import('../views/admin/Orders.vue')
      },
      {
        path: 'complaints',
        name: 'AdminComplaints',
        component: () => import('../views/admin/Complaints.vue')
      },
      {
        path: 'transactions',
        name: 'AdminTransactions',
        component: () => import('../views/admin/Transactions.vue')
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
  
  if (to.path.startsWith('/admin')) {
    if (to.path === '/admin/login') {
      next()
    } else {
      if (token) {
        next()
      } else {
        next('/admin/login')
      }
    }
  } else {
    if (to.path === '/login' || to.path === '/register') {
      next()
    } else {
      if (token) {
        next()
      } else {
        next('/login')
      }
    }
  }
})

export default router

