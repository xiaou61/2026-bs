<template><DataPage title="健康巡检" description="巡检编号、动物房间、巡检类型、兽医人员、巡检时间和巡检状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'BREEDER', 'VET']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHealthInspection, deleteHealthInspection, finishHealthInspection, getHealthInspectionPage, processHealthInspection, updateHealthInspection } from '../api'
const api = { page: getHealthInspectionPage, add: addHealthInspection, update: updateHealthInspection, delete: deleteHealthInspection, process: processHealthInspection, finish: finishHealthInspection }
const columns = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '动物房间' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '兽医人员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态' }, { prop: 'remark', label: '巡检说明' }]
const formFields = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '动物房间' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '兽医人员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态', type: 'select' }, { prop: 'remark', label: '巡检说明', type: 'textarea' }]
</script>
