<template>
  <div class="page-shell">
    <div class="page-title">
      <div>
        <h2>{{ title }}</h2>
        <p>{{ description }}</p>
      </div>
      <el-button v-if="!readonly" type="primary" :icon="Plus" @click="openAdd">新增</el-button>
    </div>
    <div class="table-panel">
      <div class="toolbar">
        <template v-for="item in filters" :key="item.prop">
          <el-input v-if="item.type === 'input'" v-model="query[item.prop]" clearable :placeholder="item.placeholder || item.label" @keyup.enter="search" />
          <el-select v-else v-model="query[item.prop]" clearable :placeholder="item.placeholder || item.label">
            <el-option v-for="option in item.options || []" :key="option.value" :label="option.label" :value="option.value" />
          </el-select>
        </template>
        <el-button type="primary" :icon="Search" @click="search">查询</el-button>
        <el-button @click="reset">重置</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe border>
        <el-table-column v-for="col in columns" :key="col.prop" :prop="col.prop" :label="col.label" :min-width="col.minWidth || 120" :width="col.width">
          <template #default="{ row }">
            <el-tag v-if="col.map" :type="col.map[row[col.prop]]?.type || 'info'">{{ col.map[row[col.prop]]?.label || row[col.prop] }}</el-tag>
            <el-text v-else-if="col.long" truncated>{{ row[col.prop] }}</el-text>
            <span v-else>{{ row[col.prop] }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="rowActions.length || !readonly" label="操作" width="280" fixed="right">
          <template #default="{ row }">
            <el-button v-for="action in rowActions" :key="action.name" link :type="action.type || 'primary'" @click="$emit('row-action', action.name, row)">{{ action.label }}</el-button>
            <el-button v-if="!readonly" link type="primary" @click="openEdit(row)">编辑</el-button>
            <el-popconfirm v-if="!readonly && api.delete" title="确认删除这条数据？" @confirm="remove(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" :page-sizes="[10, 20, 50]" @size-change="loadData" @current-change="loadData" />
      </div>
    </div>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑' : '新增'" :width="dialogWidth">
      <el-form ref="formRef" :model="form" label-width="112px">
        <el-form-item v-for="field in formFields" :key="field.prop" :label="field.label" :prop="field.prop" :rules="field.required ? [{ required: true, message: `请输入${field.label}`, trigger: 'blur' }] : []">
          <el-input v-if="field.type === 'textarea'" v-model="form[field.prop]" :rows="field.rows || 4" type="textarea" :placeholder="field.placeholder || field.label" />
          <el-input-number v-else-if="field.type === 'number'" v-model="form[field.prop]" :min="field.min" :max="field.max" :precision="field.precision" />
          <el-date-picker v-else-if="field.type === 'datetime'" v-model="form[field.prop]" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" :placeholder="field.placeholder || field.label" />
          <el-select v-else-if="field.type === 'select'" v-model="form[field.prop]" :placeholder="field.placeholder || field.label">
            <el-option v-for="option in field.options || []" :key="option.value" :label="option.label" :value="option.value" />
          </el-select>
          <el-input v-else v-model="form[field.prop]" :type="field.password ? 'password' : 'text'" :placeholder="field.placeholder || field.label" :show-password="field.password" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="saving" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus, Search } from '@element-plus/icons-vue'

const props = defineProps({
  title: String,
  description: String,
  api: Object,
  filters: { type: Array, default: () => [] },
  columns: { type: Array, default: () => [] },
  formFields: { type: Array, default: () => [] },
  rowActions: { type: Array, default: () => [] },
  defaults: { type: Object, default: () => ({}) },
  readonly: Boolean,
  dialogWidth: { type: String, default: '760px' }
})

defineEmits(['row-action'])

const loading = ref(false)
const saving = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10 })
const form = reactive({})

const normalizeQuery = () => {
  const params = {}
  Object.keys(query).forEach((key) => {
    if (query[key] !== '' && query[key] !== null && query[key] !== undefined) params[key] = query[key]
  })
  return params
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await props.api.page(normalizeQuery())
    tableData.value = res.data.list || res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const search = () => {
  query.pageNum = 1
  loadData()
}

const reset = () => {
  Object.keys(query).forEach((key) => {
    if (key !== 'pageNum' && key !== 'pageSize') query[key] = undefined
  })
  search()
}

const fillForm = (source) => {
  Object.keys(form).forEach((key) => delete form[key])
  props.formFields.forEach((field) => {
    form[field.prop] = source[field.prop] ?? props.defaults[field.prop] ?? undefined
  })
  if (source.id) form.id = source.id
}

const openAdd = () => {
  fillForm({})
  dialogVisible.value = true
}

const openEdit = (row) => {
  fillForm(row)
  dialogVisible.value = true
}

const submit = async () => {
  await formRef.value.validate()
  saving.value = true
  try {
    if (form.id) await props.api.update(form)
    else await props.api.add(form)
    ElMessage.success('保存成功')
    dialogVisible.value = false
    loadData()
  } finally {
    saving.value = false
  }
}

const remove = async (id) => {
  await props.api.delete(id)
  ElMessage.success('删除成功')
  loadData()
}

defineExpose({ loadData })
onMounted(loadData)
</script>
