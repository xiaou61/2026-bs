<template>
  <el-container style="height:100%">
    <el-aside width="220px">
      <el-menu :default-active="tab" @select="tab=$event">
        <el-menu-item index="stats">概览</el-menu-item>
        <el-menu-item index="elders">老人</el-menu-item>
        <el-menu-item index="measure">测量</el-menu-item>
        <el-menu-item index="appt">预约</el-menu-item>
        <el-menu-item index="fu">随访</el-menu-item>
        <el-menu-item index="notice">通知</el-menu-item>
        <el-menu-item index="admin" v-if="isAdmin">用户管理</el-menu-item>
        <el-menu-item index="logout">退出</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div>{{user.username}} {{user.role}}</div>
      </el-header>
      <el-main>
        <Stats v-if="tab==='stats'" />
        <Elders v-if="tab==='elders'" />
        <Measure v-if="tab==='measure'" />
        <Appt v-if="tab==='appt'" />
        <FU v-if="tab==='fu'" />
        <Notice v-if="tab==='notice'" />
        <Admin v-if="tab==='admin'" />
      </el-main>
    </el-container>
  </el-container>
</template>
<script setup>
import { ref } from 'vue'
import Stats from './parts/Stats.vue'
import Elders from './parts/Elders.vue'
import Measure from './parts/Measure.vue'
import Appt from './parts/Appt.vue'
import FU from './parts/FU.vue'
import Notice from './parts/Notice.vue'
import Admin from './parts/Admin.vue'
const user = JSON.parse(localStorage.getItem('user')||'{}')
const isAdmin = user.role==='ADMIN'
const tab = ref('stats')
if(!localStorage.getItem('token')) location.href='/login'
if(!user.username) location.href='/login'
if(user.role==='DOCTOR') tab.value='appt'
if(user.role==='ELDER') tab.value='elders'
</script>
