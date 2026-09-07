<template>
  <DataPage title="直播渠道" description="抖音、快手、视频号等直播账号、渠道负责人、粉丝量和运营状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLiveChannelPage, addLiveChannel, updateLiveChannel, deleteLiveChannel, approveLiveChannel, suspendLiveChannel } from '../api'
const api = { page: getLiveChannelPage, add: addLiveChannel, update: updateLiveChannel, delete: deleteLiveChannel }
const columns = [{"prop": "channelName", "label": "渠道名称"}, {"prop": "platformName", "label": "平台"}, {"prop": "accountNo", "label": "账号ID"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "fansCount", "label": "粉丝数"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "channelName", "label": "渠道名称"}, {"prop": "platformName", "label": "平台", "type": "select", "options": [{"label": "抖音", "value": "抖音"}, {"label": "快手", "value": "快手"}, {"label": "视频号", "value": "视频号"}, {"label": "淘宝直播", "value": "淘宝直播"}]}, {"prop": "accountNo", "label": "账号ID"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "fansCount", "label": "粉丝数", "type": "number"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "SUSPENDED", "value": "SUSPENDED"}]}]
const rowActions = [{"command": "approve", "label": "审核通过", "type": "success"}, {"command": "suspend", "label": "暂停", "type": "danger"}]
const defaults = {"status": "PENDING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveLiveChannel(row.id)
  if (command === 'suspend') await suspendLiveChannel(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
