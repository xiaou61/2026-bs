<template>
  <DataPage title="家庭成员" description="成员编号、家庭、姓名、关系、手机号和用电偏好管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFamilyMemberPage, addFamilyMember, updateFamilyMember, deleteFamilyMember, enableFamilyMember, disableFamilyMember } from '../api'
const api = { page: getFamilyMemberPage, add: addFamilyMember, update: updateFamilyMember, delete: deleteFamilyMember }
const columns = [{"prop": "memberNo", "label": "成员编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "memberName", "label": "姓名"}, {"prop": "relationType", "label": "关系"}, {"prop": "phone", "label": "手机号"}, {"prop": "usagePreference", "label": "用电偏好"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "memberNo", "label": "成员编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "memberName", "label": "姓名"}, {"prop": "relationType", "label": "关系"}, {"prop": "phone", "label": "手机号"}, {"prop": "usagePreference", "label": "用电偏好"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableFamilyMember(row.id)
  if (command === 'disable') await disableFamilyMember(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
