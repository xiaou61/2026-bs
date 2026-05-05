<template>
  <DataPage title="话术脚本" description="商品卖点、直播话术、优惠口径、审核人和脚本状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPromotionScriptPage, addPromotionScript, updatePromotionScript, deletePromotionScript, approvePromotionScript, archivePromotionScript } from '../api'
const api = { page: getPromotionScriptPage, add: addPromotionScript, update: updatePromotionScript, delete: deletePromotionScript }
const columns = [{"prop": "scriptNo", "label": "脚本编号"}, {"prop": "productName", "label": "商品"}, {"prop": "mainSellingPoint", "label": "核心卖点"}, {"prop": "couponText", "label": "优惠口径"}, {"prop": "reviewUser", "label": "审核人"}, {"prop": "versionNo", "label": "版本号"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "scriptNo", "label": "脚本编号"}, {"prop": "productName", "label": "商品"}, {"prop": "mainSellingPoint", "label": "核心卖点", "type": "textarea"}, {"prop": "couponText", "label": "优惠口径", "type": "textarea"}, {"prop": "reviewUser", "label": "审核人"}, {"prop": "versionNo", "label": "版本号"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "approve", "label": "审核通过", "type": "success"}, {"command": "archive", "label": "归档", "type": "warning"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approvePromotionScript(row.id)
  if (command === 'archive') await archivePromotionScript(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
