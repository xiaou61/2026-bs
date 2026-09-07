<template>
  <DataPage title="认证记录" description="认证编号、学员姓名、认证名称、颁发机构、发证日期和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getCertificationRecordPage, addCertificationRecord, updateCertificationRecord, deleteCertificationRecord, activateCertificationRecord, finishCertificationRecord } from '../api'
const api = { page: getCertificationRecordPage, add: addCertificationRecord, update: updateCertificationRecord, delete: deleteCertificationRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "certNo", "label": "认证编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "certificationName", "label": "认证名称"}, {"prop": "issuerName", "label": "颁发机构"}, {"prop": "issueDate", "label": "发证日期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "certNo", "label": "认证编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "certificationName", "label": "认证名称"}, {"prop": "issuerName", "label": "颁发机构"}, {"prop": "issueDate", "label": "发证日期"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "草稿", "value": "DRAFT"}, {"label": "启用", "value": "ACTIVE"}, {"label": "已完成", "value": "FINISHED"}]}]
const rowActions = computed(() => canManage.value ? [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}] : [])
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateCertificationRecord(row.id)
  if (command === 'finish') await finishCertificationRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




