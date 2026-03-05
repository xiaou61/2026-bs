import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import Child from '../views/child/index.vue'
import Donor from '../views/donor/index.vue'
import Application from '../views/application/index.vue'
import Donation from '../views/donation/index.vue'
import Project from '../views/project/index.vue'
import Fund from '../views/fund/index.vue'
import Feedback from '../views/feedback/index.vue'
import Growth from '../views/growth/index.vue'
import Announcement from '../views/announcement/index.vue'
import Sponsor from '../views/sponsor/index.vue'
import User from '../views/user/index.vue'

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
      { path: 'child', component: Child },
      { path: 'donor', component: Donor },
      { path: 'application', component: Application },
      { path: 'donation', component: Donation },
      { path: 'project', component: Project },
      { path: 'fund', component: Fund },
      { path: 'feedback', component: Feedback },
      { path: 'growth', component: Growth },
      { path: 'announcement', component: Announcement },
      { path: 'sponsor', component: Sponsor },
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
