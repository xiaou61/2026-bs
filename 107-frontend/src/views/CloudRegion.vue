<template>
  <DataPage title="云区域" description="云区域维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>
<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCloudRegionPage, addCloudRegion, updateCloudRegion, deleteCloudRegion } from '../api'
const api = { page: getCloudRegionPage, add: addCloudRegion, update: updateCloudRegion, delete: deleteCloudRegion }
const columns = [{ prop: 'regionName', label: '区域名称' }, { prop: 'regionCode', label: '区域编码' }, { prop: 'providerName', label: '云厂商' }, { prop: 'locationName', label: '地理位置' }, { prop: 'managerName', label: '负责人' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'regionName', label: '区域名称' }, { prop: 'regionCode', label: '区域编码' }, { prop: 'providerName', label: '云厂商' }, { prop: 'locationName', label: '地理位置' }, { prop: 'managerName', label: '负责人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
