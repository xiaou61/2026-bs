<template><DataPage title="异常告警" description="告警编号、实验动物、告警类型、上报人员、告警时间和告警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER', 'VET']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAbnormalAlert, deleteAbnormalAlert, finishAbnormalAlert, getAbnormalAlertPage, processAbnormalAlert, updateAbnormalAlert } from '../api'
const api = { page: getAbnormalAlertPage, add: addAbnormalAlert, update: updateAbnormalAlert, delete: deleteAbnormalAlert, process: processAbnormalAlert, finish: finishAbnormalAlert }
const columns = [{ prop: 'recordNo', label: '告警编号' }, { prop: 'recordName', label: '实验动物' }, { prop: 'category', label: '告警类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '告警时间' }, { prop: 'status', label: '告警状态' }, { prop: 'remark', label: '告警说明' }]
const formFields = [{ prop: 'recordNo', label: '告警编号' }, { prop: 'recordName', label: '实验动物' }, { prop: 'category', label: '告警类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '告警时间' }, { prop: 'status', label: '告警状态', type: 'select' }, { prop: 'remark', label: '告警说明', type: 'textarea' }]
</script>
