<template><DataPage title="收集预约" description="预约编号、预约机构、收集类型、联系人、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCollectionOrder, deleteCollectionOrder, finishCollectionOrder, getCollectionOrderPage, processCollectionOrder, updateCollectionOrder } from '../api'
const api = { page: getCollectionOrderPage, add: addCollectionOrder, update: updateCollectionOrder, delete: deleteCollectionOrder, process: processCollectionOrder, finish: finishCollectionOrder }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约机构' }, { prop: 'category', label: '收集类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约机构' }, { prop: 'category', label: '收集类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>
