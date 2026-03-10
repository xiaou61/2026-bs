<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.elderId" placeholder="老人" clearable style="width: 180px">
          <el-option v-for="item in elderOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="待随访" :value="0" />
          <el-option label="已完成" :value="1" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="warningId" label="预警ID" width="90" />
        <el-table-column prop="elderId" label="老人">
          <template #default="{ row }">{{ elderName(row.elderId) }}</template>
        </el-table-column>
        <el-table-column prop="followDate" label="随访日期" width="120" />
        <el-table-column prop="followMethod" label="随访方式" width="100" />
        <el-table-column prop="followContent" label="随访内容" min-width="220" />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已完成' : '待随访' }}</el-tag>
          </template>
        </el-table-column>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑随访' : '新增随访'" width="640px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="预警记录" prop="warningId">
          <el-select v-model="form.warningId" clearable style="width: 100%">
            <el-option v-for="item in warningOptions" :key="item.id" :label="item.warningContent" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="老人" prop="elderId">
          <el-select v-model="form.elderId" style="width: 100%">
            <el-option v-for="item in elderOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="随访日期" prop="followDate">
          <el-date-picker v-model="form.followDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="随访方式" prop="followMethod">
          <el-select v-model="form.followMethod" style="width: 100%">
            <el-option label="电话" value="电话" />
            <el-option label="上门" value="上门" />
            <el-option label="门诊" value="门诊" />
          </el-select>
        </el-form-item>
        <el-form-item label="随访内容" prop="followContent">
          <el-input v-model="form.followContent" type="textarea" />
        </el-form-item>
        <el-form-item label="下次日期" prop="nextFollowDate">
          <el-date-picker v-model="form.nextFollowDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="0">待随访</el-radio>
            <el-radio :label="1">已完成</el-radio>
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
import {
  addFollowUp,
  deleteFollowUp,
  getElderAll,
  getFollowUpList,
  getWarningList,
  updateFollowUp
} from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const elderOptions = ref([])
const warningOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  elderId: null,
  status: null
})

const form = reactive({
  id: null,
  warningId: null,
  elderId: null,
  followDate: '',
  followMethod: '电话',
  followContent: '',
  nextFollowDate: '',
  status: 0
})

const rules = {
  elderId: [{ required: true, message: '请选择老人', trigger: 'change' }],
  followDate: [{ required: true, message: '请选择日期', trigger: 'change' }]
}

const elderName = (id) => {
  const target = elderOptions.value.find(item => item.id === id)
  return target ? target.name : '-'
}

const loadOptions = async () => {
  const [elderRes, warningRes] = await Promise.all([
    getElderAll(),
    getWarningList({ pageNum: 1, pageSize: 200 })
  ])
  elderOptions.value = elderRes.data || []
  warningOptions.value = warningRes.data.records || []
}

const resetForm = () => {
  form.id = null
  form.warningId = null
  form.elderId = null
  form.followDate = ''
  form.followMethod = '电话'
  form.followContent = ''
  form.nextFollowDate = ''
  form.status = 0
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getFollowUpList(query)
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
    await updateFollowUp(form)
  } else {
    await addFollowUp(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteFollowUp(id)
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
