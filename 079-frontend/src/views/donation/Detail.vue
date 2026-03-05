<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <el-button @click="$router.back()">返回</el-button>
        <el-button type="primary" @click="donateDialog = true" style="float: right" v-if="project.status === 0">我要捐赠</el-button>
      </template>
      <h2>{{ project.name }}</h2>
      <el-progress :percentage="Math.round((project.currentAmount || 0) / (project.targetAmount || 1) * 100)" :stroke-width="15" style="margin: 20px 0" />
      <el-descriptions :column="2" border>
        <el-descriptions-item label="目标金额">¥{{ project.targetAmount }}</el-descriptions-item>
        <el-descriptions-item label="已筹金额">¥{{ project.currentAmount }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ project.startTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ project.endTime }}</el-descriptions-item>
      </el-descriptions>
      <div style="margin-top: 20px">{{ project.description }}</div>
    </el-card>
    <el-card style="margin-top: 15px">
      <template #header>捐赠记录</template>
      <el-table :data="records">
        <el-table-column prop="userName" label="捐赠人" />
        <el-table-column prop="amount" label="金额">
          <template #default="{ row }">¥{{ row.amount }}</template>
        </el-table-column>
        <el-table-column prop="message" label="留言" />
        <el-table-column prop="createTime" label="时间" />
        <el-table-column prop="status" label="状态" v-if="userStore.isAdmin()">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已确认' : '待确认' }}</el-tag>
            <el-button link type="primary" @click="handleConfirm(row.id)" v-if="row.status === 0">确认</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="donateDialog" title="捐赠" width="400px">
      <el-form :model="donateForm" label-width="80px">
        <el-form-item label="金额"><el-input-number v-model="donateForm.amount" :min="1" /></el-form-item>
        <el-form-item label="留言"><el-input v-model="donateForm.message" type="textarea" /></el-form-item>
        <el-form-item label="匿名"><el-switch v-model="donateForm.isAnonymous" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="donateDialog = false">取消</el-button>
        <el-button type="primary" @click="handleDonate">确认捐赠</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getDonationProjectById, getDonationRecords, donate, confirmDonation } from '../../api'
import { useUserStore } from '../../store/user'

const route = useRoute()
const userStore = useUserStore()
const project = ref({})
const records = ref([])
const donateDialog = ref(false)
const donateForm = reactive({ amount: 100, message: '', isAnonymous: 0 })

const loadData = async () => {
  const [projectRes, recordRes] = await Promise.all([
    getDonationProjectById(route.params.id),
    getDonationRecords({ projectId: route.params.id, pageNum: 1, pageSize: 100 })
  ])
  project.value = projectRes.data
  records.value = recordRes.data.records
}

const handleDonate = async () => {
  await donate({ ...donateForm, projectId: route.params.id })
  ElMessage.success('捐赠成功，等待确认')
  donateDialog.value = false
  loadData()
}

const handleConfirm = async (id) => {
  await confirmDonation(id)
  ElMessage.success('已确认')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
h2 { margin-bottom: 10px; }
</style>
