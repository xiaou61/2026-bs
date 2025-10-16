<template>
  <div class="idle-publish">
    <el-card>
      <template #header>
        <span>发布闲置物品</span>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="物品标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入物品标题" />
        </el-form-item>

        <el-form-item label="物品分类" prop="category">
          <el-select v-model="form.category" placeholder="请选择分类">
            <el-option label="数码设备" value="数码设备" />
            <el-option label="乐器" value="乐器" />
            <el-option label="投影设备" value="投影设备" />
            <el-option label="运动器材" value="运动器材" />
            <el-option label="服装道具" value="服装道具" />
            <el-option label="图书教材" value="图书教材" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>

        <el-form-item label="物品描述" prop="description">
          <el-input type="textarea" v-model="form.description" :rows="4" placeholder="请详细描述物品状况、使用情况等" />
        </el-form-item>

        <el-form-item label="新旧程度" prop="conditionLevel">
          <el-select v-model="form.conditionLevel" placeholder="请选择新旧程度">
            <el-option label="全新" :value="1" />
            <el-option label="九九新" :value="2" />
            <el-option label="九五新" :value="3" />
            <el-option label="九成新" :value="4" />
            <el-option label="八成以下" :value="5" />
          </el-select>
        </el-form-item>

        <el-form-item label="原价" prop="originalPrice">
          <el-input-number v-model="form.originalPrice" :min="0" :precision="2" />
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="小时租金">
              <el-input-number v-model="form.hourlyPrice" :min="0" :precision="2" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="日租金">
              <el-input-number v-model="form.dailyPrice" :min="0" :precision="2" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="周租金">
              <el-input-number v-model="form.weeklyPrice" :min="0" :precision="2" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="押金" prop="depositAmount">
          <el-input-number v-model="form.depositAmount" :min="0" :precision="2" />
        </el-form-item>

        <el-form-item label="取货方式" prop="pickupMethod">
          <el-radio-group v-model="form.pickupMethod">
            <el-radio :label="0">上门</el-radio>
            <el-radio :label="1">自取</el-radio>
            <el-radio :label="2">均可</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="取货地址" prop="pickupAddress">
          <el-input v-model="form.pickupAddress" placeholder="请输入取货地址" />
        </el-form-item>

        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>

        <el-form-item label="物品图片">
          <el-input v-model="form.images" placeholder='请输入图片URL，多个用英文逗号分隔。示例：https://example.com/1.jpg,https://example.com/2.jpg' />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handlePublish">发布</el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { publishIdle } from '@/api/idle'
import { ElMessage } from 'element-plus'

const router = useRouter()

const formRef = ref()
const loading = ref(false)

const form = reactive({
  title: '',
  category: '',
  description: '',
  conditionLevel: null,
  originalPrice: null,
  hourlyPrice: null,
  dailyPrice: null,
  weeklyPrice: null,
  depositAmount: null,
  pickupMethod: 1,
  pickupAddress: '',
  contactPhone: '',
  images: ''
})

const rules = {
  title: [{ required: true, message: '请输入物品标题', trigger: 'blur' }],
  category: [{ required: true, message: '请选择分类', trigger: 'change' }],
  description: [{ required: true, message: '请输入物品描述', trigger: 'blur' }],
  conditionLevel: [{ required: true, message: '请选择新旧程度', trigger: 'change' }],
  depositAmount: [{ required: true, message: '请输入押金', trigger: 'blur' }],
  pickupAddress: [{ required: true, message: '请输入取货地址', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
}

const handlePublish = async () => {
  await formRef.value.validate()
  
  loading.value = true
  try {
    const data = {
      ...form,
      images: form.images ? JSON.stringify(form.images.split(',').map(url => url.trim())) : '[]'
    }
    await publishIdle(data)
    ElMessage.success('发布成功，等待审核')
    router.push('/my-publish')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.idle-publish {
  max-width: 900px;
  margin: 0 auto;
}
</style>


