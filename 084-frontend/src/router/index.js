import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import Category from '../views/category/index.vue'
import Material from '../views/material/index.vue'
import Audit from '../views/audit/index.vue'
import Favorite from '../views/favorite/index.vue'
import Notice from '../views/notice/index.vue'

const routes = [
  { path: '/login', component: Login },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: Dashboard },
      { path: 'category', component: Category },
      { path: 'material', component: Material },
      { path: 'audit', component: Audit },
      { path: 'favorite', component: Favorite },
      { path: 'notice', component: Notice }
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
    return
  }
  next()
})

export default router
