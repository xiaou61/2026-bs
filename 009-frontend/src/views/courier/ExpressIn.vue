<template>
  <div class="express-in-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">快递入库</span>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="快递单号" prop="trackingNumber">
          <el-input
            v-model="form.trackingNumber"
            placeholder="请输入或扫描快递单号"
            @keyup.enter="handleSubmit"
          />
        </el-form-item>
        <el-form-item label="快递公司" prop="expressCompany">
          <el-select v-model="form.expressCompany" placeholder="请选择快递公司" style="width: 100%">
            <el-option
              v-for="item in companyList"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="收件人手机号" prop="recipientPhone">
          <el-input v-model="form.recipientPhone" placeholder="请输入收件人手机号后4位或完整手机号" />
        </el-form-item>
        <el-form-item label="代收点" prop="stationId">
          <el-select v-model="form.stationId" placeholder="请选择代收点" style="width: 100%">
            <el-option
              v-for="item in stationList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="货架位置" prop="shelfLocation">
          <el-input v-model="form.shelfLocation" placeholder="例如：A-01-05" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            确认入库
          </el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog v-model="showResult" title="入库成功" width="400px">
      <el-result icon="success" title="快递入库成功">
        <template #sub-title>
          <div class="result-info">
            <p>取件码：<span class="pickup-code">{{ resultData.pickupCode }}</span></p>
            <p>已自动发送通知给收件人</p>
          </div>
        </template>
        <template #extra>
          <el-button type="primary" @click="handleContinue">继续入库</el-button>
        </template>
      </el-result>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { expressIn } from '@/api/express'
import { getAllStations } from '@/api/station'
import { getAllCompanies } from '@/api/company'

const formRef = ref()
const loading = ref(false)
const showResult = ref(false)
const resultData = ref({})
const stationList = ref([])
const companyList = ref([])

const form = reactive({
  trackingNumber: '',
  expressCompany: '',
  recipientPhone: '',
  stationId: null,
  shelfLocation: ''
})

const rules = {
  trackingNumber: [{ required: true, message: '请输入快递单号', trigger: 'blur' }],
  expressCompany: [{ required: true, message: '请选择快递公司', trigger: 'change' }],
  recipientPhone: [{ required: true, message: '请输入收件人手机号', trigger: 'blur' }],
  stationId: [{ required: true, message: '请选择代收点', trigger: 'change' }],
  shelfLocation: [{ required: true, message: '请输入货架位置', trigger: 'blur' }]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await expressIn(form)
    resultData.value = res.data
    showResult.value = true
    ElMessage.success('入库成功')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleReset = () => {
  formRef.value.resetFields()
}

const handleContinue = () => {
  showResult.value = false
  handleReset()
  document.querySelector('input').focus()
}

const loadData = async () => {
  try {
    const [stationsRes, companiesRes] = await Promise.all([
      getAllStations(),
      getAllCompanies()
    ])
    stationList.value = stationsRes.data
    companyList.value = companiesRes.data
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.express-in-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
  max-width: 600px;
}

.result-info {
  text-align: center;
  margin: 20px 0;
}

.pickup-code {
  font-size: 32px;
  font-weight: bold;
  color: #409EFF;
  display: block;
  margin: 10px 0;
}
</style>

