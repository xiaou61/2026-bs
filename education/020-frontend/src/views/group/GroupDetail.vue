<template>
  <div class="group-detail-container">
    <el-card v-if="group">
      <template #header>
        <div class="header">
          <h2>{{ group.name }}</h2>
          <el-button v-if="!isMember" type="primary" @click="handleJoin">加入小组</el-button>
          <el-button v-else type="danger" @click="handleLeave">退出小组</el-button>
        </div>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="小组分类">
          <el-tag>{{ group.category }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="成员人数">
          {{ group.currentMembers }} / {{ group.maxMembers }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">
          {{ group.createTime }}
        </el-descriptions-item>
        <el-descriptions-item label="小组简介" :span="2">
          {{ group.description }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>
        <h3>小组成员</h3>
      </template>
      <el-table :data="members">
        <el-table-column prop="userId" label="用户ID" />
        <el-table-column prop="role" label="角色">
          <template #default="{ row }">
            <el-tag v-if="row.role === 'creator'" type="danger">创建者</el-tag>
            <el-tag v-else>成员</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="joinTime" label="加入时间" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getGroupDetail, getGroupMembers, joinGroup, leaveGroup } from '@/api/group'
import { ElMessage } from 'element-plus'

const route = useRoute()
const userStore = useUserStore()
const group = ref(null)
const members = ref([])

const isMember = computed(() => {
  return members.value.some(m => m.userId === userStore.userInfo?.id)
})

const loadData = async () => {
  try {
    const res = await getGroupDetail(route.params.id)
    group.value = res.data

    const memberRes = await getGroupMembers(route.params.id)
    members.value = memberRes.data
  } catch (error) {
    console.error(error)
  }
}

const handleJoin = async () => {
  try {
    await joinGroup({ groupId: route.params.id })
    ElMessage.success('加入成功')
    loadData()
  } catch (error) {
    console.error(error)
  }
}

const handleLeave = async () => {
  try {
    await leaveGroup({ groupId: route.params.id })
    ElMessage.success('退出成功')
    loadData()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.group-detail-container {
  max-width: 1200px;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header h2 {
  margin: 0;
}
</style>

