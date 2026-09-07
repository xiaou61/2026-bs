import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
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
      path: '/student',
      component: () => import('@/layout/StudentLayout.vue'),
      redirect: '/student/course',
      children: [
        {
          path: 'course',
          name: 'StudentCourse',
          component: () => import('@/views/student/CourseList.vue')
        },
        {
          path: 'my-courses',
          name: 'MyCourses',
          component: () => import('@/views/student/MyCourses.vue')
        },
        {
          path: 'grades',
          name: 'MyGrades',
          component: () => import('@/views/student/Grades.vue')
        }
      ]
    },
    {
      path: '/teacher',
      component: () => import('@/layout/TeacherLayout.vue'),
      redirect: '/teacher/courses',
      children: [
        {
          path: 'courses',
          name: 'TeacherCourses',
          component: () => import('@/views/teacher/MyCourses.vue')
        },
        {
          path: 'course/:id/students',
          name: 'CourseStudents',
          component: () => import('@/views/teacher/CourseStudents.vue')
        },
        {
          path: 'grades',
          name: 'TeacherGrades',
          component: () => import('@/views/teacher/GradeManage.vue')
        }
      ]
    },
    {
      path: '/admin',
      component: () => import('@/layout/AdminLayout.vue'),
      redirect: '/admin/students',
      children: [
        {
          path: 'students',
          name: 'AdminStudents',
          component: () => import('@/views/admin/StudentManage.vue')
        },
        {
          path: 'teachers',
          name: 'AdminTeachers',
          component: () => import('@/views/admin/TeacherManage.vue')
        },
        {
          path: 'courses',
          name: 'AdminCourses',
          component: () => import('@/views/admin/CourseManage.vue')
        },
        {
          path: 'notices',
          name: 'AdminNotices',
          component: () => import('@/views/admin/NoticeManage.vue')
        },
        {
          path: 'config',
          name: 'SystemConfig',
          component: () => import('@/views/admin/SystemConfig.vue')
        }
      ]
    },
    {
      path: '/',
      redirect: '/login'
    }
  ]
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const user = JSON.parse(localStorage.getItem('user') || 'null')

  if (to.path === '/login' || to.path === '/register') {
    next()
  } else {
    if (!token) {
      next('/login')
    } else {
      if (to.path.startsWith('/student') && user?.role !== 'student') {
        next('/login')
      } else if (to.path.startsWith('/teacher') && user?.role !== 'teacher') {
        next('/login')
      } else if (to.path.startsWith('/admin') && user?.role !== 'admin') {
        next('/login')
      } else {
        next()
      }
    }
  }
})

export default router

