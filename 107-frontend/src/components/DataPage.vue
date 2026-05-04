<template>
  <div class="page">
    <el-card>
      <div class="toolbar">
        <div><h3>{{ title }}</h3><p>{{ description }}</p></div>
        <el-button v-if="!readonly" type="primary" @click="openDialog()">新增</el-button>
      </div>
      <div class="search-bar">
        <el-input v-model="query.keyword" placeholder="关键词" clearable style="width: 210px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px">
          <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button @click="reset">重置</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column v-for="col in columns" :key="col.prop" :prop="col.prop" :label="col.label" :min-width="col.width || 120" show-overflow-tooltip />
        <el-table-column v-if="!readonly || rowActions.length" label="操作" fixed="right" width="270">
          <template #default="{ row }">
            <el-button v-if="!readonly" link type="primary" @click="openDialog(row)">编辑</el-button>
            <el-button v-for="act in rowActions" :key="act.command" link :type="act.type || 'primary'" @click="emitAction(act.command, row)">{{ act.label }}</el-button>
            <el-popconfirm v-if="!readonly" title="确认删除该记录？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next, sizes" style="margin-top: 14px" @current-change="loadData" @size-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑记录' : '新增记录'" width="640px">
      <el-form :model="form" label-width="110px">
        <el-form-item v-for="field in formFields" :key="field.prop" :label="field.label" :prop="field.prop">
          <el-select v-if="field.type === 'select'" v-model="form[field.prop]" clearable style="width: 100%">
            <el-option v-for="item in field.options || []" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-input-number v-else-if="field.type === 'number'" v-model="form[field.prop]" style="width: 100%" />
          <el-input v-else-if="field.type === 'textarea'" v-model="form[field.prop]" type="textarea" :rows="3" />
          <el-input v-else v-model="form[field.prop]" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
const props = defineProps({ title: String, description: String, api: Object, columns: Array, formFields: Array, rowActions: { type: Array, default: () => [] }, defaults: { type: Object, default: () => ({}) }, readonly: Boolean })
const emit = defineEmits(['row-action'])
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: '' })
const form = reactive({})
const statusOptions = [{ label: '启用', value: 'ACTIVE' }, { label: '在线', value: 'ONLINE' }, { label: '打开', value: 'OPEN' }, { label: '已确认', value: 'ACKED' }, { label: '已恢复', value: 'RESOLVED' }, { label: '已关闭', value: 'CLOSED' }]
const loadData = async () => {
  loading.value = true
  try {
    const res = await props.api.page(query)
    tableData.value = res.data.records || res.data.list || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}
const reset = () => {
  query.pageNum = 1
  query.keyword = ''
  query.status = ''
  loadData()
}
const openDialog = (row) => {
  Object.keys(form).forEach(key => delete form[key])
  Object.assign(form, props.defaults, row || {})
  dialogVisible.value = true
}
const submit = async () => {
  if (form.id) await props.api.update(form)
  else await props.api.add(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}
const handleDelete = async (id) => {
  await props.api.delete(id)
  ElMessage.success('删除成功')
  loadData()
}
const emitAction = (command, row) => emit('row-action', { command, row, refresh: loadData })
defineExpose({ loadData })
onMounted(loadData)
</script>
