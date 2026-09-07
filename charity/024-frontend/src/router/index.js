import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home,
            redirect: '/pet/list',
            children: [
                {
                    path: 'pet/list',
                    name: 'petList',
                    component: () => import('../views/pet/PetList.vue')
                },
                {
                    path: 'pet/add',
                    name: 'petAdd',
                    component: () => import('../views/pet/PetForm.vue')
                },
                {
                    path: 'pet/edit/:id',
                    name: 'petEdit',
                    component: () => import('../views/pet/PetForm.vue')
                }
            ]
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/Login.vue')
        },
        {
            path: '/register',
            name: 'register',
            component: () => import('../views/Register.vue')
        }
    ]
})

export default router
