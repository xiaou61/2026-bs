<template>
  <DataPage title="用户档案" description="本地生活用户、手机号、等级、注册来源和账号状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getConsumerProfilePage, addConsumerProfile, updateConsumerProfile, deleteConsumerProfile, activateConsumerProfile, freezeConsumerProfile } from '../api'
const api = { page: getConsumerProfilePage, add: addConsumerProfile, update: updateConsumerProfile, delete: deleteConsumerProfile }
const columns = [{"prop": "consumerName", "label": "用户姓名"}, {"prop": "consumerNo", "label": "用户编号"}, {"prop": "phone", "label": "手机号"}, {"prop": "levelName", "label": "会员等级"}, {"prop": "registerSource", "label": "注册来源"}, {"prop": "cityName", "label": "城市"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "consumerName", "label": "用户姓名"}, {"prop": "consumerNo", "label": "用户编号"}, {"prop": "phone", "label": "手机号"}, {"prop": "levelName", "label": "会员等级", "type": "select", "options": [{"label": "普通会员", "value": "普通会员"}, {"label": "黄金会员", "value": "黄金会员"}, {"label": "铂金会员", "value": "铂金会员"}]}, {"prop": "registerSource", "label": "注册来源", "type": "select", "options": [{"label": "小程序", "value": "小程序"}, {"label": "App", "value": "App"}, {"label": "门店扫码", "value": "门店扫码"}]}, {"prop": "cityName", "label": "城市"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "FROZEN", "value": "FROZEN"}]}]
const rowActions = [{"command": "activate", "label": "激活", "type": "success"}, {"command": "freeze", "label": "冻结", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateConsumerProfile(row.id)
  if (command === 'freeze') await freezeConsumerProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
