import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
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
      component: () => import('../layout/Layout.vue'),
      redirect: '/home',
      children: [
        {
          path: 'home',
          name: 'Home',
          component: () => import('../views/Home.vue')
        },
        {
          path: 'pets',
          name: 'Pets',
          component: () => import('../views/Pets.vue')
        },
        {
          path: 'providers',
          name: 'Providers',
          component: () => import('../views/Providers.vue')
        },
        {
          path: 'provider/:id',
          name: 'ProviderDetail',
          component: () => import('../views/ProviderDetail.vue')
        },
        {
          path: 'bookings',
          name: 'Bookings',
          component: () => import('../views/Bookings.vue')
        },
        {
          path: 'profile',
          name: 'Profile',
          component: () => import('../views/Profile.vue')
        }
      ]
    }
  ]
})

router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('token')
  if (!token && to.path !== '/login' && to.path !== '/register') {
    next('/login')
  } else {
    next()
  }
})

export default router
