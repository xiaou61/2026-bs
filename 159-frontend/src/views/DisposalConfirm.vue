<template><DataPage title="处置确认" description="处置编号、处置批次、处置方式、确认人、处置时间和处置状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDisposalConfirm, deleteDisposalConfirm, finishDisposalConfirm, getDisposalConfirmPage, processDisposalConfirm, updateDisposalConfirm } from '../api'
const api = { page: getDisposalConfirmPage, add: addDisposalConfirm, update: updateDisposalConfirm, delete: deleteDisposalConfirm, process: processDisposalConfirm, finish: finishDisposalConfirm }
const columns = [{ prop: 'recordNo', label: '处置编号' }, { prop: 'recordName', label: '处置批次' }, { prop: 'category', label: '处置方式' }, { prop: 'ownerName', label: '确认人' }, { prop: 'planTime', label: '处置时间' }, { prop: 'status', label: '处置状态' }, { prop: 'remark', label: '处置说明' }]
const formFields = [{ prop: 'recordNo', label: '处置编号' }, { prop: 'recordName', label: '处置批次' }, { prop: 'category', label: '处置方式' }, { prop: 'ownerName', label: '确认人' }, { prop: 'planTime', label: '处置时间' }, { prop: 'status', label: '处置状态', type: 'select' }, { prop: 'remark', label: '处置说明', type: 'textarea' }]
</script>
