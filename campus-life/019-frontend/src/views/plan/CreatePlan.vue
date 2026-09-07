<template>
  <div class="create-plan">
    <el-card>
      <template #header>
        <span>创建健身计划</span>
      </template>
      
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="计划名称" prop="planName">
          <el-input v-model="form.planName" placeholder="例如: 减脂塑形30天" />
        </el-form-item>
        
        <el-form-item label="计划类型" prop="planType">
          <el-select v-model="form.planType" placeholder="请选择计划类型">
            <el-option label="力量训练" value="strength" />
            <el-option label="有氧运动" value="cardio" />
            <el-option label="减脂减重" value="lose_weight" />
            <el-option label="增肌增重" value="gain_muscle" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="目标描述" prop="targetDesc">
          <el-input
            v-model="form.targetDesc"
            type="textarea"
            :rows="3"
            placeholder="描述一下你的目标..."
          />
        </el-form-item>
        
        <el-form-item label="计划天数" prop="durationDays">
          <el-input-number v-model="form.durationDays" :min="1" :max="365" />
        </el-form-item>
        
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker
            v-model="form.startDate"
            type="date"
            placeholder="选择开始日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker
            v-model="form.endDate"
            type="date"
            placeholder="选择结束日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        
        <el-form-item label="计划内容" prop="planContent">
          <el-input
            v-model="form.planContent"
            type="textarea"
            :rows="6"
            placeholder="详细描述每天的训练内容..."
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            创建计划
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
import { createPlan } from '@/api/plan'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  planName: '',
  planType: '',
  targetDesc: '',
  durationDays: 30,
  startDate: '',
  endDate: '',
  planContent: ''
})

const rules = {
  planName: [
    { required: true, message: '请输入计划名称', trigger: 'blur' }
  ],
  planType: [
    { required: true, message: '请选择计划类型', trigger: 'change' }
  ],
  durationDays: [
    { required: true, message: '请输入计划天数', trigger: 'blur' }
  ],
  startDate: [
    { required: true, message: '请选择开始日期', trigger: 'change' }
  ],
  endDate: [
    { required: true, message: '请选择结束日期', trigger: 'change' }
  ]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  
  loading.value = true
  try {
    await createPlan(form)
    ElMessage.success('创建成功')
    router.push('/plan/list')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.create-plan {
  max-width: 800px;
}
</style>

