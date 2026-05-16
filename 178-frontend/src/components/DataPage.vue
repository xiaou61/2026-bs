<template>
  <div class="page">
    <el-card>
      <div class="toolbar"><div><h3>{{ title }}</h3><p>{{ description }}</p></div><el-button v-if="canManage" type="primary" @click="openDialog()">新增</el-button></div>
      <div class="search-bar"><el-input v-model="query.keyword" placeholder="关键词" clearable style="width:220px" /><el-select v-model="query.status" placeholder="状态" clearable style="width:161px"><el-option v-for="item in normalizedStatusOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select><el-button type="primary" @click="loadData">查询</el-button><el-button @click="reset">重置</el-button></div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column v-for="col in columns" :key="col.prop" :prop="col.prop" :label="col.label" :min-width="col.width || 120" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="260"><template #default="{ row }"><el-button v-if="canManage" link type="primary" @click="openDialog(row)">编辑</el-button><el-button v-if="canManage" link type="warning" @click="handleProcess(row.id)">处理</el-button><el-button v-if="canManage" link type="success" @click="handleFinish(row.id)">完成</el-button><el-popconfirm v-if="canDelete" title="确认删除该记录？" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm></template></el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next, sizes" style="margin-top:14px" @current-change="loadData" @size-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑记录' : '新增记录'" width="680px">
      <el-form :model="form" label-width="112px"><el-form-item v-for="field in formFields" :key="field.prop" :label="field.label"><el-select v-if="field.type === 'select'" v-model="form[field.prop]" clearable style="width:100%"><el-option v-for="item in field.options || normalizedStatusOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select><el-input v-else-if="field.type === 'textarea'" v-model="form[field.prop]" type="textarea" :rows="3" /><el-input v-else v-model="form[field.prop]" /></el-form-item></el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="submit">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
const props = defineProps({ title: String, description: String, api: Object, columns: Array, formFields: Array, manageRoles: Array, deleteRoles: Array, statusOptions: Array, defaultStatus: { type: [String, Number], default: 'PENDING' } })
const userStore = useUserStore()
const normalizedStatusOptions = computed(() => props.statusOptions || [{ label: '待回收', value: 'PENDING' }, { label: '清洗中', value: 'CLEANING' }, { label: '待灭菌', value: 'STERILIZING_PENDING' }, { label: '灭菌中', value: 'STERILIZING' }, { label: '待放行', value: 'RELEASE_PENDING' }, { label: '已放行', value: 'RELEASED' }, { label: '已召回', value: 'RECALLED' }, { label: '已完成', value: 'FINISHED' }])
const canManage = computed(() => (props.manageRoles || []).includes(userStore.user?.role))
const canDelete = computed(() => (props.deleteRoles || ['ADMIN']).includes(userStore.user?.role))
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: '' })
const form = reactive({})
const loadData = async () => { loading.value = true; try { const res = await props.api.page(query); tableData.value = res.data.records || res.data.list || []; total.value = res.data.total || 0 } finally { loading.value = false } }
const reset = () => { query.pageNum = 1; query.keyword = ''; query.status = ''; loadData() }
const openDialog = row => { Object.keys(form).forEach(key => delete form[key]); Object.assign(form, { status: props.defaultStatus }, row || {}); dialogVisible.value = true }
const submit = async () => { if (form.id) await props.api.update(form); else await props.api.add(form); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async id => { await props.api.delete(id); ElMessage.success('删除成功'); loadData() }
const handleProcess = async id => { await props.api.process(id); ElMessage.success('操作成功'); loadData() }
const handleFinish = async id => { await props.api.finish(id); ElMessage.success('操作成功'); loadData() }
onMounted(loadData)
</script>
