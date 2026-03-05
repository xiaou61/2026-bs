import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import Order from '../views/order/index.vue'
import Technician from '../views/technician/index.vue'
import Category from '../views/category/index.vue'
import Part from '../views/part/index.vue'
import Notice from '../views/notice/index.vue'
import Evaluate from '../views/evaluate/index.vue'
import User from '../views/user/index.vue'
import TechnicianWorkbench from '../views/technician-workbench/index.vue'

const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: Dashboard },
      { path: 'order', component: Order },
      { path: 'technician', component: Technician },
      { path: 'technician-workbench', component: TechnicianWorkbench },
      { path: 'category', component: Category },
      { path: 'part', component: Part },
      { path: 'notice', component: Notice },
      { path: 'evaluate', component: Evaluate },
      { path: 'user', component: User }
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
