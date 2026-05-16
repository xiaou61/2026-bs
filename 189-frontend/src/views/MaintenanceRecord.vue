<template><DataPage title="维修保养" description="保养编号、农机设备、保养类型、维修人员、保养时间和保养状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'MECHANIC', 'DRIVER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaintenanceRecord, deleteMaintenanceRecord, finishMaintenanceRecord, getMaintenanceRecordPage, processMaintenanceRecord, updateMaintenanceRecord } from '../api'
const api = { page: getMaintenanceRecordPage, add: addMaintenanceRecord, update: updateMaintenanceRecord, delete: deleteMaintenanceRecord, process: processMaintenanceRecord, finish: finishMaintenanceRecord }
const columns = [{ prop: 'recordNo', label: '保养编号' }, { prop: 'recordName', label: '农机设备' }, { prop: 'category', label: '保养类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '保养时间' }, { prop: 'status', label: '保养状态' }, { prop: 'remark', label: '保养说明' }]
const formFields = [{ prop: 'recordNo', label: '保养编号' }, { prop: 'recordName', label: '农机设备' }, { prop: 'category', label: '保养类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '保养时间' }, { prop: 'status', label: '保养状态', type: 'select' }, { prop: 'remark', label: '保养说明', type: 'textarea' }]
</script>
