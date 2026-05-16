<template><DataPage title="签到分诊" description="分诊编号、就诊患者、分诊类型、导诊人员、签到时间和分诊状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCheckinTriage, deleteCheckinTriage, finishCheckinTriage, getCheckinTriagePage, processCheckinTriage, updateCheckinTriage } from '../api'
const api = { page: getCheckinTriagePage, add: addCheckinTriage, update: updateCheckinTriage, delete: deleteCheckinTriage, process: processCheckinTriage, finish: finishCheckinTriage }
const columns = [{ prop: 'recordNo', label: '分诊编号' }, { prop: 'recordName', label: '就诊患者' }, { prop: 'category', label: '分诊类型' }, { prop: 'ownerName', label: '导诊人员' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '分诊状态' }, { prop: 'remark', label: '分诊说明' }]
const formFields = [{ prop: 'recordNo', label: '分诊编号' }, { prop: 'recordName', label: '就诊患者' }, { prop: 'category', label: '分诊类型' }, { prop: 'ownerName', label: '导诊人员' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '分诊状态', type: 'select' }, { prop: 'remark', label: '分诊说明', type: 'textarea' }]
</script>
