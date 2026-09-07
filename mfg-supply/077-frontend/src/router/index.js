import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/index.vue'), meta: { role: 'ADMIN' } },
      { path: 'category', component: () => import('../views/category/index.vue'), meta: { role: 'ADMIN' } },
      { path: 'item', component: () => import('../views/item/index.vue') },
      { path: 'market', component: () => import('../views/item/Market.vue') },
      { path: 'order', component: () => import('../views/order/index.vue'), meta: { role: 'ADMIN' } },
      { path: 'my-order', component: () => import('../views/order/MyOrder.vue') },
      { path: 'sale-order', component: () => import('../views/order/SaleOrder.vue') },
      { path: 'review', component: () => import('../views/review/index.vue'), meta: { role: 'ADMIN' } },
      { path: 'favorite', component: () => import('../views/favorite/index.vue') },
      { path: 'complaint', component: () => import('../views/complaint/index.vue'), meta: { role: 'ADMIN' } },
      { path: 'my-complaint', component: () => import('../views/complaint/MyComplaint.vue') },
      { path: 'profile', component: () => import('../views/profile/index.vue') },
      { path: 'announcement', component: () => import('../views/announcement/index.vue') }
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
  if (to.meta.role && user?.role !== to.meta.role) {
    next('/dashboard')
    return
  }
  next()
})

export default router
