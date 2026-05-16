import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  ADMISSION: '/allocation',
  NURSING: '/plan',
  CAREGIVER: '/care',
  FAMILY: '/family',
  DIRECTOR: '/area'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'ADMISSION', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'area', component: () => import('../views/CareArea.vue'), meta: { roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] } },
      { path: 'room', component: () => import('../views/RoomProfile.vue'), meta: { roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] } },
      { path: 'bed', component: () => import('../views/BedProfile.vue'), meta: { roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] } },
      { path: 'elder', component: () => import('../views/ElderProfile.vue'), meta: { roles: ['ADMIN', 'ADMISSION', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] } },
      { path: 'family', component: () => import('../views/FamilyProfile.vue'), meta: { roles: ['ADMIN', 'ADMISSION', 'NURSING', 'FAMILY'] } },
      { path: 'assessment', component: () => import('../views/CheckinAssessment.vue'), meta: { roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] } },
      { path: 'allocation', component: () => import('../views/BedAllocation.vue'), meta: { roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] } },
      { path: 'plan', component: () => import('../views/CarePlan.vue'), meta: { roles: ['ADMIN', 'NURSING', 'CAREGIVER', 'DIRECTOR'] } },
      { path: 'care', component: () => import('../views/CareRecord.vue'), meta: { roles: ['ADMIN', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] } },
      { path: 'query', component: () => import('../views/FamilyQuery.vue'), meta: { roles: ['ADMIN', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] } },
      { path: 'exception', component: () => import('../views/ExceptionReport.vue'), meta: { roles: ['ADMIN', 'ADMISSION', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'DIRECTOR'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'
  if (to.path !== '/login' && !userStore.token) return next('/login')
  if (to.path === '/login' && userStore.token) return next(home)
  if (to.meta?.roles && !to.meta.roles.includes(role)) return next(home)
  next()
})
export default router
