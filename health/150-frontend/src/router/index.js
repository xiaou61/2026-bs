import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  DOCTOR: '/appointment',
  TECHNICIAN: '/checkin',
  PATIENT: '/appointment'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'item', component: () => import('../views/ExamItem.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'patient', component: () => import('../views/PatientProfile.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'doctor', component: () => import('../views/DoctorProfile.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'appointment', component: () => import('../views/ExamAppointment.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'department', component: () => import('../views/ExamDepartment.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'checkin', component: () => import('../views/CheckinRecord.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'report', component: () => import('../views/ExamReport.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'alert', component: () => import('../views/AbnormalAlert.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN'] } },
      { path: 'delivery', component: () => import('../views/ReportDelivery.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'advice', component: () => import('../views/RevisitAdvice.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'queue', component: () => import('../views/QueueCall.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'notice', component: () => import('../views/HospitalNotice.vue'), meta: { roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'

  if (to.path !== '/login' && !userStore.token) {
    next('/login')
    return
  }
  if (to.path === '/login' && userStore.token) {
    next(home)
    return
  }
  if (to.path === '/' && userStore.token) {
    next(home)
    return
  }
  if (to.meta?.roles && !canAccess(role, to.meta.roles)) {
    next(home)
    return
  }
  next()
})

export default router
