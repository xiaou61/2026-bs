<template>
  <DataPage title="门店网点" description="商户门店、城市商圈、地址、联系人和营业状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getStoreBranchPage, addStoreBranch, updateStoreBranch, deleteStoreBranch, openStoreBranch, closeStoreBranch } from '../api'
const api = { page: getStoreBranchPage, add: addStoreBranch, update: updateStoreBranch, delete: deleteStoreBranch }
const columns = [{"prop": "branchName", "label": "门店名称"}, {"prop": "branchNo", "label": "门店编号"}, {"prop": "merchantName", "label": "所属商户"}, {"prop": "cityName", "label": "城市"}, {"prop": "businessDistrict", "label": "商圈"}, {"prop": "addressText", "label": "地址"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "branchName", "label": "门店名称"}, {"prop": "branchNo", "label": "门店编号"}, {"prop": "merchantName", "label": "所属商户"}, {"prop": "cityName", "label": "城市", "type": "select", "options": [{"label": "杭州", "value": "杭州"}, {"label": "上海", "value": "上海"}, {"label": "南京", "value": "南京"}, {"label": "苏州", "value": "苏州"}]}, {"prop": "businessDistrict", "label": "商圈"}, {"prop": "addressText", "label": "地址", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "open", "label": "营业", "type": "success"}, {"command": "close", "label": "停业", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'open') await openStoreBranch(row.id)
  if (command === 'close') await closeStoreBranch(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
