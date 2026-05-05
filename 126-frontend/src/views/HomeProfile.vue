<template>
  <DataPage title="家庭档案" description="家庭编号、户主、城市、小区、住房面积和家庭人数管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getHomeProfilePage, addHomeProfile, updateHomeProfile, deleteHomeProfile, enableHomeProfile, disableHomeProfile } from '../api'
const api = { page: getHomeProfilePage, add: addHomeProfile, update: updateHomeProfile, delete: deleteHomeProfile }
const columns = [{"prop": "homeNo", "label": "家庭编号"}, {"prop": "ownerName", "label": "户主"}, {"prop": "cityName", "label": "城市"}, {"prop": "communityName", "label": "小区"}, {"prop": "areaSize", "label": "住房面积"}, {"prop": "memberCount", "label": "家庭人数"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "homeNo", "label": "家庭编号"}, {"prop": "ownerName", "label": "户主"}, {"prop": "cityName", "label": "城市"}, {"prop": "communityName", "label": "小区"}, {"prop": "areaSize", "label": "住房面积", "type": "number"}, {"prop": "memberCount", "label": "家庭人数", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableHomeProfile(row.id)
  if (command === 'disable') await disableHomeProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
