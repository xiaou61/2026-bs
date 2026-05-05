<template>
  <DataPage title="商户档案" description="本地生活商户主体、行业类型、联系人、结算周期和合作状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMerchantInfoPage, addMerchantInfo, updateMerchantInfo, deleteMerchantInfo, approveMerchantInfo, suspendMerchantInfo } from '../api'
const api = { page: getMerchantInfoPage, add: addMerchantInfo, update: updateMerchantInfo, delete: deleteMerchantInfo }
const columns = [{"prop": "merchantName", "label": "商户名称"}, {"prop": "merchantNo", "label": "商户编号"}, {"prop": "industryName", "label": "行业类型"}, {"prop": "contactName", "label": "联系人"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "settlementCycle", "label": "结算周期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "merchantName", "label": "商户名称"}, {"prop": "merchantNo", "label": "商户编号"}, {"prop": "industryName", "label": "行业类型", "type": "select", "options": [{"label": "餐饮美食", "value": "餐饮美食"}, {"label": "休闲娱乐", "value": "休闲娱乐"}, {"label": "丽人美业", "value": "丽人美业"}, {"label": "亲子教育", "value": "亲子教育"}]}, {"prop": "contactName", "label": "联系人"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "settlementCycle", "label": "结算周期", "type": "select", "options": [{"label": "T+1", "value": "T+1"}, {"label": "T+7", "value": "T+7"}, {"label": "月结", "value": "月结"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "SUSPENDED", "value": "SUSPENDED"}]}]
const rowActions = [{"command": "approve", "label": "审核通过", "type": "success"}, {"command": "suspend", "label": "暂停合作", "type": "danger"}]
const defaults = {"status": "PENDING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveMerchantInfo(row.id)
  if (command === 'suspend') await suspendMerchantInfo(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
