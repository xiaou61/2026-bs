import { createRouter, createWebHistory } from 'vue-router'
import PortalLayout from '../views/portal/PortalLayout.vue'
import Home from '../views/portal/Home.vue'
import ChildList from '../views/portal/ChildList.vue'
import ChildDetail from '../views/portal/ChildDetail.vue'
import Profile from '../views/portal/Profile.vue'
import Application from '../views/portal/Application.vue'
import Progress from '../views/portal/Progress.vue'
import Notice from '../views/portal/Notice.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import ChildAdmin from '../views/admin/child/index.vue'
import AdopterAdmin from '../views/admin/adopter/index.vue'
import ApplicationAdmin from '../views/admin/application/index.vue'
import ReviewAdmin from '../views/admin/review/index.vue'
import MatchAdmin from '../views/admin/match/index.vue'
import AgreementAdmin from '../views/admin/agreement/index.vue'
import FollowAdmin from '../views/admin/follow/index.vue'
import NoticeAdmin from '../views/admin/notice/index.vue'

const routes = [
  {
    path: '/',
    component: PortalLayout,
    children: [
      { path: '', component: Home },
      { path: 'children', component: ChildList },
      { path: 'children/:id', component: ChildDetail, props: true },
      { path: 'profile', component: Profile, meta: { requiresAuth: true } },
      { path: 'application', component: Application, meta: { requiresAuth: true } },
      { path: 'progress', component: Progress, meta: { requiresAuth: true } },
      { path: 'notices', component: Notice }
    ]
  },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/dashboard',
    meta: { requiresAuth: true, requiresStaff: true },
    children: [
      { path: 'dashboard', component: Dashboard, meta: { requiresAuth: true, requiresStaff: true } },
      { path: 'child', component: ChildAdmin, meta: { requiresAuth: true, requiresStaff: true } },
      { path: 'adopter', component: AdopterAdmin, meta: { requiresAuth: true, requiresStaff: true } },
      { path: 'application', component: ApplicationAdmin, meta: { requiresAuth: true, requiresStaff: true } },
      { path: 'review', component: ReviewAdmin, meta: { requiresAuth: true, requiresStaff: true } },
      { path: 'match', component: MatchAdmin, meta: { requiresAuth: true, requiresStaff: true } },
      { path: 'agreement', component: AgreementAdmin, meta: { requiresAuth: true, requiresStaff: true } },
      { path: 'follow', component: FollowAdmin, meta: { requiresAuth: true, requiresStaff: true } },
      { path: 'notice', component: NoticeAdmin, meta: { requiresAuth: true, requiresStaff: true } }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userInfo = JSON.parse(localStorage.getItem('userInfo') || 'null')
  if (to.meta.requiresAuth && !token) {
    next('/login')
    return
  }
  if (to.meta.requiresStaff && !['admin', 'reviewer'].includes(userInfo?.role)) {
    next('/')
    return
  }
  if ((to.path === '/login' || to.path === '/register') && token) {
    next(['admin', 'reviewer'].includes(userInfo?.role) ? '/admin/dashboard' : '/')
    return
  }
  next()
})

export default router
