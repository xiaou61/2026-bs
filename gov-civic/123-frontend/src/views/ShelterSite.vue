<template>
  <DataPage title="避险点位" description="点位编号、名称、地址、容量、负责人和开放状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getShelterSitePage, addShelterSite, updateShelterSite, deleteShelterSite, openShelterSite, closeShelterSite } from '../api'
const api = { page: getShelterSitePage, add: addShelterSite, update: updateShelterSite, delete: deleteShelterSite }
const columns = [{"prop": "siteNo", "label": "点位编号"}, {"prop": "siteName", "label": "点位名称"}, {"prop": "addressName", "label": "地址"}, {"prop": "capacityCount", "label": "容量"}, {"prop": "managerName", "label": "负责人"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "siteNo", "label": "点位编号"}, {"prop": "siteName", "label": "点位名称"}, {"prop": "addressName", "label": "地址"}, {"prop": "capacityCount", "label": "容量", "type": "number"}, {"prop": "managerName", "label": "负责人"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "open", "label": "开放", "type": "success"}, {"command": "close", "label": "关闭", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'open') await openShelterSite(row.id)
  if (command === 'close') await closeShelterSite(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
