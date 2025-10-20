<template>
  <div class="publish-container">
    <el-card class="publish-card">
      <template #header>
        <div class="card-header">
          <span>发布代领订单</span>
        </div>
      </template>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="快递公司" prop="expressCompany">
          <el-select v-model="form.expressCompany" placeholder="请选择快递公司">
            <el-option label="菜鸟驿站" value="菜鸟驿站" />
            <el-option label="京东快递" value="京东快递" />
            <el-option label="顺丰快递" value="顺丰快递" />
            <el-option label="中通快递" value="中通快递" />
            <el-option label="圆通快递" value="圆通快递" />
            <el-option label="申通快递" value="申通快递" />
            <el-option label="韵达快递" value="韵达快递" />
          </el-select>
        </el-form-item>
        <el-form-item label="取件码" prop="pickupCode">
          <el-input v-model="form.pickupCode" placeholder="请输入取件码" />
        </el-form-item>
        <el-form-item label="物品类型" prop="itemType">
          <el-select v-model="form.itemType" placeholder="请选择物品类型">
            <el-option label="文件" value="文件" />
            <el-option label="服饰" value="服饰" />
            <el-option label="食品" value="食品" />
            <el-option label="数码" value="数码" />
            <el-option label="日用品" value="日用品" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="物品重量" prop="itemWeight">
          <el-radio-group v-model="form.itemWeight" @change="calculateFee">
            <el-radio label="SMALL">小件（<2kg）</el-radio>
            <el-radio label="MEDIUM">中件（2-5kg）</el-radio>
            <el-radio label="LARGE">大件（>5kg）</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="取件地址" prop="pickupAddress">
          <el-input v-model="form.pickupAddress" placeholder="请输入取件地址（快递站点）" />
        </el-form-item>
        <el-form-item label="送达地址" prop="deliveryBuilding">
          <el-input v-model="form.deliveryBuilding" placeholder="宿舍楼栋" style="width: 48%; margin-right: 4%" />
          <el-input v-model="form.deliveryRoom" placeholder="房间号" style="width: 48%" />
        </el-form-item>
        <el-form-item label="跑腿费" prop="fee">
          <el-input-number v-model="form.fee" :min="1" :max="50" :step="0.5" :precision="2" />
          <span style="margin-left: 10px; color: #909399">元（建议：¥{{ suggestedFee }}）</span>
        </el-form-item>
        <el-form-item label="期望送达" prop="expectTime">
          <el-date-picker
            v-model="form.expectTime"
            type="datetime"
            placeholder="选择期望送达时间"
            :disabled-date="disabledDate"
          />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="备注说明" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注说明（选填）" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" :loading="loading" @click="handleSubmit">
            发布订单
          </el-button>
          <el-button size="large" @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { createOrder } from '../api/order'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  expressCompany: '',
  pickupCode: '',
  itemType: '',
  itemWeight: 'SMALL',
  pickupAddress: '',
  deliveryBuilding: '',
  deliveryRoom: '',
  fee: 2,
  expectTime: '',
  contactPhone: '',
  remark: ''
})

const suggestedFee = computed(() => {
  let fee = 2
  if (form.itemWeight === 'MEDIUM') fee += 1
  if (form.itemWeight === 'LARGE') fee += 2
  return fee
})

const calculateFee = () => {
  form.fee = suggestedFee.value
}

const rules = {
  expressCompany: [{ required: true, message: '请选择快递公司', trigger: 'change' }],
  pickupCode: [{ required: true, message: '请输入取件码', trigger: 'blur' }],
  itemType: [{ required: true, message: '请选择物品类型', trigger: 'change' }],
  itemWeight: [{ required: true, message: '请选择物品重量', trigger: 'change' }],
  pickupAddress: [{ required: true, message: '请输入取件地址', trigger: 'blur' }],
  deliveryBuilding: [{ required: true, message: '请输入宿舍楼栋', trigger: 'blur' }],
  fee: [{ required: true, message: '请输入跑腿费', trigger: 'blur' }],
  expectTime: [{ required: true, message: '请选择期望送达时间', trigger: 'change' }],
  contactPhone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ]
}

const disabledDate = (time) => {
  return time.getTime() < Date.now() - 8.64e7
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      if (!form.deliveryRoom) {
        ElMessage.warning('请输入房间号')
        return
      }
      loading.value = true
      try {
        await createOrder({
          ...form,
          deliveryAddress: `${form.deliveryBuilding} ${form.deliveryRoom}`
        })
        ElMessage.success('发布成功')
        router.push('/my-orders')
      } catch (error) {
        console.error('发布失败', error)
      } finally {
        loading.value = false
      }
    }
  })
}

onMounted(() => {
  if (userStore.userInfo) {
    form.deliveryBuilding = userStore.userInfo.dormitoryBuilding || ''
    form.deliveryRoom = userStore.userInfo.dormitoryRoom || ''
    form.contactPhone = userStore.userInfo.phone || ''
  }
})
</script>

<style scoped>
.publish-container {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  font-size: 18px;
  font-weight: 500;
}
</style>

