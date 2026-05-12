<template>
  <DataPage ref="pageRef" title="证书材料" description="管理证书材料并支持HR核验通过或驳回。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ verifyStatus: 0 }" :readonly="readonly" @row-action="handleAction" />
</template>

<script setup>
import { computed, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addCertificate, deleteCertificate, getCertificatePage, rejectCertificate, updateCertificate, verifyCertificate } from '../api'
import { useUserStore } from '../store/user'

const pageRef = ref()
const userStore = useUserStore()
const isCandidate = computed(() => userStore.user?.role === 'CANDIDATE')
const readonly = computed(() => !isCandidate.value)
const verifyMap = { 0: { label: '待核验', type: 'warning' }, 1: { label: '已通过', type: 'success' }, 2: { label: '已驳回', type: 'danger' } }
const api = { page: getCertificatePage, add: addCertificate, update: updateCertificate, delete: deleteCertificate }
const verifyOptions = [{ label: '待核验', value: 0 }, { label: '已通过', value: 1 }, { label: '已驳回', value: 2 }]
const filters = computed(() => {
  const items = [{ type: 'input', prop: 'keyword', label: '关键词' }]
  if (!isCandidate.value) items.push({ type: 'input', prop: 'candidateId', label: '候选人ID' })
  items.push({ type: 'select', prop: 'verifyStatus', label: '核验状态', options: verifyOptions })
  return items
})
const columns = computed(() => {
  const items = [{ prop: 'certName', label: '证书名称', minWidth: 180 }, { prop: 'certType', label: '类型' }, { prop: 'issueOrg', label: '发证机构', minWidth: 160 }, { prop: 'verifyStatus', label: '状态', map: verifyMap }, { prop: 'reviewComment', label: '核验意见', minWidth: 180, long: true }]
  if (!isCandidate.value) items.splice(1, 0, { prop: 'candidateId', label: '候选人ID' })
  return items
})
const formFields = computed(() => {
  const fields = []
  if (!isCandidate.value) fields.push({ prop: 'candidateId', label: '候选人ID', type: 'number', min: 1, required: true })
  fields.push(
    { prop: 'certName', label: '证书名称', required: true },
    { prop: 'certType', label: '证书类型' },
    { prop: 'issueOrg', label: '发证机构' },
    { prop: 'certUrl', label: '材料地址' }
  )
  return fields
})
const rowActions = computed(() => (isCandidate.value ? [] : [{ name: 'verify', label: '通过', type: 'success' }, { name: 'reject', label: '驳回', type: 'danger' }]))

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入核验意见', '证书核验', { inputValue: name === 'verify' ? '核验通过' : '核验驳回', confirmButtonText: '确定', cancelButtonText: '取消' })
  if (name === 'verify') await verifyCertificate(row.id, value)
  if (name === 'reject') await rejectCertificate(row.id, value)
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
