<template>
  <div class="health-profile">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>添加健康记录</span>
          </template>
          
          <el-form :model="form" label-width="120px">
            <el-form-item label="记录日期">
              <el-date-picker
                v-model="form.recordDate"
                type="date"
                placeholder="选择日期"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
            
            <el-form-item label="体重(kg)">
              <el-input-number v-model="form.weight" :min="30" :max="200" :precision="1" />
            </el-form-item>
            
            <el-form-item label="体脂率(%)">
              <el-input-number v-model="form.bodyFat" :min="0" :max="50" :precision="1" />
            </el-form-item>
            
            <el-form-item label="肌肉量(kg)">
              <el-input-number v-model="form.muscleMass" :min="0" :max="100" :precision="1" />
            </el-form-item>
            
            <el-form-item label="饮水量(ml)">
              <el-input-number v-model="form.waterIntake" :min="0" :max="10000" :step="100" />
            </el-form-item>
            
            <el-form-item label="睡眠时长(小时)">
              <el-input-number v-model="form.sleepHours" :min="0" :max="24" :precision="1" />
            </el-form-item>
            
            <el-form-item label="饮食卡路里">
              <el-input-number v-model="form.dietCalories" :min="0" :max="5000" />
            </el-form-item>
            
            <el-form-item label="饮食记录">
              <el-input
                v-model="form.dietRecord"
                type="textarea"
                :rows="3"
                placeholder="记录今天的饮食..."
              />
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="handleSubmit" :loading="loading">
                添加记录
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>体重趋势</span>
          </template>
          <div ref="weightChart" style="width: 100%; height: 300px;"></div>
        </el-card>
        
        <el-card style="margin-top: 20px;">
          <template #header>
            <span>体脂率趋势</span>
          </template>
          <div ref="bodyFatChart" style="width: 100%; height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-card style="margin-top: 20px;">
      <template #header>
        <span>健康记录列表</span>
      </template>
      
      <el-table :data="records" style="width: 100%">
        <el-table-column prop="recordDate" label="日期" width="120" />
        <el-table-column prop="weight" label="体重(kg)" width="100" />
        <el-table-column prop="bmi" label="BMI" width="100" />
        <el-table-column prop="bodyFat" label="体脂率(%)" width="100" />
        <el-table-column prop="muscleMass" label="肌肉量(kg)" width="100" />
        <el-table-column prop="waterIntake" label="饮水(ml)" width="100" />
        <el-table-column prop="sleepHours" label="睡眠(h)" width="100" />
        <el-table-column prop="dietCalories" label="卡路里" width="100" />
        <el-table-column prop="dietRecord" label="饮食记录" min-width="150" show-overflow-tooltip />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { createHealthRecord, getHealthRecords, getHealthTrend } from '@/api/health'
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'

const loading = ref(false)
const records = ref([])
const weightChart = ref(null)
const bodyFatChart = ref(null)

const form = reactive({
  recordDate: new Date().toISOString().split('T')[0],
  weight: 0,
  bodyFat: 0,
  muscleMass: 0,
  waterIntake: 2000,
  sleepHours: 8,
  dietCalories: 0,
  dietRecord: ''
})

const handleSubmit = async () => {
  loading.value = true
  try {
    await createHealthRecord(form)
    ElMessage.success('添加成功')
    loadRecords()
    loadTrend()
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const loadRecords = async () => {
  try {
    const res = await getHealthRecords({ page: 1, size: 30 })
    records.value = res.data.records || []
  } catch (error) {
    console.error(error)
  }
}

const loadTrend = async () => {
  try {
    const res = await getHealthTrend({ days: 30 })
    const data = res.data || []
    renderWeightChart(data)
    renderBodyFatChart(data)
  } catch (error) {
    console.error(error)
  }
}

const renderWeightChart = (data) => {
  const chart = echarts.init(weightChart.value)
  const dates = data.map(item => item.recordDate).reverse()
  const weights = data.map(item => item.weight).reverse()
  
  chart.setOption({
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: dates
    },
    yAxis: {
      type: 'value',
      name: '体重(kg)'
    },
    series: [{
      data: weights,
      type: 'line',
      smooth: true,
      itemStyle: {
        color: '#1890ff'
      }
    }]
  })
}

const renderBodyFatChart = (data) => {
  const chart = echarts.init(bodyFatChart.value)
  const dates = data.map(item => item.recordDate).reverse()
  const bodyFats = data.map(item => item.bodyFat).reverse()
  
  chart.setOption({
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: dates
    },
    yAxis: {
      type: 'value',
      name: '体脂率(%)'
    },
    series: [{
      data: bodyFats,
      type: 'line',
      smooth: true,
      itemStyle: {
        color: '#52c41a'
      }
    }]
  })
}

onMounted(() => {
  loadRecords()
  nextTick(() => {
    loadTrend()
  })
})
</script>

<style scoped>
.health-profile {
  padding: 20px;
}
</style>

