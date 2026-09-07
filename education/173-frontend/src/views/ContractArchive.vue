<template><DataPage title="协议归档" description="协议编号、毕业学生、协议类型、审核人员、归档时间和协议状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addContractArchive, deleteContractArchive, finishContractArchive, getContractArchivePage, processContractArchive, updateContractArchive } from '../api'
const api = { page: getContractArchivePage, add: addContractArchive, update: updateContractArchive, delete: deleteContractArchive, process: processContractArchive, finish: finishContractArchive }
const columns = [{ prop: 'recordNo', label: '协议编号' }, { prop: 'recordName', label: '毕业学生' }, { prop: 'category', label: '协议类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '归档时间' }, { prop: 'status', label: '协议状态' }, { prop: 'remark', label: '协议说明' }]
const formFields = [{ prop: 'recordNo', label: '协议编号' }, { prop: 'recordName', label: '毕业学生' }, { prop: 'category', label: '协议类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '归档时间' }, { prop: 'status', label: '协议状态', type: 'select' }, { prop: 'remark', label: '协议说明', type: 'textarea' }]
</script>
