<template>
  <DataPage title="孪生模型" description="孪生模型、模型类型、关联设备、版本、同步时间和模型状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTwinModelPage, addTwinModel, updateTwinModel, deleteTwinModel, publishTwinModel, offlineTwinModel } from '../api'
const api = { page: getTwinModelPage, add: addTwinModel, update: updateTwinModel, delete: deleteTwinModel }
const columns = [{"prop": "modelNo", "label": "模型编号"}, {"prop": "modelName", "label": "模型名称"}, {"prop": "modelType", "label": "模型类型"}, {"prop": "deviceName", "label": "关联设备"}, {"prop": "versionNo", "label": "版本"}, {"prop": "syncTime", "label": "同步时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "modelNo", "label": "模型编号"}, {"prop": "modelName", "label": "模型名称"}, {"prop": "modelType", "label": "模型类型", "type": "select", "options": [{"label": "设备模型", "value": "设备模型"}, {"label": "楼宇模型", "value": "楼宇模型"}, {"label": "管线模型", "value": "管线模型"}]}, {"prop": "deviceName", "label": "关联设备"}, {"prop": "versionNo", "label": "版本"}, {"prop": "syncTime", "label": "同步时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ONLINE", "value": "ONLINE"}, {"label": "OFFLINE", "value": "OFFLINE"}, {"label": "UPDATING", "value": "UPDATING"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "offline", "label": "下线", "type": "danger"}]
const defaults = {"status": "ONLINE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishTwinModel(row.id)
  if (command === 'offline') await offlineTwinModel(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
