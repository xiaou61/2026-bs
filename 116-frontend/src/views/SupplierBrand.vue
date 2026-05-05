<template>
  <DataPage title="供应商品牌" description="供应商主体、品牌、联系人、资质等级和合作状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSupplierBrandPage, addSupplierBrand, updateSupplierBrand, deleteSupplierBrand, approveSupplierBrand, disableSupplierBrand } from '../api'
const api = { page: getSupplierBrandPage, add: addSupplierBrand, update: updateSupplierBrand, delete: deleteSupplierBrand }
const columns = [{"prop": "supplierName", "label": "供应商"}, {"prop": "brandName", "label": "品牌"}, {"prop": "contactName", "label": "联系人"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "qualificationLevel", "label": "资质等级"}, {"prop": "cooperationMode", "label": "合作模式"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "supplierName", "label": "供应商"}, {"prop": "brandName", "label": "品牌"}, {"prop": "contactName", "label": "联系人"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "qualificationLevel", "label": "资质等级", "type": "select", "options": [{"label": "A", "value": "A"}, {"label": "B", "value": "B"}, {"label": "C", "value": "C"}]}, {"prop": "cooperationMode", "label": "合作模式", "type": "select", "options": [{"label": "佣金带货", "value": "佣金带货"}, {"label": "坑位费", "value": "坑位费"}, {"label": "混合结算", "value": "混合结算"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_AUDIT", "value": "WAIT_AUDIT"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "approve", "label": "通过", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "WAIT_AUDIT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveSupplierBrand(row.id)
  if (command === 'disable') await disableSupplierBrand(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
