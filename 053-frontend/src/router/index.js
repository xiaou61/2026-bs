import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'disaster', name: 'Disaster', component: () => import('../views/disaster/index.vue') },
      { path: 'category', name: 'Category', component: () => import('../views/material/Category.vue') },
      { path: 'material', name: 'Material', component: () => import('../views/material/Material.vue') },
      { path: 'warehouse', name: 'Warehouse', component: () => import('../views/warehouse/index.vue') },
      { path: 'stock', name: 'Stock', component: () => import('../views/warehouse/Stock.vue') },
      { path: 'dispatch', name: 'Dispatch', component: () => import('../views/dispatch/index.vue') },
      { path: 'task', name: 'Task', component: () => import('../views/task/index.vue') },
      { path: 'notice', name: 'Notice', component: () => import('../views/notice/index.vue') },
      { path: 'user', name: 'User', component: () => import('../views/user/index.vue') }
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
