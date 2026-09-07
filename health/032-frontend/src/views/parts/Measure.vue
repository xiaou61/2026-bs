<template>
  <el-space direction="vertical" fill>
    <template v-if="isElder">
      <el-form inline :model="query">
        <el-form-item>
          <el-select v-model="query.type" placeholder="类型">
            <el-option label="全部" value="" />
            <el-option label="血压" value="BP" />
            <el-option label="体重" value="WEIGHT" />
            <el-option label="身高" value="HEIGHT" />
          </el-select>
        </el-form-item>
        <el-button @click="load">刷新</el-button>
      </el-form>
      <el-card>
        <el-space wrap>
          <el-tag size="large">BMI：{{ assessment.bmi ?? '--' }}</el-tag>
          <el-tag size="large">血压状态：{{ assessment.bpStatus || '--' }}</el-tag>
          <el-tag size="large" :type="riskType(assessment.risk)">风险等级：{{ assessment.risk || '--' }}</el-tag>
        </el-space>
      </el-card>
    </template>
    <el-form v-else inline :model="f">
      <el-form-item><el-input v-model.number="f.elderId" placeholder="老人ID" /></el-form-item>
      <el-form-item>
        <el-select v-model="f.type" placeholder="类型">
          <el-option label="血压" value="BP" />
          <el-option label="体重" value="WEIGHT" />
          <el-option label="身高" value="HEIGHT" />
        </el-select>
      </el-form-item>
      <el-form-item><el-input v-model.number="f.value1" placeholder="值1" /></el-form-item>
      <el-form-item><el-input v-model.number="f.value2" placeholder="值2" /></el-form-item>
      <el-form-item><el-input v-model="f.unit" placeholder="单位" /></el-form-item>
      <el-button type="primary" @click="create">录入</el-button>
      <el-button @click="load">最近</el-button>
    </el-form>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="type" label="类型" />
      <el-table-column prop="value1" label="值1" />
      <el-table-column prop="value2" label="值2" />
      <el-table-column prop="unit" label="单位" />
      <el-table-column prop="measuredAt" label="测量时间" />
    </el-table>
  </el-space>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../../api'

const currentUser = JSON.parse(localStorage.getItem('user') || '{}')
const isElder = currentUser.role === 'ELDER'
const query = reactive({ type: '' })
const f = reactive({ elderId:null, type:'BP', value1:null, value2:null, unit:'', measuredAt:null })
const list = ref([])
const assessment = ref({})

const riskType = (risk) => {
  if (risk === 'HIGH') return 'danger'
  if (risk === 'MEDIUM') return 'warning'
  return 'success'
}

const create = async () => {
  f.measuredAt = new Date().toISOString().slice(0, 19)
  const { data } = await api.post('/api/measurements', f)
  if (data.code !== 0) {
    ElMessage.error(data.message)
    return
  }
  ElMessage.success('录入成功')
  if (f.elderId) {
    load()
  }
}

const load = async () => {
  const path = isElder ? '/api/measurements/me/latest' : `/api/measurements/elder/${f.elderId}/latest`
  const params = new URLSearchParams({ size: '10' })
  const type = isElder ? query.type : f.type
  if (type) {
    params.set('type', type)
  }
  const { data } = await api.get(`${path}?${params.toString()}`)
  if (data.code !== 0) {
    ElMessage.error(data.message)
    return
  }
  list.value = data.data
  if (isElder) {
    const assessmentResp = await api.get('/api/assessment/me')
    if (assessmentResp.data.code === 0) {
      assessment.value = assessmentResp.data.data
    }
  }
}

onMounted(() => {
  if (isElder) {
    load()
  }
})
</script>
