<template>
  <DataPage ref="pageRef" title="知识文章" description="维护知识文章并支持发布和下线。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 1, hitCount: 0 }" @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addKnowledgeArticle, deleteKnowledgeArticle, getKnowledgeArticlePage, offlineArticle, publishArticle, updateKnowledgeArticle } from '../api'

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

const api = { page: getKnowledgeArticlePage, add: addKnowledgeArticle, update: updateKnowledgeArticle, delete: deleteKnowledgeArticle }
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'input', prop: 'categoryId', label: '分类ID' }, { type: 'select', prop: 'status', label: '状态', options: statusOptions }]
const columns = [{ prop: 'title', label: '标题', minWidth: 220 }, { prop: 'keywords', label: '关键词', minWidth: 180 }, { prop: 'hitCount', label: '命中次数' }, { prop: 'status', label: '状态', map: statusMap }]
const formFields = [{ prop: 'categoryId', label: '分类ID', type: 'number', min: 1 }, { prop: 'title', label: '标题', required: true }, { prop: 'keywords', label: '关键词' }, { prop: 'content', label: '正文', type: 'textarea', rows: 6 }, { prop: 'status', label: '状态', type: 'select', options: statusOptions }, { prop: 'hitCount', label: '命中次数', type: 'number', min: 0 }]
const rowActions = [{ name: 'publish', label: '发布', type: 'success' }, { name: 'offline', label: '下线', type: 'danger' }]
const handleAction = async (name, row) => {
  if (name === 'publish') await publishArticle(row.id)
  if (name === 'offline') await offlineArticle(row.id)
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
