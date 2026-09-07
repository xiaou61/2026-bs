import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/user'

const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue'),
        meta: { requiresAuth: false }
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
        meta: { requiresAuth: false }
    },
    {
        path: '/store',
        children: [
            {
                path: '',
                name: 'StoreList',
                component: () => import('@/views/store/StoreList.vue'),
                meta: { requiresAuth: false }
            },
            {
                path: ':id',
                name: 'StoreDetail',
                component: () => import('@/views/store/StoreDetail.vue'),
                meta: { requiresAuth: false }
            }
        ]
    },
    {
        path: '/hairdresser',
        children: [
            {
                path: '',
                name: 'HairdresserList',
                component: () => import('@/views/hairdresser/HairdresserList.vue'),
                meta: { requiresAuth: false }
            },
            {
                path: ':id',
                name: 'HairdresserDetail',
                component: () => import('@/views/hairdresser/HairdresserDetail.vue'),
                meta: { requiresAuth: false }
            }
        ]
    },
    {
        path: '/appointment',
        children: [
            {
                path: 'create',
                name: 'CreateAppointment',
                component: () => import('@/views/appointment/CreateAppointment.vue'),
                meta: { requiresAuth: true }
            },
            {
                path: 'my',
                name: 'MyAppointments',
                component: () => import('@/views/appointment/MyAppointments.vue'),
                meta: { requiresAuth: true }
            }
        ]
    },
    {
        path: '/profile',
        name: 'Profile',
        component: () => import('@/views/Profile.vue'),
        meta: { requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    const userStore = useUserStore()

    if (to.meta.requiresAuth && !userStore.isLoggedIn) {
        // 需要登录但未登录，跳转到登录页
        next({ path: '/login', query: { redirect: to.fullPath } })
    } else {
        next()
    }
})

export default router
