<template>
  <DataPage title="资源分组" description="资源分组维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>
<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getResourceGroupPage, addResourceGroup, updateResourceGroup, deleteResourceGroup } from '../api'
const api = { page: getResourceGroupPage, add: addResourceGroup, update: updateResourceGroup, delete: deleteResourceGroup }
const columns = [{ prop: 'groupName', label: '分组名称' }, { prop: 'groupCode', label: '分组编码' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'groupName', label: '分组名称' }, { prop: 'groupCode', label: '分组编码' }, { prop: 'ownerName', label: '负责人' }, { prop: 'description', label: '说明', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
