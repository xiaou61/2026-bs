<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <el-button @click="$router.back()">返回</el-button>
        <el-button type="primary" @click="handleSign" v-if="!activity.signed && activity.status === 0" style="float: right">立即报名</el-button>
        <el-button @click="handleCancelSign" v-if="activity.signed && activity.status === 0" style="float: right">取消报名</el-button>
      </template>
      <h2>{{ activity.title }}</h2>
      <el-descriptions :column="2" border style="margin-top: 20px">
        <el-descriptions-item label="活动地点">{{ activity.address }}</el-descriptions-item>
        <el-descriptions-item label="组织者">{{ activity.organizerName }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ activity.startTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ activity.endTime }}</el-descriptions-item>
        <el-descriptions-item label="报名截止">{{ activity.signDeadline }}</el-descriptions-item>
        <el-descriptions-item label="报名人数">{{ activity.currentCount }}/{{ activity.maxCount }}</el-descriptions-item>
        <el-descriptions-item label="活动状态">
          <el-tag :type="activity.status === 0 ? 'success' : activity.status === 1 ? 'warning' : 'info'">
            {{ activity.status === 0 ? '报名中' : activity.status === 1 ? '进行中' : '已结束' }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
      <div class="content" v-html="activity.content" style="margin-top: 20px"></div>
    </el-card>
    <el-card style="margin-top: 15px" v-if="userStore.isAdmin()">
      <template #header>报名列表</template>
      <el-table :data="signs">
        <el-table-column prop="userName" label="姓名" />
        <el-table-column prop="userPhone" label="电话" />
        <el-table-column prop="signTime" label="报名时间" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : ''">{{ row.status === 1 ? '已签到' : '未签到' }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getActivityById, getActivitySigns, signActivity, cancelSignActivity } from '../../api'
import { useUserStore } from '../../store/user'

const route = useRoute()
const userStore = useUserStore()
const activity = ref({})
const signs = ref([])

const loadData = async () => {
  const res = await getActivityById(route.params.id)
  activity.value = res.data
  if (userStore.isAdmin()) {
    const signRes = await getActivitySigns(route.params.id)
    signs.value = signRes.data
  }
}

const handleSign = async () => {
  await signActivity(route.params.id)
  ElMessage.success('报名成功')
  loadData()
}

const handleCancelSign = async () => {
  await cancelSignActivity(route.params.id)
  ElMessage.success('已取消报名')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
h2 { margin-bottom: 10px; }
.content { line-height: 1.8; }
</style>
