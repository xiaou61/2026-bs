<template>
  <div class="create-record">
    <el-card>
      <template #header>
        <span>创建运动记录</span>
      </template>
      
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="运动类型" prop="sportType">
          <el-select v-model="form.sportType" placeholder="请选择运动类型">
            <el-option label="跑步" value="running" />
            <el-option label="健身房" value="gym" />
            <el-option label="篮球" value="basketball" />
            <el-option label="足球" value="football" />
            <el-option label="羽毛球" value="badminton" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="运动日期" prop="sportDate">
          <el-date-picker
            v-model="form.sportDate"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        
        <el-form-item label="运动时长(分钟)" prop="duration">
          <el-input-number v-model="form.duration" :min="1" :max="1000" />
        </el-form-item>
        
        <el-form-item label="距离(公里)" v-if="form.sportType === 'running'">
          <el-input-number v-model="form.distance" :min="0" :precision="2" />
        </el-form-item>
        
        <el-form-item label="步数" v-if="form.sportType === 'running'">
          <el-input-number v-model="form.steps" :min="0" />
        </el-form-item>
        
        <el-form-item label="消耗卡路里">
          <el-input-number v-model="form.calories" :min="0" />
        </el-form-item>
        
        <el-form-item label="备注">
          <el-input
            v-model="form.remark"
            type="textarea"
            :rows="3"
            placeholder="记录一下今天的运动感受..."
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            提交打卡
          </el-button>
          <el-button @click="$router.back()">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { createRecord } from '@/api/sport'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  sportType: '',
  sportDate: new Date().toISOString().split('T')[0],
  duration: 30,
  distance: 0,
  steps: 0,
  calories: 0,
  remark: ''
})

const rules = {
  sportType: [
    { required: true, message: '请选择运动类型', trigger: 'change' }
  ],
  sportDate: [
    { required: true, message: '请选择运动日期', trigger: 'change' }
  ],
  duration: [
    { required: true, message: '请输入运动时长', trigger: 'blur' }
  ]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  
  loading.value = true
  try {
    await createRecord(form)
    ElMessage.success('打卡成功')
    router.push('/sport/record')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.create-record {
  max-width: 800px;
}
</style>

