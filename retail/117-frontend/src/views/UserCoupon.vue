<template>
  <DataPage title="用户领券" description="用户服务券、领券时间、有效期、来源渠道和可用状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getUserCouponPage, addUserCoupon, updateUserCoupon, deleteUserCoupon, lockUserCoupon, useUserCoupon, expireUserCoupon } from '../api'
const api = { page: getUserCouponPage, add: addUserCoupon, update: updateUserCoupon, delete: deleteUserCoupon }
const columns = [{"prop": "couponNo", "label": "券码"}, {"prop": "consumerName", "label": "用户"}, {"prop": "couponName", "label": "券名称"}, {"prop": "receiveTime", "label": "领券时间"}, {"prop": "expireTime", "label": "过期时间"}, {"prop": "sourceChannel", "label": "来源渠道"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "couponNo", "label": "券码"}, {"prop": "consumerName", "label": "用户"}, {"prop": "couponName", "label": "券名称"}, {"prop": "receiveTime", "label": "领券时间"}, {"prop": "expireTime", "label": "过期时间"}, {"prop": "sourceChannel", "label": "来源渠道", "type": "select", "options": [{"label": "小程序领券", "value": "小程序领券"}, {"label": "活动页", "value": "活动页"}, {"label": "门店扫码", "value": "门店扫码"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "LOCKED", "value": "LOCKED"}, {"label": "USED", "value": "USED"}, {"label": "EXPIRED", "value": "EXPIRED"}]}]
const rowActions = [{"command": "lock", "label": "锁定", "type": "warning"}, {"command": "use", "label": "使用", "type": "success"}, {"command": "expire", "label": "过期", "type": "danger"}]
const defaults = {"status": "AVAILABLE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'lock') await lockUserCoupon(row.id)
  if (command === 'use') await useUserCoupon(row.id)
  if (command === 'expire') await expireUserCoupon(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
