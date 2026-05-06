import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/SysUser.vue') },
      { path: 'item', component: () => import('../views/ExamItem.vue') },
      { path: 'patient', component: () => import('../views/PatientProfile.vue') },
      { path: 'doctor', component: () => import('../views/DoctorProfile.vue') },
      { path: 'appointment', component: () => import('../views/ExamAppointment.vue') },
      { path: 'department', component: () => import('../views/ExamDepartment.vue') },
      { path: 'checkin', component: () => import('../views/CheckinRecord.vue') },
      { path: 'report', component: () => import('../views/ExamReport.vue') },
      { path: 'alert', component: () => import('../views/AbnormalAlert.vue') },
      { path: 'delivery', component: () => import('../views/ReportDelivery.vue') },
      { path: 'advice', component: () => import('../views/RevisitAdvice.vue') },
      { path: 'queue', component: () => import('../views/QueueCall.vue') },
      { path: 'notice', component: () => import('../views/HospitalNotice.vue') },
      { path: 'log', component: () => import('../views/OperationLog.vue') }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) next('/login')
  else if (to.path === '/login' && userStore.token) next('/dashboard')
  else next()
})

export default router











