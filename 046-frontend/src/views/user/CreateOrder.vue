<template>
  <div>
    <el-card>
      <template #header>预约回收</template>
      <el-form :model="form" label-width="100px" style="max-width: 600px;">
        <el-form-item label="联系人" required>
          <el-input v-model="form.contactName" placeholder="请输入联系人姓名" />
        </el-form-item>
        <el-form-item label="联系电话" required>
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="回收地址" required>
          <el-input v-model="form.address" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="预约时间" required>
          <el-date-picker v-model="form.appointmentTime" type="datetime" placeholder="选择预约时间" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="垃圾类型">
          <el-checkbox-group v-model="form.categoryIds">
            <el-checkbox v-for="c in categories" :key="c.id" :label="c.id">{{ c.name }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请描述回收物品的大概数量和情况" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">提交预约</el-button>
          <el-button @click="router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px;">
      <template #header>回收价格参考</template>
      <el-table :data="categories" size="small">
        <el-table-column prop="name" label="分类" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="price" label="回收价格(元/kg)" />
        <el-table-column prop="pointsRate" label="积分(/kg)" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCategoryList, createOrder } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const categories = ref<any[]>([])
const form = ref({
  contactName: '',
  contactPhone: '',
  address: '',
  appointmentTime: '',
  categoryIds: [] as number[],
  remark: ''
})

const loadCategories = async () => {
  const res: any = await getCategoryList()
  categories.value = res.data
}

const handleSubmit = async () => {
  if (!form.value.contactName || !form.value.contactPhone || !form.value.address || !form.value.appointmentTime) {
    ElMessage.warning('请填写必填项')
    return
  }
  await createOrder(form.value)
  ElMessage.success('预约成功')
  router.push('/user/order')
}

onMounted(loadCategories)
</script>
