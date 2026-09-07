<template>
  <DataPage title="承运企业" description="承运企业资质、联系人、车辆规模和合作状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCarrierCompanyPage, addCarrierCompany, updateCarrierCompany, deleteCarrierCompany, approveCarrierCompany, suspendCarrierCompany } from '../api'
const api = { page: getCarrierCompanyPage, add: addCarrierCompany, update: updateCarrierCompany, delete: deleteCarrierCompany }
const columns = [{"prop": "companyName", "label": "企业名称"}, {"prop": "companyCode", "label": "企业编码"}, {"prop": "licenseNo", "label": "资质编号"}, {"prop": "contactName", "label": "联系人"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "vehicleCount", "label": "车辆数"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "companyName", "label": "企业名称"}, {"prop": "companyCode", "label": "企业编码"}, {"prop": "licenseNo", "label": "资质编号"}, {"prop": "contactName", "label": "联系人"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "vehicleCount", "label": "车辆数", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "SUSPENDED", "value": "SUSPENDED"}]}]
const rowActions = [{"command": "approve", "label": "通过", "type": "primary"}, {"command": "suspend", "label": "暂停", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveCarrierCompany(row.id)
  if (command === 'suspend') await suspendCarrierCompany(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
