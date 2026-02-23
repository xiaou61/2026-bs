<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="景点名称" clearable style="width: 200px" />
        <el-input v-model="query.city" placeholder="城市" clearable style="width: 160px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="上架" :value="1" />
          <el-option label="下架" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增景点</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="name" label="景点名称" min-width="170" />
        <el-table-column prop="city" label="城市" width="110" />
        <el-table-column prop="tags" label="标签" min-width="180" show-overflow-tooltip />
        <el-table-column prop="price" label="价格" width="100" />
        <el-table-column label="状态" width="110">
          <template #default="{ row }">
            <el-switch :model-value="row.status === 1" @change="(val) => changeStatus(row, val)" />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="170" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除该景点？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div class="pager">
        <el-pagination
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :total="total"
          :page-sizes="[10, 20, 30]"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑景点' : '新增景点'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="景点名称" prop="name"><el-input v-model="form.name" maxlength="100" /></el-form-item>
        <el-form-item label="城市" prop="city"><el-input v-model="form.city" maxlength="50" /></el-form-item>
        <el-form-item label="标签"><el-input v-model="form.tags" maxlength="255" placeholder="多个标签用逗号分隔" /></el-form-item>
        <el-form-item label="价格" prop="price"><el-input-number v-model="form.price" :min="0" :precision="2" :step="1" /></el-form-item>
        <el-form-item label="封面图片">
          <input ref="coverFileRef" type="file" accept="image/*" @change="handleCoverSelect" />
          <div class="cover-wrap">
            <el-image v-if="form.cover" :src="form.cover" fit="cover" class="cover-image" :preview-src-list="[form.cover]" />
            <el-button v-if="form.cover" link type="danger" @click="form.cover = ''">清除</el-button>
          </div>
        </el-form-item>
        <el-form-item label="简介"><el-input v-model="form.intro" type="textarea" :rows="4" maxlength="1000" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">上架</el-radio>
            <el-radio :label="0">下架</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addSpot, deleteSpot, getSpotPage, updateSpot, updateSpotStatus } from '../../api'

const loading = ref(false)
const total = ref(0)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const coverFileRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  city: '',
  status: null
})

const form = reactive({
  id: null,
  name: '',
  city: '',
  tags: '',
  price: 0,
  cover: '',
  intro: '',
  status: 1
})

const rules = {
  name: [{ required: true, message: '请输入景点名称', trigger: 'blur' }],
  city: [{ required: true, message: '请输入城市', trigger: 'blur' }],
  price: [{ required: true, message: '请输入价格', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getSpotPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, { id: null, name: '', city: '', tags: '', price: 0, cover: '', intro: '', status: 1 })
  if (coverFileRef.value) {
    coverFileRef.value.value = ''
  }
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, { ...row })
  if (coverFileRef.value) {
    coverFileRef.value.value = ''
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateSpot(form)
  } else {
    await addSpot(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteSpot(id)
  ElMessage.success('删除成功')
  loadData()
}

const changeStatus = async (row, val) => {
  await updateSpotStatus({ id: row.id, status: val ? 1 : 0 })
  ElMessage.success('状态已更新')
  loadData()
}

const handleCoverSelect = async (event) => {
  const file = event.target.files && event.target.files[0]
  if (!file) {
    return
  }
  form.cover = await fileToBase64(file)
  if (coverFileRef.value) {
    coverFileRef.value.value = ''
  }
}

const fileToBase64 = (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

onMounted(loadData)
</script>

<style scoped>
.search-bar {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 14px;
}

.pager {
  margin-top: 14px;
  display: flex;
  justify-content: flex-end;
}

.cover-wrap {
  margin-top: 8px;
}

.cover-image {
  width: 180px;
  height: 120px;
  border-radius: 8px;
  display: block;
  margin-bottom: 6px;
}
</style>
