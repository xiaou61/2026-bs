<template>
  <div class="create-activity">
    <el-card>
      <template #header>
        <span>发起约球活动</span>
      </template>
      
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="活动名称" prop="activityName">
          <el-input v-model="form.activityName" placeholder="例如: 周末篮球约战" />
        </el-form-item>
        
        <el-form-item label="运动类型" prop="sportType">
          <el-select v-model="form.sportType" placeholder="请选择运动类型">
            <el-option label="篮球" value="basketball" />
            <el-option label="足球" value="football" />
            <el-option label="羽毛球" value="badminton" />
            <el-option label="网球" value="tennis" />
            <el-option label="乒乓球" value="pingpong" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="活动时间" prop="activityTime">
          <el-date-picker
            v-model="form.activityTime"
            type="datetime"
            placeholder="选择活动时间"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        
        <el-form-item label="最大人数" prop="maxParticipants">
          <el-input-number v-model="form.maxParticipants" :min="2" :max="50" />
        </el-form-item>
        
        <el-form-item label="水平要求" prop="levelRequirement">
          <el-select v-model="form.levelRequirement" placeholder="请选择水平要求">
            <el-option label="新手" value="beginner" />
            <el-option label="中级" value="intermediate" />
            <el-option label="高级" value="advanced" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="活动描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="描述活动的详细信息..."
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            发起活动
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
import { createActivity } from '@/api/activity'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  activityName: '',
  sportType: '',
  activityTime: '',
  maxParticipants: 10,
  levelRequirement: 'beginner',
  description: ''
})

const rules = {
  activityName: [
    { required: true, message: '请输入活动名称', trigger: 'blur' }
  ],
  sportType: [
    { required: true, message: '请选择运动类型', trigger: 'change' }
  ],
  activityTime: [
    { required: true, message: '请选择活动时间', trigger: 'change' }
  ]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  
  loading.value = true
  try {
    await createActivity(form)
    ElMessage.success('发起成功')
    router.push('/activity/list')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.create-activity {
  max-width: 800px;
}
</style>

