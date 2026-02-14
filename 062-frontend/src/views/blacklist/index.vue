<template>
  <el-card>
    <div class="bar">
      <el-select v-model="query.type" placeholder="类型" style="width: 140px" clearable>
        <el-option label="账号" value="ACCOUNT" />
        <el-option label="设备" value="DEVICE" />
        <el-option label="IP" value="IP" />
      </el-select>
      <el-input v-model="query.value" placeholder="黑名单值" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="启用" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="type" label="类型" width="120" />
      <el-table-column prop="value" label="值" min-width="220" />
      <el-table-column prop="reason" label="原因" min-width="220" />
      <el-table-column prop="level" label="等级" width="90" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button link @click="openEdit(row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑黑名单' : '新增黑名单'" width="520px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="类型" prop="type"><el-select v-model="form.type" style="width: 100%"><el-option label="账号" value="ACCOUNT" /><el-option label="设备" value="DEVICE" /><el-option label="IP" value="IP" /></el-select></el-form-item>
      <el-form-item label="值" prop="value"><el-input v-model="form.value" maxlength="100" /></el-form-item>
      <el-form-item label="原因"><el-input v-model="form.reason" maxlength="255" /></el-form-item>
      <el-form-item label="等级"><el-input-number v-model="form.level" :min="1" :max="3" /></el-form-item>
      <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addBlacklist, deleteBlacklist, getBlacklistPage, updateBlacklist } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, type: '', value: '', status: null })
const form = reactive({})

const rules = {
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  value: [{ required: true, message: '请输入值', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getBlacklistPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  Object.assign(form, { id: null, type: 'ACCOUNT', value: '', reason: '', level: 2, status: 1 })
  dialogVisible.value = true
}

const openEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateBlacklist(form)
  } else {
    await addBlacklist(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteBlacklist(id)
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
}
</style>
