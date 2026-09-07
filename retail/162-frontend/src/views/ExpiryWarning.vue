<template><DataPage title="临期预警" description="预警编号、预警商品、预警等级、处理人、预警时间和预警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'CLERK', 'STOCK']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExpiryWarning, deleteExpiryWarning, finishExpiryWarning, getExpiryWarningPage, processExpiryWarning, updateExpiryWarning } from '../api'
const api = { page: getExpiryWarningPage, add: addExpiryWarning, update: updateExpiryWarning, delete: deleteExpiryWarning, process: processExpiryWarning, finish: finishExpiryWarning }
const columns = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警商品' }, { prop: 'category', label: '预警等级' }, { prop: 'ownerName', label: '处理人' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态' }, { prop: 'remark', label: '预警说明' }]
const formFields = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警商品' }, { prop: 'category', label: '预警等级' }, { prop: 'ownerName', label: '处理人' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态', type: 'select' }, { prop: 'remark', label: '预警说明', type: 'textarea' }]
</script>
