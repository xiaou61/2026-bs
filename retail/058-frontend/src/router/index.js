import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/index.vue') },
      { path: 'category', component: () => import('../views/category/index.vue') },
      { path: 'product', component: () => import('../views/product/index.vue') },
      { path: 'area', component: () => import('../views/area/index.vue') },
      { path: 'route', component: () => import('../views/route/index.vue') },
      { path: 'subscription', component: () => import('../views/subscription/index.vue') },
      { path: 'order', component: () => import('../views/order/index.vue') },
      { path: 'complaint', component: () => import('../views/complaint/index.vue') },
      { path: 'delivery/task', component: () => import('../views/delivery/Task.vue') },
      { path: 'delivery/history', component: () => import('../views/delivery/History.vue') },
      { path: 'my/product', component: () => import('../views/my/Product.vue') },
      { path: 'my/subscription', component: () => import('../views/my/Subscription.vue') },
      { path: 'my/order', component: () => import('../views/my/Order.vue') },
      { path: 'my/address', component: () => import('../views/my/Address.vue') },
      { path: 'my/complaint', component: () => import('../views/my/Complaint.vue') }
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
  } else {
    next()
  }
})

export default router
