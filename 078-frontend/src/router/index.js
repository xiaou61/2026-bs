import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  { path: '/register', component: () => import('../views/Register.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/user/List.vue') },
      { path: 'merchant', component: () => import('../views/merchant/List.vue') },
      { path: 'merchant/apply', component: () => import('../views/merchant/Apply.vue') },
      { path: 'merchant/info', component: () => import('../views/merchant/Info.vue') },
      { path: 'category', component: () => import('../views/category/List.vue') },
      { path: 'product', component: () => import('../views/product/List.vue') },
      { path: 'activity', component: () => import('../views/activity/List.vue') },
      { path: 'order', component: () => import('../views/order/List.vue') },
      { path: 'review', component: () => import('../views/review/List.vue') },
      { path: 'notice', component: () => import('../views/notice/List.vue') },
      { path: 'home', component: () => import('../views/home/Index.vue') },
      { path: 'activity/detail/:id', component: () => import('../views/activity/Detail.vue') },
      { path: 'cart', component: () => import('../views/cart/Index.vue') },
      { path: 'address', component: () => import('../views/address/List.vue') },
      { path: 'group', component: () => import('../views/group/List.vue') },
      { path: 'profile', component: () => import('../views/profile/Index.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && to.path !== '/register' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
