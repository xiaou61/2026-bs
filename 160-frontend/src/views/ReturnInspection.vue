<template><DataPage title="归还验收" description="验收编号、归还物资、验收类型、验收人、验收时间和验收状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION', 'WAREHOUSE', 'CLUB']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReturnInspection, deleteReturnInspection, finishReturnInspection, getReturnInspectionPage, processReturnInspection, updateReturnInspection } from '../api'
const api = { page: getReturnInspectionPage, add: addReturnInspection, update: updateReturnInspection, delete: deleteReturnInspection, process: processReturnInspection, finish: finishReturnInspection }
const columns = [{ prop: 'recordNo', label: '验收编号' }, { prop: 'recordName', label: '归还物资' }, { prop: 'category', label: '验收类型' }, { prop: 'ownerName', label: '验收人' }, { prop: 'planTime', label: '验收时间' }, { prop: 'status', label: '验收状态' }, { prop: 'remark', label: '验收说明' }]
const formFields = [{ prop: 'recordNo', label: '验收编号' }, { prop: 'recordName', label: '归还物资' }, { prop: 'category', label: '验收类型' }, { prop: 'ownerName', label: '验收人' }, { prop: 'planTime', label: '验收时间' }, { prop: 'status', label: '验收状态', type: 'select' }, { prop: 'remark', label: '验收说明', type: 'textarea' }]
</script>
