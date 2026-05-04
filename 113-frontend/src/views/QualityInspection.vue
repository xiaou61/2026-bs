<template>
  <DataPage title="质检报告" description="农残、重金属、抽检结论和报告编号管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getQualityInspectionPage, addQualityInspection, updateQualityInspection, deleteQualityInspection, publishQualityInspection, failQualityInspection } from '../api'
const api = { page: getQualityInspectionPage, add: addQualityInspection, update: updateQualityInspection, delete: deleteQualityInspection }
const columns = [{"prop": "reportNo", "label": "报告编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "productName", "label": "产品名称"}, {"prop": "inspectorName", "label": "质检员"}, {"prop": "inspectionDate", "label": "检测日期"}, {"prop": "resultStatus", "label": "检测结论"}, {"prop": "reportUrl", "label": "报告地址"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reportNo", "label": "报告编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "productName", "label": "产品名称"}, {"prop": "inspectorName", "label": "质检员"}, {"prop": "inspectionDate", "label": "检测日期"}, {"prop": "resultStatus", "label": "检测结论", "type": "select", "options": [{"label": "PASS", "value": "PASS"}, {"label": "WARNING", "value": "WARNING"}, {"label": "FAILED", "value": "FAILED"}]}, {"prop": "reportUrl", "label": "报告地址"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "FAILED", "value": "FAILED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "fail", "label": "不合格", "type": "primary"}]
const defaults = {"resultStatus": "PASS", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishQualityInspection(row.id)
  if (command === 'fail') await failQualityInspection(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
