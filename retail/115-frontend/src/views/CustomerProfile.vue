<template>
  <DataPage title="客户档案" description="海外客户身份、联系方式、地区和合规核验状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCustomerProfilePage, addCustomerProfile, updateCustomerProfile, deleteCustomerProfile, verifyCustomerProfile, freezeCustomerProfile } from '../api'
const api = { page: getCustomerProfilePage, add: addCustomerProfile, update: updateCustomerProfile, delete: deleteCustomerProfile }
const columns = [{"prop": "customerName", "label": "客户姓名"}, {"prop": "customerNo", "label": "客户编号"}, {"prop": "countryRegion", "label": "国家地区"}, {"prop": "email", "label": "邮箱"}, {"prop": "phone", "label": "电话"}, {"prop": "identityNo", "label": "身份编号"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "customerName", "label": "客户姓名"}, {"prop": "customerNo", "label": "客户编号"}, {"prop": "countryRegion", "label": "国家地区"}, {"prop": "email", "label": "邮箱"}, {"prop": "phone", "label": "电话"}, {"prop": "identityNo", "label": "身份编号"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NEW", "value": "NEW"}, {"label": "VERIFIED", "value": "VERIFIED"}, {"label": "FROZEN", "value": "FROZEN"}]}]
const rowActions = [{"command": "verify", "label": "核验", "type": "success"}, {"command": "freeze", "label": "冻结", "type": "danger"}]
const defaults = {"status": "NEW"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'verify') await verifyCustomerProfile(row.id)
  if (command === 'freeze') await freezeCustomerProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
