<template>
  <DataPage title="数据主体" description="个人数据主体身份、联系方式和认证状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDataSubjectPage, addDataSubject, updateDataSubject, deleteDataSubject, verifyDataSubject, disableDataSubject } from '../api'
const api = { page: getDataSubjectPage, add: addDataSubject, update: updateDataSubject, delete: deleteDataSubject }
const columns = [{"prop": "subjectName", "label": "姓名"}, {"prop": "subjectCode", "label": "主体编号"}, {"prop": "identityType", "label": "证件类型"}, {"prop": "phone", "label": "手机"}, {"prop": "email", "label": "邮箱"}, {"prop": "regionName", "label": "地区"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "subjectName", "label": "姓名"}, {"prop": "subjectCode", "label": "主体编号"}, {"prop": "identityType", "label": "证件类型", "type": "select", "options": [{"label": "ID_CARD", "value": "ID_CARD"}, {"label": "PASSPORT", "value": "PASSPORT"}, {"label": "PHONE", "value": "PHONE"}]}, {"prop": "phone", "label": "手机"}, {"prop": "email", "label": "邮箱"}, {"prop": "regionName", "label": "地区"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "VERIFIED", "value": "VERIFIED"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "verify", "label": "认证", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"identityType": "ID_CARD", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'verify') await verifyDataSubject(row.id)
  if (command === 'disable') await disableDataSubject(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
