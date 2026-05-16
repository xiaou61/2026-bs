<template><DataPage title="票种产品" description="票种编码、票种名称、适用人群、配置人、可售时段和上架状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'CHECKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTicketProduct, deleteTicketProduct, finishTicketProduct, getTicketProductPage, processTicketProduct, updateTicketProduct } from '../api'
const api = { page: getTicketProductPage, add: addTicketProduct, update: updateTicketProduct, delete: deleteTicketProduct, process: processTicketProduct, finish: finishTicketProduct }
const columns = [{ prop: 'recordNo', label: '票种编码' }, { prop: 'recordName', label: '票种名称' }, { prop: 'category', label: '适用人群' }, { prop: 'ownerName', label: '配置人' }, { prop: 'planTime', label: '可售时段' }, { prop: 'status', label: '上架状态' }, { prop: 'remark', label: '票种说明' }]
const formFields = [{ prop: 'recordNo', label: '票种编码' }, { prop: 'recordName', label: '票种名称' }, { prop: 'category', label: '适用人群' }, { prop: 'ownerName', label: '配置人' }, { prop: 'planTime', label: '可售时段' }, { prop: 'status', label: '上架状态', type: 'select' }, { prop: 'remark', label: '票种说明', type: 'textarea' }]
</script>
