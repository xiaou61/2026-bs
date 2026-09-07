import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import User from '../views/user/index.vue'
import Subject from '../views/subject/index.vue'
import Course from '../views/course/index.vue'
import Bank from '../views/bank/index.vue'
import Question from '../views/question/index.vue'
import Paper from '../views/paper/index.vue'
import ExamRecord from '../views/exam-record/index.vue'
import Plan from '../views/plan/index.vue'
import Notice from '../views/notice/index.vue'

const routes = [
  { path: '/login', component: Login },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: Dashboard },
      { path: 'user', component: User },
      { path: 'subject', component: Subject },
      { path: 'course', component: Course },
      { path: 'bank', component: Bank },
      { path: 'question', component: Question },
      { path: 'paper', component: Paper },
      { path: 'exam-record', component: ExamRecord },
      { path: 'plan', component: Plan },
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
