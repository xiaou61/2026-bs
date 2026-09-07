import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/auth/Login.vue')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('@/views/auth/Register.vue')
    },
    {
      path: '/',
      component: () => import('@/layout/MainLayout.vue'),
      children: [
        {
          path: '',
          name: 'Home',
          component: () => import('@/views/home/Index.vue')
        },
        {
          path: 'house',
          name: 'HouseList',
          component: () => import('@/views/house/List.vue')
        },
        {
          path: 'house/:id',
          name: 'HouseDetail',
          component: () => import('@/views/house/Detail.vue')
        },
        {
          path: 'profile',
          name: 'Profile',
          component: () => import('@/views/home/Profile.vue'),
          meta: { requiresAuth: true }
        }
      ]
    },
    {
      path: '/landlord',
      component: () => import('@/layout/UserLayout.vue'),
      meta: { requiresAuth: true, role: 'landlord' },
      children: [
        {
          path: '',
          name: 'LandlordDashboard',
          component: () => import('@/views/landlord/Dashboard.vue')
        },
        {
          path: 'houses',
          name: 'LandlordHouses',
          component: () => import('@/views/landlord/Houses.vue')
        },
        {
          path: 'house/publish',
          name: 'PublishHouse',
          component: () => import('@/views/landlord/PublishHouse.vue')
        },
        {
          path: 'house/edit/:id',
          name: 'EditHouse',
          component: () => import('@/views/landlord/PublishHouse.vue')
        },
        {
          path: 'appointments',
          name: 'LandlordAppointments',
          component: () => import('@/views/landlord/Appointments.vue')
        },
        {
          path: 'contracts',
          name: 'LandlordContracts',
          component: () => import('@/views/landlord/Contracts.vue')
        },
        {
          path: 'bills',
          name: 'LandlordBills',
          component: () => import('@/views/landlord/Bills.vue')
        },
        {
          path: 'repairs',
          name: 'LandlordRepairs',
          component: () => import('@/views/landlord/Repairs.vue')
        }
      ]
    },
    {
      path: '/tenant',
      component: () => import('@/layout/UserLayout.vue'),
      meta: { requiresAuth: true, role: 'tenant' },
      children: [
        {
          path: '',
          name: 'TenantDashboard',
          component: () => import('@/views/tenant/Dashboard.vue')
        },
        {
          path: 'favorites',
          name: 'TenantFavorites',
          component: () => import('@/views/tenant/Favorites.vue')
        },
        {
          path: 'appointments',
          name: 'TenantAppointments',
          component: () => import('@/views/tenant/Appointments.vue')
        },
        {
          path: 'contracts',
          name: 'TenantContracts',
          component: () => import('@/views/tenant/Contracts.vue')
        },
        {
          path: 'bills',
          name: 'TenantBills',
          component: () => import('@/views/tenant/Bills.vue')
        },
        {
          path: 'repairs',
          name: 'TenantRepairs',
          component: () => import('@/views/tenant/Repairs.vue')
        }
      ]
    },
    {
      path: '/admin',
      component: () => import('@/layout/AdminLayout.vue'),
      meta: { requiresAuth: true, role: 'admin' },
      children: [
        {
          path: '',
          name: 'AdminDashboard',
          component: () => import('@/views/admin/Dashboard.vue')
        },
        {
          path: 'users',
          name: 'AdminUsers',
          component: () => import('@/views/admin/Users.vue')
        },
        {
          path: 'houses',
          name: 'AdminHouses',
          component: () => import('@/views/admin/Houses.vue')
        },
        {
          path: 'contracts',
          name: 'AdminContracts',
          component: () => import('@/views/admin/Contracts.vue')
        }
      ]
    }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userInfo = JSON.parse(localStorage.getItem('userInfo') || 'null')

  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else if (to.meta.role && userInfo?.role !== to.meta.role) {
    next('/')
  } else {
    next()
  }
})

export default router
