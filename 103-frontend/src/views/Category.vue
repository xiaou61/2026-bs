<template>
  <DataPage ref="pageRef" title="知识分类" description="维护知识库分类和排序。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 1, sortNo: 1 }" @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addKnowledgeCategory, deleteKnowledgeCategory, getKnowledgeCategoryPage, updateKnowledgeCategory } from '../api'

const pageRef = ref()

const statusMap = { 0: { label: '停用', type: 'info' }, 1: { label: '启用', type: 'success' }, 2: { label: '完成', type: 'primary' }, 3: { label: '关闭', type: 'danger' } }
const statusOptions = [{ label: '停用', value: 0 }, { label: '启用', value: 1 }, { label: '完成', value: 2 }, { label: '关闭', value: 3 }]
const roleMap = { ADMIN: { label: '管理员', type: 'danger' }, SUPERVISOR: { label: '主管', type: 'success' }, AGENT: { label: '坐席', type: 'primary' }, QA: { label: '质检员', type: 'warning' } }
const roleOptions = [{ label: '管理员', value: 'ADMIN' }, { label: '主管', value: 'SUPERVISOR' }, { label: '坐席', value: 'AGENT' }, { label: '质检员', value: 'QA' }]
const priorityMap = { 低: { label: '低', type: 'info' }, 普通: { label: '普通', type: 'primary' }, 高: { label: '高', type: 'danger' } }
const priorityOptions = [{ label: '低', value: '低' }, { label: '普通', value: '普通' }, { label: '高', value: '高' }]
const orderMap = { 0: { label: '待受理', type: 'warning' }, 1: { label: '处理中', type: 'primary' }, 2: { label: '已解决', type: 'success' }, 3: { label: '已关闭', type: 'info' } }
const orderOptions = [{ label: '待受理', value: 0 }, { label: '处理中', value: 1 }, { label: '已解决', value: 2 }, { label: '已关闭', value: 3 }]
const reviewMap = { 0: { label: '待复核', type: 'warning' }, 1: { label: '通过', type: 'success' }, 2: { label: '驳回', type: 'danger' } }
const reviewOptions = [{ label: '待复核', value: 0 }, { label: '通过', value: 1 }, { label: '驳回', value: 2 }]
const adoptMap = { 0: { label: '未处理', type: 'warning' }, 1: { label: '已采纳', type: 'success' }, 2: { label: '未采纳', type: 'danger' } }
const adoptOptions = [{ label: '未处理', value: 0 }, { label: '已采纳', value: 1 }, { label: '未采纳', value: 2 }]
const flagMap = { 0: { label: '正常', type: 'success' }, 1: { label: '敏感', type: 'danger' } }
const flagOptions = [{ label: '正常', value: 0 }, { label: '敏感', value: 1 }]

const api = { page: getKnowledgeCategoryPage, add: addKnowledgeCategory, update: updateKnowledgeCategory, delete: deleteKnowledgeCategory }
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'select', prop: 'status', label: '状态', options: statusOptions }]
const columns = [{ prop: 'categoryName', label: '分类名称' }, { prop: 'parentName', label: '父级分类' }, { prop: 'sortNo', label: '排序' }, { prop: 'status', label: '状态', map: statusMap }]
const formFields = [{ prop: 'categoryName', label: '分类名称', required: true }, { prop: 'parentName', label: '父级分类' }, { prop: 'sortNo', label: '排序', type: 'number', min: 0 }, { prop: 'status', label: '状态', type: 'select', options: statusOptions }]
const rowActions = []
const handleAction = async (name, row) => {
  return name && row
}
</script>
