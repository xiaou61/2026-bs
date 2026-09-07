<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.ruleName" placeholder="规则名称" style="width: 200px" clearable />
      <el-select v-model="query.ruleType" placeholder="规则类型" style="width: 140px" clearable>
        <el-option label="金额" value="AMOUNT" />
        <el-option label="频次" value="FREQUENCY" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="启用" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="ruleName" label="规则名称" min-width="180" />
      <el-table-column prop="ruleCode" label="规则编码" min-width="160" />
      <el-table-column prop="ruleType" label="类型" width="110" />
      <el-table-column prop="threshold" label="阈值" width="120" />
      <el-table-column prop="weight" label="权重" width="90" />
      <el-table-column prop="hitCount" label="命中次数" width="110" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag></template>
      </el-table-column>
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

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑规则' : '新增规则'" width="560px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="规则名称" prop="ruleName"><el-input v-model="form.ruleName" maxlength="80" /></el-form-item>
      <el-form-item label="规则编码" prop="ruleCode"><el-input v-model="form.ruleCode" maxlength="50" /></el-form-item>
      <el-form-item label="规则类型" prop="ruleType"><el-select v-model="form.ruleType" style="width: 100%"><el-option label="金额" value="AMOUNT" /><el-option label="频次" value="FREQUENCY" /></el-select></el-form-item>
      <el-form-item label="阈值" prop="threshold"><el-input-number v-model="form.threshold" :min="0" :precision="2" style="width: 100%" /></el-form-item>
      <el-form-item label="权重" prop="weight"><el-input-number v-model="form.weight" :min="1" :max="100" style="width: 100%" /></el-form-item>
      <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      <el-form-item label="描述"><el-input v-model="form.description" maxlength="255" /></el-form-item>
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
import { addRule, deleteRule, getRulePage, updateRule } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, ruleName: '', ruleType: '', status: null })
const form = reactive({})

const rules = {
  ruleName: [{ required: true, message: '请输入规则名称', trigger: 'blur' }],
  ruleCode: [{ required: true, message: '请输入规则编码', trigger: 'blur' }],
  ruleType: [{ required: true, message: '请选择规则类型', trigger: 'change' }],
  threshold: [{ required: true, message: '请输入阈值', trigger: 'blur' }],
  weight: [{ required: true, message: '请输入权重', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getRulePage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  Object.assign(form, { id: null, ruleName: '', ruleCode: '', ruleType: 'AMOUNT', threshold: 0, weight: 10, status: 1, description: '' })
  dialogVisible.value = true
}

const openEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateRule(form)
  } else {
    await addRule(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteRule(id)
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
