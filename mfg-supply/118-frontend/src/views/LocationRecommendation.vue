<template>
  <DataPage title="库位推荐" description="推荐单、物料、推荐库位、匹配分、推荐理由和采纳状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLocationRecommendationPage, addLocationRecommendation, updateLocationRecommendation, deleteLocationRecommendation, applyLocationRecommendation, rejectLocationRecommendation } from '../api'
const api = { page: getLocationRecommendationPage, add: addLocationRecommendation, update: updateLocationRecommendation, delete: deleteLocationRecommendation }
const columns = [{"prop": "recommendNo", "label": "推荐编号"}, {"prop": "itemName", "label": "物料名称"}, {"prop": "locationNo", "label": "推荐库位"}, {"prop": "matchScore", "label": "匹配分"}, {"prop": "reasonText", "label": "推荐理由"}, {"prop": "operatorName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recommendNo", "label": "推荐编号"}, {"prop": "itemName", "label": "物料名称"}, {"prop": "locationNo", "label": "推荐库位"}, {"prop": "matchScore", "label": "匹配分", "type": "number"}, {"prop": "reasonText", "label": "推荐理由", "type": "textarea"}, {"prop": "operatorName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_APPLY", "value": "WAIT_APPLY"}, {"label": "APPLIED", "value": "APPLIED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "apply", "label": "采纳", "type": "success"}, {"command": "reject", "label": "驳回", "type": "danger"}]
const defaults = {"status": "WAIT_APPLY"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'apply') await applyLocationRecommendation(row.id)
  if (command === 'reject') await rejectLocationRecommendation(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
