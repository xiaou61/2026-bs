<template>
  <div>
    <el-card>
      <template #header>老人列表</template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="gender" label="性别">
          <template #default="{ row }">{{ row.gender === 1 ? '男' : '女' }}</template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" />
        <el-table-column prop="careLevel" label="护理等级">
          <template #default="{ row }">{{ careLevelMap[row.careLevel] }}</template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" link @click="viewDetail(row.id)">详情</el-button>
            <el-button type="success" link @click="addHealth(row)">添加健康记录</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>

    <el-dialog v-model="healthDialogVisible" title="添加健康记录" width="600">
      <el-form :model="healthForm" label-width="100px">
        <el-form-item label="血压(高压)"><el-input-number v-model="healthForm.bloodPressureHigh" /></el-form-item>
        <el-form-item label="血压(低压)"><el-input-number v-model="healthForm.bloodPressureLow" /></el-form-item>
        <el-form-item label="心率"><el-input-number v-model="healthForm.heartRate" /></el-form-item>
        <el-form-item label="体温"><el-input-number v-model="healthForm.temperature" :precision="1" /></el-form-item>
        <el-form-item label="血糖"><el-input-number v-model="healthForm.bloodSugar" :precision="1" /></el-form-item>
        <el-form-item label="症状描述"><el-input v-model="healthForm.symptoms" type="textarea" /></el-form-item>
        <el-form-item label="用药情况"><el-input v-model="healthForm.medication" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="healthDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitHealth">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getElderList, addHealthRecord } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const careLevelMap: Record<number, string> = { 1: '自理', 2: '半护理', 3: '全护理', 4: '特护' }
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10, status: 1 })
const healthDialogVisible = ref(false)
const healthForm = ref<any>({})

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getElderList(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const viewDetail = (id: number) => {
  router.push(`/nurse/elder/${id}`)
}

const addHealth = (row: any) => {
  healthForm.value = { elderId: row.id }
  healthDialogVisible.value = true
}

const submitHealth = async () => {
  await addHealthRecord(healthForm.value)
  ElMessage.success('添加成功')
  healthDialogVisible.value = false
}

onMounted(loadData)
</script>
