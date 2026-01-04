import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () =&gt; import('@/views/Layout.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () =&gt; import('@/views/Home.vue')
      },
      {
        path: '/projects',
        name: 'Projects',
        component: () =&gt; import('@/views/Projects.vue')
      },
      {
        path: '/project/:id',
        name: 'ProjectDetail',
        component: () =&gt; import('@/views/ProjectDetail.vue')
      },
      {
        path: '/my',
        name: 'MyCenter',
        component: () =&gt; import('@/views/MyCenter.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () =&gt; import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () =&gt; import('@/views/Register.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
