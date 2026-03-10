<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.appointmentId" placeholder="预约单" clearable style="width: 180px">
          <el-option v-for="item in appointmentOptions" :key="item.id" :label="item.appointmentNo" :value="item.id" />
        </el-select>
        <el-select v-model="query.elderId" placeholder="老人" clearable style="width: 180px">
          <el-option v-for="item in elderOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.abnormalFlag" placeholder="是否异常" clearable style="width: 140px">
          <el-option label="正常" :value="0" />
          <el-option label="异常" :value="1" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="appointmentId" label="预约单">
          <template #default="{ row }">{{ appointmentNo(row.appointmentId) }}</template>
        </el-table-column>
        <el-table-column prop="elderId" label="老人">
          <template #default="{ row }">{{ elderName(row.elderId) }}</template>
        </el-table-column>
        <el-table-column prop="itemId" label="项目">
          <template #default="{ row }">{{ itemName(row.itemId) }}</template>
        </el-table-column>
        <el-table-column prop="itemValue" label="结果值" width="100" />
        <el-table-column prop="abnormalFlag" label="异常" width="90">
          <template #default="{ row }">
            <el-tag :type="row.abnormalFlag === 1 ? 'danger' : 'success'">{{ row.abnormalFlag === 1 ? '异常' : '正常' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="conclusion" label="结论" min-width="180" />
        <el-table-column label="操作" width="170">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑结果' : '新增结果'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="预约单" prop="appointmentId">
          <el-select v-model="form.appointmentId" style="width: 100%">
            <el-option v-for="item in appointmentOptions" :key="item.id" :label="item.appointmentNo" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="老人" prop="elderId">
          <el-select v-model="form.elderId" style="width: 100%">
            <el-option v-for="item in elderOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="体检项目" prop="itemId">
          <el-select v-model="form.itemId" style="width: 100%">
            <el-option v-for="item in itemOptions" :key="item.id" :label="item.itemName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="结果值" prop="itemValue">
          <el-input v-model="form.itemValue" />
        </el-form-item>
        <el-form-item label="检查时间" prop="checkTime">
          <el-date-picker v-model="form.checkTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结论" prop="conclusion">
          <el-input v-model="form.conclusion" type="textarea" />
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
import {
  addResult,
  deleteResult,
  getAppointmentList,
  getElderAll,
  getItemAll,
  getResultList,
  updateResult
} from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const appointmentOptions = ref([])
const elderOptions = ref([])
const itemOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  appointmentId: null,
  elderId: null,
  abnormalFlag: null
})

const form = reactive({
  id: null,
  appointmentId: null,
  elderId: null,
  itemId: null,
  itemValue: '',
  checkTime: '',
  conclusion: ''
})

const rules = {
  appointmentId: [{ required: true, message: '请选择预约单', trigger: 'change' }],
  elderId: [{ required: true, message: '请选择老人', trigger: 'change' }],
  itemId: [{ required: true, message: '请选择项目', trigger: 'change' }],
  itemValue: [{ required: true, message: '请输入结果值', trigger: 'blur' }]
}

const elderName = (id) => {
  const target = elderOptions.value.find(item => item.id === id)
  return target ? target.name : '-'
}

const itemName = (id) => {
  const target = itemOptions.value.find(item => item.id === id)
  return target ? target.itemName : '-'
}

const appointmentNo = (id) => {
  const target = appointmentOptions.value.find(item => item.id === id)
  return target ? target.appointmentNo : '-'
}

const loadOptions = async () => {
  const [appointmentRes, elderRes, itemRes] = await Promise.all([
    getAppointmentList({ pageNum: 1, pageSize: 200 }),
    getElderAll(),
    getItemAll()
  ])
  appointmentOptions.value = appointmentRes.data.records || []
  elderOptions.value = elderRes.data || []
  itemOptions.value = itemRes.data || []
}

const resetForm = () => {
  form.id = null
  form.appointmentId = null
  form.elderId = null
  form.itemId = null
  form.itemValue = ''
  form.checkTime = ''
  form.conclusion = ''
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getResultList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateResult(form)
  } else {
    await addResult(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteResult(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  await loadData()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
  flex-wrap: wrap;
}
</style>
