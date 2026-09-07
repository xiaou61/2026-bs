<template>
  <DataPage ref="pageRef" title="证据材料" description="管理案件证据材料并支持核验通过或驳回。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ verifyStatus: 0 }" @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addEvidenceMaterial, deleteEvidenceMaterial, getEvidenceMaterialPage, rejectEvidence, updateEvidenceMaterial, verifyEvidence } from '../api'

const pageRef = ref()

const statusMap = { 0: { label: '停用', type: 'info' }, 1: { label: '启用', type: 'success' }, 2: { label: '完成', type: 'primary' }, 3: { label: '关闭', type: 'danger' } }
const statusOptions = [{ label: '停用', value: 0 }, { label: '启用', value: 1 }, { label: '完成', value: 2 }, { label: '关闭', value: 3 }]
const roleMap = { ADMIN: { label: '管理员', type: 'danger' }, LAWYER: { label: '律师', type: 'success' }, ASSISTANT: { label: '助理', type: 'warning' }, CLIENT: { label: '委托人', type: 'primary' } }
const roleOptions = [{ label: '管理员', value: 'ADMIN' }, { label: '律师', value: 'LAWYER' }, { label: '助理', value: 'ASSISTANT' }, { label: '委托人', value: 'CLIENT' }]
const serviceMap = { 0: { label: '暂停', type: 'info' }, 1: { label: '接案中', type: 'success' } }
const serviceOptions = [{ label: '暂停', value: 0 }, { label: '接案中', value: 1 }]
const caseStatusMap = { 0: { label: '受理中', type: 'warning' }, 1: { label: '处理中', type: 'primary' }, 2: { label: '待归档', type: 'success' }, 3: { label: '已结案', type: 'info' } }
const caseStatusOptions = [{ label: '受理中', value: 0 }, { label: '处理中', value: 1 }, { label: '待归档', value: 2 }, { label: '已结案', value: 3 }]
const priorityMap = { 低: { label: '低', type: 'info' }, 普通: { label: '普通', type: 'primary' }, 高: { label: '高', type: 'danger' } }
const priorityOptions = [{ label: '低', value: '低' }, { label: '普通', value: '普通' }, { label: '高', value: '高' }]
const nodeMap = { 0: { label: '未开始', type: 'info' }, 1: { label: '进行中', type: 'primary' }, 2: { label: '已完成', type: 'success' } }
const nodeOptions = [{ label: '未开始', value: 0 }, { label: '进行中', value: 1 }, { label: '已完成', value: 2 }]
const riskOptions = [{ label: '低', value: '低' }, { label: '中', value: '中' }, { label: '高', value: '高' }]
const docMap = { 0: { label: '草稿', type: 'info' }, 1: { label: '已生成', type: 'primary' }, 2: { label: '已通过', type: 'success' }, 3: { label: '已驳回', type: 'danger' } }
const docOptions = [{ label: '草稿', value: 0 }, { label: '已生成', value: 1 }, { label: '已通过', value: 2 }, { label: '已驳回', value: 3 }]
const appointmentMap = { 0: { label: '待确认', type: 'warning' }, 1: { label: '已确认', type: 'primary' }, 2: { label: '已取消', type: 'danger' }, 3: { label: '已完成', type: 'success' } }
const appointmentOptions = [{ label: '待确认', value: 0 }, { label: '已确认', value: 1 }, { label: '已取消', value: 2 }, { label: '已完成', value: 3 }]
const verifyMap = { 0: { label: '待核验', type: 'warning' }, 1: { label: '通过', type: 'success' }, 2: { label: '驳回', type: 'danger' } }
const verifyOptions = [{ label: '待核验', value: 0 }, { label: '通过', value: 1 }, { label: '驳回', value: 2 }]
const payMap = { 0: { label: '未支付', type: 'warning' }, 1: { label: '已支付', type: 'success' }, 2: { label: '已退款', type: 'danger' } }
const payOptions = [{ label: '未支付', value: 0 }, { label: '已支付', value: 1 }, { label: '已退款', value: 2 }]

const api = { page: getEvidenceMaterialPage, add: addEvidenceMaterial, update: updateEvidenceMaterial, delete: deleteEvidenceMaterial }
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'input', prop: 'caseId', label: '案件ID' }, { type: 'select', prop: 'verifyStatus', label: '核验状态', options: verifyOptions }]
const columns = [{ prop: 'caseId', label: '案件ID' }, { prop: 'materialName', label: '材料名称', minWidth: 180 }, { prop: 'materialType', label: '类型' }, { prop: 'fileUrl', label: '文件地址', minWidth: 220, long: true }, { prop: 'verifyStatus', label: '状态', map: verifyMap }, { prop: 'reviewComment', label: '核验意见', minWidth: 180, long: true }]
const formFields = [{ prop: 'caseId', label: '案件ID', type: 'number', min: 1, required: true }, { prop: 'materialName', label: '材料名称', required: true }, { prop: 'materialType', label: '材料类型' }, { prop: 'fileUrl', label: '文件地址' }, { prop: 'submitterId', label: '提交人ID', type: 'number', min: 1 }, { prop: 'verifyStatus', label: '状态', type: 'select', options: verifyOptions }, { prop: 'reviewComment', label: '核验意见', type: 'textarea', rows: 3 }]
const rowActions = [{ name: 'verify', label: '通过', type: 'success' }, { name: 'reject', label: '驳回', type: 'danger' }]

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入核验意见', '证据核验', { inputValue: name === 'verify' ? '核验通过' : '核验驳回', confirmButtonText: '确定', cancelButtonText: '取消' })
  if (name === 'verify') await verifyEvidence(row.id, value)
  if (name === 'reject') await rejectEvidence(row.id, value)
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
