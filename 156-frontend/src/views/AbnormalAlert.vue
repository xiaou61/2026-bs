<template><DataPage title="异常预警" description="预警编号、预警宿舍、预警类型、处理人、预警时间和预警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAbnormalAlert, deleteAbnormalAlert, finishAbnormalAlert, getAbnormalAlertPage, processAbnormalAlert, updateAbnormalAlert } from '../api'
const api = { page: getAbnormalAlertPage, add: addAbnormalAlert, update: updateAbnormalAlert, delete: deleteAbnormalAlert, process: processAbnormalAlert, finish: finishAbnormalAlert }
const columns = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警宿舍' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '处理人' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态' }, { prop: 'remark', label: '预警说明' }]
const formFields = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警宿舍' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '处理人' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态', type: 'select' }, { prop: 'remark', label: '预警说明', type: 'textarea' }]
</script>
