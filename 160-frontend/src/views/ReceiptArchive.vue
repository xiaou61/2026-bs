<template><DataPage title="票据归档" description="票据编号、票据事项、票据类型、归档人、归档时间和票据状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION', 'TREASURER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReceiptArchive, deleteReceiptArchive, finishReceiptArchive, getReceiptArchivePage, processReceiptArchive, updateReceiptArchive } from '../api'
const api = { page: getReceiptArchivePage, add: addReceiptArchive, update: updateReceiptArchive, delete: deleteReceiptArchive, process: processReceiptArchive, finish: finishReceiptArchive }
const columns = [{ prop: 'recordNo', label: '票据编号' }, { prop: 'recordName', label: '票据事项' }, { prop: 'category', label: '票据类型' }, { prop: 'ownerName', label: '归档人' }, { prop: 'planTime', label: '归档时间' }, { prop: 'status', label: '票据状态' }, { prop: 'remark', label: '票据说明' }]
const formFields = [{ prop: 'recordNo', label: '票据编号' }, { prop: 'recordName', label: '票据事项' }, { prop: 'category', label: '票据类型' }, { prop: 'ownerName', label: '归档人' }, { prop: 'planTime', label: '归档时间' }, { prop: 'status', label: '票据状态', type: 'select' }, { prop: 'remark', label: '票据说明', type: 'textarea' }]
</script>
