import { createRouter, createWebHistory } from 'vue-router'
import PublicLayout from '../views/public/PublicLayout.vue'
import Home from '../views/public/Home.vue'
import Discover from '../views/public/Discover.vue'
import Detail from '../views/public/Detail.vue'
import Favorites from '../views/public/Favorites.vue'
import MyUploads from '../views/public/MyUploads.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import AdminLayout from '../views/admin/Layout.vue'
import Dashboard from '../views/admin/Dashboard.vue'
import CategoryAdmin from '../views/admin/Category.vue'
import TagAdmin from '../views/admin/Tag.vue'
import WallpaperAdmin from '../views/admin/Wallpaper.vue'
import AuditAdmin from '../views/admin/Audit.vue'
import BannerAdmin from '../views/admin/Banner.vue'
import NoticeAdmin from '../views/admin/Notice.vue'

const routes = [
  {
    path: '/',
    component: PublicLayout,
    children: [
      { path: '', name: 'home', component: Home },
      { path: 'discover', name: 'discover', component: Discover },
      { path: 'detail/:id', name: 'detail', component: Detail, props: true },
      { path: 'favorites', name: 'favorites', component: Favorites, meta: { requiresAuth: true } },
      { path: 'my-uploads', name: 'my-uploads', component: MyUploads, meta: { requiresAuth: true } }
    ]
  },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  {
    path: '/admin',
    component: AdminLayout,
    redirect: '/admin/dashboard',
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      { path: 'dashboard', component: Dashboard, meta: { requiresAuth: true, requiresAdmin: true } },
      { path: 'category', component: CategoryAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
      { path: 'tag', component: TagAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
      { path: 'wallpaper', component: WallpaperAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
      { path: 'audit', component: AuditAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
      { path: 'banner', component: BannerAdmin, meta: { requiresAuth: true, requiresAdmin: true } },
      { path: 'notice', component: NoticeAdmin, meta: { requiresAuth: true, requiresAdmin: true } }
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
  if (to.meta.requiresAdmin && userInfo?.role !== 'admin') {
    next('/')
    return
  }
  if ((to.path === '/login' || to.path === '/register') && token) {
    next(userInfo?.role === 'admin' ? '/admin/dashboard' : '/')
    return
  }
  next()
})

export default router
