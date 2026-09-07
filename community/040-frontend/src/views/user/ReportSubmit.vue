<template>
  <div class="page-container">
    <el-page-header @back="$router.back()" title="返回">
      <template #content>
        <span class="page-title">提交违停举报</span>
      </template>
    </el-page-header>

    <el-card style="margin-top: 20px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="车牌号" prop="plateNumber">
          <el-input v-model="form.plateNumber" placeholder="请输入车牌号，如：京A12345" maxlength="10" />
        </el-form-item>

        <el-form-item label="违停类型" prop="violationTypeId">
          <el-select v-model="form.violationTypeId" placeholder="请选择违停类型" style="width: 100%">
            <el-option
              v-for="type in violationTypes"
              :key="type.id"
              :label="type.name"
              :value="type.id"
            >
              <span>{{ type.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">扣{{ type.penaltyPoints }}分</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="违停位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入详细位置" />
        </el-form-item>

        <el-form-item label="经纬度">
          <el-row :gutter="10">
            <el-col :span="12">
              <el-input v-model="form.longitude" placeholder="经度（可选）" type="number" />
            </el-col>
            <el-col :span="12">
              <el-input v-model="form.latitude" placeholder="纬度（可选）" type="number" />
            </el-col>
          </el-row>
          <div style="color: #909399; font-size: 12px; margin-top: 5px">提示：可通过地图选点自动填充</div>
        </el-form-item>

        <el-form-item label="违停描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请详细描述违停情况"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>

        <el-form-item label="违停照片" prop="images">
          <el-upload
            v-model:file-list="fileList"
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :limit="3"
            accept="image/*"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
          <div style="color: #909399; font-size: 12px">最多上传3张照片，支持jpg、png格式</div>
        </el-form-item>

        <el-form-item label="匿名举报">
          <el-switch v-model="form.isAnonymous" />
          <span style="margin-left: 10px; color: #909399; font-size: 13px">
            开启后举报信息将不显示您的身份
          </span>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading" size="large">
            提交举报
          </el-button>
          <el-button @click="handleReset" size="large">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getViolationTypes } from '@/api/violation'
import { submitReport } from '@/api/report'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const fileList = ref([])
const violationTypes = ref([])

const form = ref({
  plateNumber: '',
  violationTypeId: null,
  location: '',
  longitude: null,
  latitude: null,
  description: '',
  images: '',
  isAnonymous: false
})

const rules = {
  plateNumber: [
    { required: true, message: '请输入车牌号', trigger: 'blur' },
    { pattern: /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-Z0-9]{5}$/, message: '车牌号格式不正确', trigger: 'blur' }
  ],
  violationTypeId: [{ required: true, message: '请选择违停类型', trigger: 'change' }],
  location: [{ required: true, message: '请输入违停位置', trigger: 'blur' }],
  description: [{ required: true, message: '请描述违停情况', trigger: 'blur' }]
}

const loadViolationTypes = async () => {
  try {
    const res = await getViolationTypes()
    violationTypes.value = res.data || []
  } catch (error) {
    ElMessage.error('加载违停类型失败')
  }
}

const handleSubmit = async () => {
  await formRef.value.validate()
  
  if (fileList.value.length === 0) {
    ElMessage.warning('请至少上传一张违停照片')
    return
  }

  loading.value = true
  try {
    const imageUrls = fileList.value.map(file => file.url || file.name).join(',')
    const data = {
      ...form.value,
      images: imageUrls
    }
    
    await submitReport(data)
    ElMessage.success('举报提交成功，等待审核')
    router.push('/reports/my')
  } catch (error) {
    ElMessage.error(error.message || '提交失败')
  } finally {
    loading.value = false
  }
}

const handleReset = () => {
  formRef.value.resetFields()
  fileList.value = []
}

onMounted(() => {
  loadViolationTypes()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
  min-height: 100vh;
  background: #f0f2f5;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.el-card {
  max-width: 800px;
  margin: 0 auto;
}
</style>
