import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import Layout from '@/layout/Layout.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/Home.vue')
      },
      {
        path: 'resource',
        name: 'Resource',
        component: () => import('@/views/resource/ResourceList.vue')
      },
      {
        path: 'resource/:id',
        name: 'ResourceDetail',
        component: () => import('@/views/resource/ResourceDetail.vue')
      },
      {
        path: 'resource/upload',
        name: 'ResourceUpload',
        component: () => import('@/views/resource/ResourceUpload.vue')
      },
      {
        path: 'group',
        name: 'Group',
        component: () => import('@/views/group/GroupList.vue')
      },
      {
        path: 'group/:id',
        name: 'GroupDetail',
        component: () => import('@/views/group/GroupDetail.vue')
      },
      {
        path: 'question',
        name: 'Question',
        component: () => import('@/views/question/QuestionList.vue')
      },
      {
        path: 'question/practice',
        name: 'QuestionPractice',
        component: () => import('@/views/question/QuestionPractice.vue')
      },
      {
        path: 'question/wrong',
        name: 'WrongQuestion',
        component: () => import('@/views/question/WrongQuestion.vue')
      },
      {
        path: 'qa',
        name: 'QA',
        component: () => import('@/views/qa/QAList.vue')
      },
      {
        path: 'qa/:id',
        name: 'QADetail',
        component: () => import('@/views/qa/QADetail.vue')
      },
      {
        path: 'qa/ask',
        name: 'QAAsk',
        component: () => import('@/views/qa/QAAsk.vue')
      },
      {
        path: 'note',
        name: 'Note',
        component: () => import('@/views/note/NoteList.vue')
      },
      {
        path: 'note/:id',
        name: 'NoteDetail',
        component: () => import('@/views/note/NoteDetail.vue')
      },
      {
        path: 'note/edit',
        name: 'NoteEdit',
        component: () => import('@/views/note/NoteEdit.vue')
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/Profile.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && to.path !== '/register' && !userStore.isLoggedIn()) {
    next('/login')
  } else {
    next()
  }
})

export default router

