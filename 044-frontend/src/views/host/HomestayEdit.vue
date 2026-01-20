<template>
  <div class="homestay-edit">
    <el-card>
      <template #header>
        <h2>{{ isEdit ? '编辑民宿' : '添加民宿' }}</h2>
      </template>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" style="max-width: 600px">
        <el-form-item label="民宿名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入民宿名称" />
        </el-form-item>
        <el-form-item label="封面图片" prop="coverImage">
          <el-input v-model="form.coverImage" placeholder="请输入封面图片URL" />
        </el-form-item>
        <el-form-item label="省份" prop="province">
          <el-input v-model="form.province" placeholder="省份" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="form.city" placeholder="城市" />
        </el-form-item>
        <el-form-item label="区县" prop="district">
          <el-input v-model="form.district" placeholder="区县" />
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="form.address" placeholder="详细地址" />
        </el-form-item>
        <el-form-item label="最低价格" prop="minPrice">
          <el-input-number v-model="form.minPrice" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="民宿介绍" prop="description">
          <el-input v-model="form.description" type="textarea" rows="4" placeholder="请输入民宿介绍" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">{{ isEdit ? '保存修改' : '提交审核' }}</el-button>
          <el-button @click="$router.back()">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card v-if="isEdit" style="margin-top: 20px">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center">
          <h3>房型管理</h3>
          <el-button type="primary" size="small" @click="showRoomDialog()">添加房型</el-button>
        </div>
      </template>
      <el-table :data="roomTypes" stripe>
        <el-table-column prop="name" label="房型名称" />
        <el-table-column prop="price" label="价格" width="100">
          <template #default="{ row }">¥{{ row.price }}</template>
        </el-table-column>
        <el-table-column prop="area" label="面积" width="80">
          <template #default="{ row }">{{ row.area }}㎡</template>
        </el-table-column>
        <el-table-column prop="bedType" label="床型" width="100" />
        <el-table-column prop="maxGuests" label="最多人数" width="100" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" text @click="showRoomDialog(row)">编辑</el-button>
            <el-button type="danger" text @click="handleDeleteRoom(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="roomDialogVisible" :title="roomForm.id ? '编辑房型' : '添加房型'" width="500px">
      <el-form ref="roomFormRef" :model="roomForm" label-width="80px">
        <el-form-item label="房型名称" required>
          <el-input v-model="roomForm.name" placeholder="如：豪华大床房" />
        </el-form-item>
        <el-form-item label="价格" required>
          <el-input-number v-model="roomForm.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="面积">
          <el-input-number v-model="roomForm.area" :min="0" />
        </el-form-item>
        <el-form-item label="床型">
          <el-input v-model="roomForm.bedType" placeholder="如：1.8m大床" />
        </el-form-item>
        <el-form-item label="最多人数">
          <el-input-number v-model="roomForm.maxGuests" :min="1" :max="10" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="roomDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleRoomSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { getHomestayDetail } from '@/api/homestay'
import { addHomestay, updateHomestay, addRoomType, updateRoomType, deleteRoomType } from '@/api/host'

const route = useRoute()
const router = useRouter()
const formRef = ref<FormInstance>()
const roomFormRef = ref<FormInstance>()

const isEdit = computed(() => !!route.params.id)

const form = reactive({
  id: undefined as number | undefined,
  name: '',
  coverImage: '',
  province: '',
  city: '',
  district: '',
  address: '',
  minPrice: 0,
  description: ''
})

const rules: FormRules = {
  name: [{ required: true, message: '请输入民宿名称', trigger: 'blur' }],
  city: [{ required: true, message: '请输入城市', trigger: 'blur' }],
  address: [{ required: true, message: '请输入地址', trigger: 'blur' }]
}

const roomTypes = ref<any[]>([])
const roomDialogVisible = ref(false)
const roomForm = reactive({
  id: undefined as number | undefined,
  homestayId: undefined as number | undefined,
  name: '',
  price: 0,
  area: 0,
  bedType: '',
  maxGuests: 2,
  status: 1
})

const loadData = async () => {
  if (!route.params.id) return
  const res: any = await getHomestayDetail(Number(route.params.id))
  const homestay = res.data.homestay
  Object.assign(form, {
    id: homestay.id,
    name: homestay.name,
    coverImage: homestay.coverImage,
    province: homestay.province,
    city: homestay.city,
    district: homestay.district,
    address: homestay.address,
    minPrice: homestay.minPrice,
    description: homestay.description
  })
  roomTypes.value = res.data.roomTypes || []
}

const handleSubmit = async () => {
  await formRef.value?.validate()
  try {
    if (isEdit.value) {
      await updateHomestay(form)
      ElMessage.success('修改成功')
    } else {
      await addHomestay(form)
      ElMessage.success('提交成功，等待审核')
      router.push('/host/homestay')
    }
  } catch (e) {}
}

const showRoomDialog = (row?: any) => {
  if (row) {
    Object.assign(roomForm, row)
  } else {
    Object.assign(roomForm, {
      id: undefined,
      homestayId: form.id,
      name: '',
      price: 0,
      area: 0,
      bedType: '',
      maxGuests: 2,
      status: 1
    })
  }
  roomDialogVisible.value = true
}

const handleRoomSubmit = async () => {
  try {
    if (roomForm.id) {
      await updateRoomType(roomForm)
      ElMessage.success('修改成功')
    } else {
      roomForm.homestayId = form.id
      await addRoomType(roomForm)
      ElMessage.success('添加成功')
    }
    roomDialogVisible.value = false
    loadData()
  } catch (e) {}
}

const handleDeleteRoom = async (row: any) => {
  try {
    await deleteRoomType(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) {}
}

onMounted(() => {
  loadData()
})
</script>

<style scoped lang="scss">
.homestay-edit {
  h2, h3 { margin: 0; }
}
</style>
