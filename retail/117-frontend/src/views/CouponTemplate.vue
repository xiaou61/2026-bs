<template>
  <DataPage title="券模板" description="服务券名称、券类型、面额、门槛、有效期和模板状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCouponTemplatePage, addCouponTemplate, updateCouponTemplate, deleteCouponTemplate, publishCouponTemplate, offlineCouponTemplate } from '../api'
const api = { page: getCouponTemplatePage, add: addCouponTemplate, update: updateCouponTemplate, delete: deleteCouponTemplate }
const columns = [{"prop": "templateNo", "label": "模板编号"}, {"prop": "couponName", "label": "券名称"}, {"prop": "merchantName", "label": "适用商户"}, {"prop": "couponType", "label": "券类型"}, {"prop": "faceValue", "label": "券面额"}, {"prop": "thresholdAmount", "label": "使用门槛"}, {"prop": "validDays", "label": "有效天数"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "templateNo", "label": "模板编号"}, {"prop": "couponName", "label": "券名称"}, {"prop": "merchantName", "label": "适用商户"}, {"prop": "couponType", "label": "券类型", "type": "select", "options": [{"label": "满减券", "value": "满减券"}, {"label": "折扣券", "value": "折扣券"}, {"label": "套餐券", "value": "套餐券"}, {"label": "体验券", "value": "体验券"}]}, {"prop": "faceValue", "label": "券面额", "type": "number"}, {"prop": "thresholdAmount", "label": "使用门槛", "type": "number"}, {"prop": "validDays", "label": "有效天数", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "OFFLINE", "value": "OFFLINE"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "offline", "label": "下线", "type": "warning"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishCouponTemplate(row.id)
  if (command === 'offline') await offlineCouponTemplate(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
