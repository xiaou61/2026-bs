<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.title" placeholder="项目标题" style="width: 200px" clearable />
      <el-input v-model="query.serviceName" placeholder="服务名称" style="width: 160px" clearable />
      <el-select v-model="query.categoryId" placeholder="分类" style="width: 140px" clearable>
        <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="上架" :value="1" />
        <el-option label="下架" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">发布服务</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" min-width="220" />
      <el-table-column prop="serviceName" label="服务名称" width="140" />
      <el-table-column prop="vehicleType" label="适用车型" width="120" />
      <el-table-column prop="price" label="价格" width="100" />
      <el-table-column prop="stock" label="可预约名额" width="80" />
      <el-table-column prop="bookedCount" label="预约量" width="80" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '上架' : '下架' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="230" fixed="right">
        <template #default="{ row }">
          <el-button link @click="openEdit(row)">编辑</el-button>
          <el-button link @click="toggleStatus(row)">{{ row.status === 1 ? '下架' : '上架' }}</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑项目' : '发布服务'" width="680px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="标题" prop="title"><el-input v-model="form.title" maxlength="120" show-word-limit /></el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select v-model="form.categoryId" style="width: 100%">
          <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="服务名称" prop="serviceName"><el-input v-model="form.serviceName" maxlength="80" show-word-limit /></el-form-item>
      <el-form-item label="门店"><el-input v-model="form.storeName" maxlength="80" show-word-limit /></el-form-item>
      <el-form-item label="适用车型"><el-input v-model="form.vehicleType" maxlength="40" show-word-limit /></el-form-item>
      <el-form-item label="预约方式"><el-input v-model="form.bookingType" maxlength="30" show-word-limit /></el-form-item>
      <el-form-item label="封面链接">
        <el-input v-model="form.cover" maxlength="255" />
        <el-upload style="margin-top: 8px" :show-file-list="false" :http-request="handleCoverUpload" accept="image/png,image/jpeg,image/webp,image/gif">
          <el-button type="primary" plain>上传封面</el-button>
        </el-upload>
      </el-form-item>
      <el-row :gutter="10">
        <el-col :span="12"><el-form-item label="价格" prop="price"><el-input-number v-model="form.price" :min="0.01" :precision="2" style="width: 100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="可预约名额" prop="stock"><el-input-number v-model="form.stock" :min="0" style="width: 100%" /></el-form-item></el-col>
      </el-row>
      <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="3" maxlength="5000" show-word-limit /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addItem, deleteItem, getCategoryList, getItemPage, updateItem, updateItemStatus, uploadFile } from '../../api'
import { useUserStore } from '../../store/user'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const categories = ref([])
const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')

const query = reactive({ pageNum: 1, pageSize: 10, title: '', serviceName: '', categoryId: null, status: null })
const form = reactive({})

const rules = {
  title: [
    { required: true, message: '请输入标题', trigger: 'blur' },
    { max: 120, message: '标题不能超过120字符', trigger: 'blur' }
  ],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  serviceName: [
    { required: true, message: '请输入服务名称', trigger: 'blur' },
    { max: 80, message: '服务名称不能超过80字符', trigger: 'blur' }
  ],
  price: [
    { required: true, message: '请输入价格', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (Number(value) <= 0) {
          callback(new Error('价格必须大于0'))
          return
        }
        callback()
      },
      trigger: 'blur'
    }
  ],
  stock: [{ required: true, message: '请输入可预约名额', trigger: 'blur' }]
}

const loadCategory = async () => {
  const res = await getCategoryList()
  categories.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getItemPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  Object.assign(form, { id: null, title: '', categoryId: null, serviceName: '', storeName: '', vehicleType: '', bookingType: '', cover: '', price: 1, stock: 1, status: 1, description: '' })
  dialogVisible.value = true
}

const openEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateItem(form)
  } else {
    await addItem(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const toggleStatus = async (row) => {
  const nextStatus = row.status === 1 ? 0 : 1
  if (isAdmin.value) {
    await updateItemStatus({ id: row.id, status: nextStatus })
  } else {
    await updateItem({ ...row, status: nextStatus })
  }
  ElMessage.success('状态已更新')
  loadData()
}

const handleDelete = async (id) => {
  await deleteItem(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleCoverUpload = async (option) => {
  if (option.file.size > 5 * 1024 * 1024) {
    ElMessage.warning('图片大小不能超过5MB')
    option.onError(new Error('file too large'))
    return
  }
  try {
    const res = await uploadFile(option.file)
    form.cover = res.data
    option.onSuccess(res, option.file)
    ElMessage.success('封面上传成功')
  } catch (e) {
    option.onError(e)
  }
}

onMounted(async () => {
  await loadCategory()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>



