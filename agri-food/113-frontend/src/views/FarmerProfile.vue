<template>
  <DataPage title="农户档案" description="农户实名、合作社、联系方式和信用等级管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFarmerProfilePage, addFarmerProfile, updateFarmerProfile, deleteFarmerProfile, activateFarmerProfile, freezeFarmerProfile } from '../api'
const api = { page: getFarmerProfilePage, add: addFarmerProfile, update: updateFarmerProfile, delete: deleteFarmerProfile }
const columns = [{"prop": "farmerName", "label": "农户姓名"}, {"prop": "farmerNo", "label": "农户编号"}, {"prop": "cooperativeName", "label": "合作社"}, {"prop": "regionName", "label": "区域"}, {"prop": "phone", "label": "电话"}, {"prop": "creditLevel", "label": "信用等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "farmerName", "label": "农户姓名"}, {"prop": "farmerNo", "label": "农户编号"}, {"prop": "cooperativeName", "label": "合作社"}, {"prop": "regionName", "label": "区域"}, {"prop": "phone", "label": "电话"}, {"prop": "creditLevel", "label": "信用等级", "type": "select", "options": [{"label": "A", "value": "A"}, {"label": "B", "value": "B"}, {"label": "C", "value": "C"}, {"label": "D", "value": "D"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "FROZEN", "value": "FROZEN"}, {"label": "LEFT", "value": "LEFT"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "primary"}, {"command": "freeze", "label": "冻结", "type": "primary"}]
const defaults = {"creditLevel": "A", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateFarmerProfile(row.id)
  if (command === 'freeze') await freezeFarmerProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
