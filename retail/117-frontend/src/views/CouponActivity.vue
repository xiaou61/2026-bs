<template>
  <DataPage title="营销活动" description="活动名称、适用券、商户、发放总量、日期和活动状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCouponActivityPage, addCouponActivity, updateCouponActivity, deleteCouponActivity, startCouponActivity, pauseCouponActivity, finishCouponActivity } from '../api'
const api = { page: getCouponActivityPage, add: addCouponActivity, update: updateCouponActivity, delete: deleteCouponActivity }
const columns = [{"prop": "activityNo", "label": "活动编号"}, {"prop": "activityName", "label": "活动名称"}, {"prop": "merchantName", "label": "商户"}, {"prop": "couponName", "label": "券名称"}, {"prop": "startDate", "label": "开始日期"}, {"prop": "endDate", "label": "结束日期"}, {"prop": "issueTotal", "label": "发放总量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "activityNo", "label": "活动编号"}, {"prop": "activityName", "label": "活动名称"}, {"prop": "merchantName", "label": "商户"}, {"prop": "couponName", "label": "券名称"}, {"prop": "startDate", "label": "开始日期"}, {"prop": "endDate", "label": "结束日期"}, {"prop": "issueTotal", "label": "发放总量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "RUNNING", "value": "RUNNING"}, {"label": "PAUSED", "value": "PAUSED"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "start", "label": "开始", "type": "success"}, {"command": "pause", "label": "暂停", "type": "warning"}, {"command": "finish", "label": "结束", "type": "success"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'start') await startCouponActivity(row.id)
  if (command === 'pause') await pauseCouponActivity(row.id)
  if (command === 'finish') await finishCouponActivity(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
