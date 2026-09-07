import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'supplier', component: () => import('../views/supplier/index.vue') },
      { path: 'customer', component: () => import('../views/customer/index.vue') },
      { path: 'category', component: () => import('../views/category/index.vue') },
      { path: 'product', component: () => import('../views/product/index.vue') },
      { path: 'purchase', component: () => import('../views/purchase/index.vue') },
      { path: 'sale', component: () => import('../views/sale/index.vue') },
      { path: 'stock', component: () => import('../views/stock/index.vue') },
      { path: 'announcement', component: () => import('../views/announcement/index.vue') },
      { path: 'profile', component: () => import('../views/profile/index.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const user = JSON.parse(localStorage.getItem('user') || 'null')
  if (to.path !== '/login' && !token) {
    next('/login')
    return
  }
  if (to.meta.roles && (!user || !to.meta.roles.includes(user.role))) {
    next('/dashboard')
    return
  }
  next()
})

export default router
