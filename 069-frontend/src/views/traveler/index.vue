<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="出行人姓名" clearable style="width: 220px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增出行人</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="name" label="姓名" min-width="120" />
        <el-table-column prop="certType" label="证件类型" width="110" />
        <el-table-column prop="certNo" label="证件号" min-width="180" />
        <el-table-column prop="phone" label="手机号" min-width="140" />
        <el-table-column label="默认" width="90">
          <template #default="{ row }">
            <el-tag :type="row.isDefault === 1 ? 'success' : 'info'">{{ row.isDefault === 1 ? '是' : '否' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除该出行人？" @confirm="handleDelete(row.id)">
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑出行人' : '新增出行人'" width="520px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="姓名" prop="name"><el-input v-model="form.name" maxlength="50" /></el-form-item>
        <el-form-item label="证件类型" prop="certType">
          <el-select v-model="form.certType" style="width: 100%">
            <el-option label="身份证" value="身份证" />
            <el-option label="护照" value="护照" />
            <el-option label="港澳通行证" value="港澳通行证" />
          </el-select>
        </el-form-item>
        <el-form-item label="证件号" prop="certNo"><el-input v-model="form.certNo" maxlength="50" /></el-form-item>
        <el-form-item label="手机号" prop="phone"><el-input v-model="form.phone" maxlength="20" /></el-form-item>
        <el-form-item label="设为默认">
          <el-switch v-model="defaultFlag" />
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
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { addTraveler, deleteTraveler, getTravelerPage, updateTraveler } from '../../api'

const loading = ref(false)
const total = ref(0)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const defaultFlag = ref(false)

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: ''
})

const form = reactive({
  id: null,
  name: '',
  certType: '身份证',
  certNo: '',
  phone: '',
  isDefault: 0
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  certType: [{ required: true, message: '请选择证件类型', trigger: 'change' }],
  certNo: [{ required: true, message: '请输入证件号', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getTravelerPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, { id: null, name: '', certType: '身份证', certNo: '', phone: '', isDefault: 0 })
  defaultFlag.value = false
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, { ...row })
  defaultFlag.value = row.isDefault === 1
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  form.isDefault = defaultFlag.value ? 1 : 0
  if (form.id) {
    await updateTraveler(form)
  } else {
    await addTraveler(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteTraveler(id)
  ElMessage.success('删除成功')
  loadData()
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
</style>
