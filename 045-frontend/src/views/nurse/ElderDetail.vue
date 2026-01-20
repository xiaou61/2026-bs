<template>
  <div>
    <el-card v-if="elder">
      <template #header>老人信息</template>
      <el-descriptions :column="3" border>
        <el-descriptions-item label="姓名">{{ elder.name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ elder.gender === 1 ? '男' : '女' }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ elder.age }}</el-descriptions-item>
        <el-descriptions-item label="护理等级">{{ careLevelMap[elder.careLevel] }}</el-descriptions-item>
        <el-descriptions-item label="入住日期">{{ elder.checkInDate }}</el-descriptions-item>
        <el-descriptions-item label="健康状况">{{ elder.healthStatus }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card style="margin-top: 20px;">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>护理计划</span>
          <el-button type="primary" size="small" @click="showPlanDialog">添加计划</el-button>
        </div>
      </template>
      <el-table :data="plans">
        <el-table-column prop="planName" label="计划名称" />
        <el-table-column prop="planContent" label="计划内容" />
        <el-table-column prop="frequency" label="执行频率" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '执行中' : '已结束' }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card style="margin-top: 20px;">
      <template #header>健康记录</template>
      <el-table :data="healthRecords">
        <el-table-column prop="recordTime" label="记录时间" />
        <el-table-column label="血压">
          <template #default="{ row }">{{ row.bloodPressureHigh }}/{{ row.bloodPressureLow }}</template>
        </el-table-column>
        <el-table-column prop="heartRate" label="心率" />
        <el-table-column prop="temperature" label="体温" />
        <el-table-column prop="symptoms" label="症状" />
      </el-table>
    </el-card>

    <el-dialog v-model="planDialogVisible" title="添加护理计划" width="500">
      <el-form :model="planForm" label-width="100px">
        <el-form-item label="计划名称"><el-input v-model="planForm.planName" /></el-form-item>
        <el-form-item label="计划内容"><el-input v-model="planForm.planContent" type="textarea" /></el-form-item>
        <el-form-item label="执行频率"><el-input v-model="planForm.frequency" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="planDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPlan">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getElderDetail, getCarePlans, getHealthRecords, addCarePlan } from '@/api'
import { ElMessage } from 'element-plus'

const route = useRoute()
const careLevelMap: Record<number, string> = { 1: '自理', 2: '半护理', 3: '全护理', 4: '特护' }
const elderId = Number(route.params.id)
const elder = ref<any>(null)
const plans = ref<any[]>([])
const healthRecords = ref<any[]>([])
const planDialogVisible = ref(false)
const planForm = ref<any>({})

const loadData = async () => {
  const [elderRes, planRes, healthRes]: any = await Promise.all([
    getElderDetail(elderId),
    getCarePlans(elderId),
    getHealthRecords(elderId, { current: 1, size: 20 })
  ])
  elder.value = elderRes.data
  plans.value = planRes.data
  healthRecords.value = healthRes.data.records
}

const showPlanDialog = () => {
  planForm.value = { elderId }
  planDialogVisible.value = true
}

const submitPlan = async () => {
  await addCarePlan(planForm.value)
  ElMessage.success('添加成功')
  planDialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>
