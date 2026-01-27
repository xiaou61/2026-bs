import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    children: [
      { path: '', name: 'Home', component: () => import('../views/Home.vue') },
      { path: 'courses', name: 'CourseList', component: () => import('../views/CourseList.vue') },
      { path: 'course/:id', name: 'CourseDetail', component: () => import('../views/CourseDetail.vue') },
      { path: 'video/:id', name: 'VideoPlayer', component: () => import('../views/VideoPlayer.vue'), meta: { auth: true } },
      { path: 'my-courses', name: 'MyCourse', component: () => import('../views/MyCourse.vue'), meta: { auth: true } },
      { path: 'favorites', name: 'Favorite', component: () => import('../views/Favorite.vue'), meta: { auth: true } },
      { path: 'profile', name: 'Profile', component: () => import('../views/Profile.vue'), meta: { auth: true } }
    ]
  },
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', component: () => import('../views/Register.vue') }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.auth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
