<template>
  <DataPage title="商家店铺" description="跨境商家、平台店铺、主体公司、国家地区和合作状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMerchantStorePage, addMerchantStore, updateMerchantStore, deleteMerchantStore, approveMerchantStore, suspendMerchantStore } from '../api'
const api = { page: getMerchantStorePage, add: addMerchantStore, update: updateMerchantStore, delete: deleteMerchantStore }
const columns = [{"prop": "storeName", "label": "店铺名称"}, {"prop": "storeCode", "label": "店铺编码"}, {"prop": "platformName", "label": "平台"}, {"prop": "companyName", "label": "主体公司"}, {"prop": "countryRegion", "label": "国家地区"}, {"prop": "contactName", "label": "联系人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "storeName", "label": "店铺名称"}, {"prop": "storeCode", "label": "店铺编码"}, {"prop": "platformName", "label": "平台"}, {"prop": "companyName", "label": "主体公司"}, {"prop": "countryRegion", "label": "国家地区"}, {"prop": "contactName", "label": "联系人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "SUSPENDED", "value": "SUSPENDED"}]}]
const rowActions = [{"command": "approve", "label": "审核通过", "type": "success"}, {"command": "suspend", "label": "暂停", "type": "danger"}]
const defaults = {"status": "PENDING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveMerchantStore(row.id)
  if (command === 'suspend') await suspendMerchantStore(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
