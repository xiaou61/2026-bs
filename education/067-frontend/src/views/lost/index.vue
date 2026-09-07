<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.keyword" placeholder="标题/物品" style="width: 220px" clearable />
      <el-select v-model="query.type" placeholder="类型" style="width: 130px" clearable>
        <el-option label="寻物" :value="1" />
        <el-option label="招领" :value="2" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="进行中" :value="1" />
        <el-option label="已完成" :value="2" />
      </el-select>
      <el-checkbox v-if="isStudent" v-model="query.myOnly">仅看我的</el-checkbox>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">发布信息</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column label="类型" width="80">
        <template #default="{ row }"><el-tag :type="row.type === 1 ? 'warning' : 'success'">{{ row.type === 1 ? '寻物' : '招领' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="title" label="标题" min-width="160" />
      <el-table-column prop="itemName" label="物品" width="120" />
      <el-table-column prop="publisherName" label="发布人" width="120" />
      <el-table-column prop="contact" label="联系方式" width="140" />
      <el-table-column prop="location" label="地点" width="120" />
      <el-table-column prop="happenTime" label="时间" width="170" />
      <el-table-column label="状态" width="90">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'primary' : 'info'">{{ row.status === 1 ? '进行中' : '已完成' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="220" show-overflow-tooltip />
      <el-table-column label="操作" width="260">
        <template #default="{ row }">
          <el-button v-if="canEdit(row)" link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button v-if="canEdit(row) && row.status === 1" link type="success" @click="finishRow(row)">标记完成</el-button>
          <el-popconfirm v-if="canEdit(row)" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑信息' : '发布信息'" width="700px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="类型" prop="type">
        <el-select v-model="form.type" style="width: 100%">
          <el-option label="寻物" :value="1" />
          <el-option label="招领" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title"><el-input v-model="form.title" maxlength="120" /></el-form-item>
      <el-form-item label="物品名称" prop="itemName"><el-input v-model="form.itemName" maxlength="120" /></el-form-item>
      <el-form-item label="联系方式" prop="contact"><el-input v-model="form.contact" maxlength="120" /></el-form-item>
      <el-form-item label="地点"><el-input v-model="form.location" maxlength="120" /></el-form-item>
      <el-form-item label="时间"><el-date-picker v-model="form.happenTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item>
      <el-form-item label="图片链接"><el-input v-model="form.imageUrl" maxlength="255" /></el-form-item>
      <el-form-item label="状态">
        <el-select v-model="form.status" style="width: 100%">
          <el-option label="进行中" :value="1" />
          <el-option label="已完成" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="4" maxlength="1000" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addLost, deleteLost, getLostPage, updateLost, updateLostStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isStudent = computed(() => userStore.user?.role === 'STUDENT')

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', type: null, status: null, myOnly: false })
const form = reactive({})

const rules = {
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  itemName: [{ required: true, message: '请输入物品名称', trigger: 'blur' }],
  contact: [{ required: true, message: '请输入联系方式', trigger: 'blur' }]
}

const canEdit = (row) => isAdmin.value || row.publisherId === userStore.user?.id

const loadData = async () => {
  loading.value = true
  try {
    const res = await getLostPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  Object.assign(form, { id: null, type: 1, title: '', itemName: '', contact: '', location: '', happenTime: '', imageUrl: '', status: 1, description: '' })
  dialogVisible.value = true
}

const openEdit = (row) => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateLost(form)
  } else {
    await addLost(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const finishRow = async (row) => {
  await updateLostStatus({ id: row.id, status: 2 })
  ElMessage.success('已标记完成')
  loadData()
}

const handleDelete = async (id) => {
  await deleteLost(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
</style>
