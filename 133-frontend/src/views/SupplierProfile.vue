<template>
  <DataPage title="供应商档案" description="供应商编号、名称、联系人、联系电话、资质等级和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSupplierProfilePage, addSupplierProfile, updateSupplierProfile, deleteSupplierProfile, activateSupplierProfile, finishSupplierProfile } from '../api'
const api = { page: getSupplierProfilePage, add: addSupplierProfile, update: updateSupplierProfile, delete: deleteSupplierProfile }
const columns = [{"prop": "supplierNo", "label": "供应商编号"}, {"prop": "supplierName", "label": "供应商名称"}, {"prop": "contactName", "label": "联系人"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "qualificationLevel", "label": "资质等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "supplierNo", "label": "供应商编号"}, {"prop": "supplierName", "label": "供应商名称"}, {"prop": "contactName", "label": "联系人"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "qualificationLevel", "label": "资质等级"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateSupplierProfile(row.id)
  if (command === 'finish') await finishSupplierProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
