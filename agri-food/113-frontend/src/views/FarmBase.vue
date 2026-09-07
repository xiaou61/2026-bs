<template>
  <DataPage title="种植基地" description="基地备案、地块面积、负责人和认证状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFarmBasePage, addFarmBase, updateFarmBase, deleteFarmBase, certifyFarmBase, suspendFarmBase } from '../api'
const api = { page: getFarmBasePage, add: addFarmBase, update: updateFarmBase, delete: deleteFarmBase }
const columns = [{"prop": "baseName", "label": "基地名称"}, {"prop": "baseCode", "label": "基地编码"}, {"prop": "regionName", "label": "区域"}, {"prop": "areaMu", "label": "面积亩"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "certStatus", "label": "认证状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "baseName", "label": "基地名称"}, {"prop": "baseCode", "label": "基地编码"}, {"prop": "regionName", "label": "区域"}, {"prop": "areaMu", "label": "面积亩", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "certStatus", "label": "认证状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "CERTIFIED", "value": "CERTIFIED"}, {"label": "EXPIRED", "value": "EXPIRED"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "SUSPENDED", "value": "SUSPENDED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "certify", "label": "认证", "type": "primary"}, {"command": "suspend", "label": "暂停", "type": "primary"}]
const defaults = {"certStatus": "CERTIFIED", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'certify') await certifyFarmBase(row.id)
  if (command === 'suspend') await suspendFarmBase(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
