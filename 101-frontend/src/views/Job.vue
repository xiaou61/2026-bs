<template>
  <DataPage
    ref="pageRef"
    title="岗位管理"
    description="维护招聘岗位、部门、地点、薪资和发布状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="{ status: 1, jobType: '社招' }"
    @row-action="handleAction"
  />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addJob, closeJob, deleteJob, getJobPage, publishJob, updateJob } from '../api'

const pageRef = ref()

const statusMap = { 0: { label: '草稿', type: 'info' }, 1: { label: '招聘中', type: 'success' }, 2: { label: '已关闭', type: 'danger' } }
const api = { page: getJobPage, add: addJob, update: updateJob, delete: deleteJob }
const typeOptions = [{ label: '校招', value: '校招' }, { label: '社招', value: '社招' }, { label: '实习', value: '实习' }]
const statusOptions = [{ label: '草稿', value: 0 }, { label: '招聘中', value: 1 }, { label: '已关闭', value: 2 }]

const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'select', prop: 'jobType', label: '类型', options: typeOptions }, { type: 'select', prop: 'status', label: '状态', options: statusOptions }]
const columns = [{ prop: 'jobName', label: '岗位名称', minWidth: 180 }, { prop: 'department', label: '部门' }, { prop: 'jobType', label: '类型' }, { prop: 'location', label: '地点' }, { prop: 'salaryRange', label: '薪资' }, { prop: 'status', label: '状态', map: statusMap }]
const formFields = [{ prop: 'jobName', label: '岗位名称', required: true }, { prop: 'department', label: '部门' }, { prop: 'jobType', label: '类型', type: 'select', options: typeOptions }, { prop: 'location', label: '地点' }, { prop: 'salaryRange', label: '薪资范围' }, { prop: 'description', label: '岗位描述', type: 'textarea', rows: 4 }, { prop: 'status', label: '状态', type: 'select', options: statusOptions }]
const rowActions = [{ name: 'publish', label: '发布', type: 'success' }, { name: 'close', label: '关闭', type: 'danger' }]

const handleAction = async (name, row) => {
  if (name === 'publish') await publishJob(row.id)
  if (name === 'close') await closeJob(row.id)
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
