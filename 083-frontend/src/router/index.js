import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import Elder from '../views/elder/index.vue'
import Item from '../views/item/index.vue'
import PackagePage from '../views/package/index.vue'
import Appointment from '../views/appointment/index.vue'
import ResultPage from '../views/result/index.vue'
import Warning from '../views/warning/index.vue'
import FollowUp from '../views/follow-up/index.vue'
import Notice from '../views/notice/index.vue'

const routes = [
  { path: '/login', component: Login },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: Dashboard },
      { path: 'elder', component: Elder },
      { path: 'item', component: Item },
      { path: 'package', component: PackagePage },
      { path: 'appointment', component: Appointment },
      { path: 'result', component: ResultPage },
      { path: 'warning', component: Warning },
      { path: 'follow-up', component: FollowUp },
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
