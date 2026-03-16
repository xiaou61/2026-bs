import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import Category from '../views/category/index.vue'
import Course from '../views/course/index.vue'
import Indicator from '../views/indicator/index.vue'
import Task from '../views/task/index.vue'
import Evaluation from '../views/evaluation/index.vue'
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
      { path: 'course', component: Course },
      { path: 'indicator', component: Indicator },
      { path: 'task', component: Task },
      { path: 'evaluation', component: Evaluation },
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
