<template>
  <DataPage ref="pageRef" title="证书材料" description="管理证书材料并支持HR核验通过或驳回。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ verifyStatus: 0 }" @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addCertificate, deleteCertificate, getCertificatePage, rejectCertificate, updateCertificate, verifyCertificate } from '../api'

const pageRef = ref()
const verifyMap = { 0: { label: '待核验', type: 'warning' }, 1: { label: '已通过', type: 'success' }, 2: { label: '已驳回', type: 'danger' } }
const api = { page: getCertificatePage, add: addCertificate, update: updateCertificate, delete: deleteCertificate }
const verifyOptions = [{ label: '待核验', value: 0 }, { label: '已通过', value: 1 }, { label: '已驳回', value: 2 }]
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'input', prop: 'candidateId', label: '候选人ID' }, { type: 'select', prop: 'verifyStatus', label: '核验状态', options: verifyOptions }]
const columns = [{ prop: 'certName', label: '证书名称', minWidth: 180 }, { prop: 'candidateId', label: '候选人ID' }, { prop: 'certType', label: '类型' }, { prop: 'issueOrg', label: '发证机构', minWidth: 160 }, { prop: 'verifyStatus', label: '状态', map: verifyMap }, { prop: 'reviewComment', label: '核验意见', minWidth: 180, long: true }]
const formFields = [{ prop: 'candidateId', label: '候选人ID', type: 'number', min: 1, required: true }, { prop: 'certName', label: '证书名称', required: true }, { prop: 'certType', label: '证书类型' }, { prop: 'issueOrg', label: '发证机构' }, { prop: 'certUrl', label: '材料地址' }, { prop: 'verifyStatus', label: '状态', type: 'select', options: verifyOptions }, { prop: 'reviewComment', label: '核验意见', type: 'textarea', rows: 3 }]
const rowActions = [{ name: 'verify', label: '通过', type: 'success' }, { name: 'reject', label: '驳回', type: 'danger' }]

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入核验意见', '证书核验', { inputValue: name === 'verify' ? '核验通过' : '核验驳回', confirmButtonText: '确定', cancelButtonText: '取消' })
  if (name === 'verify') await verifyCertificate(row.id, value)
  if (name === 'reject') await rejectCertificate(row.id, value)
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
