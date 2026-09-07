<template><DataPage title="健康指标" description="指标编号、监测患者、指标类型、采集人员、采集时间和指标状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHealthIndicator, deleteHealthIndicator, finishHealthIndicator, getHealthIndicatorPage, processHealthIndicator, updateHealthIndicator } from '../api'
const api = { page: getHealthIndicatorPage, add: addHealthIndicator, update: updateHealthIndicator, delete: deleteHealthIndicator, process: processHealthIndicator, finish: finishHealthIndicator }
const columns = [{ prop: 'recordNo', label: '指标编号' }, { prop: 'recordName', label: '监测患者' }, { prop: 'category', label: '指标类型' }, { prop: 'ownerName', label: '采集人员' }, { prop: 'planTime', label: '采集时间' }, { prop: 'status', label: '指标状态' }, { prop: 'remark', label: '指标说明' }]
const formFields = [{ prop: 'recordNo', label: '指标编号' }, { prop: 'recordName', label: '监测患者' }, { prop: 'category', label: '指标类型' }, { prop: 'ownerName', label: '采集人员' }, { prop: 'planTime', label: '采集时间' }, { prop: 'status', label: '指标状态', type: 'select' }, { prop: 'remark', label: '指标说明', type: 'textarea' }]
</script>
