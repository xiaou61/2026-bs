<template><DataPage title="访客档案" description="访客编号、访客姓名、访客类型、接待员工、登记时间和访客状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'SECURITY', 'EMPLOYEE', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVisitorProfile, deleteVisitorProfile, finishVisitorProfile, getVisitorProfilePage, processVisitorProfile, updateVisitorProfile } from '../api'
const api = { page: getVisitorProfilePage, add: addVisitorProfile, update: updateVisitorProfile, delete: deleteVisitorProfile, process: processVisitorProfile, finish: finishVisitorProfile }
const columns = [{ prop: 'recordNo', label: '访客编号' }, { prop: 'recordName', label: '访客姓名' }, { prop: 'category', label: '访客类型' }, { prop: 'ownerName', label: '接待员工' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '访客状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '访客编号' }, { prop: 'recordName', label: '访客姓名' }, { prop: 'category', label: '访客类型' }, { prop: 'ownerName', label: '接待员工' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '访客状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>
