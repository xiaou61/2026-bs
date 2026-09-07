<template><DataPage title="疫苗接种" description="接种编号、疫苗计划、疫苗批次、接种护士、接种时间和接种状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DOCTOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVaccineRecord, deleteVaccineRecord, finishVaccineRecord, getVaccineRecordPage, processVaccineRecord, updateVaccineRecord } from '../api'
const api = { page: getVaccineRecordPage, add: addVaccineRecord, update: updateVaccineRecord, delete: deleteVaccineRecord, process: processVaccineRecord, finish: finishVaccineRecord }
const columns = [{ prop: 'recordNo', label: '接种编号' }, { prop: 'recordName', label: '疫苗计划' }, { prop: 'category', label: '疫苗批次' }, { prop: 'ownerName', label: '接种护士' }, { prop: 'planTime', label: '接种时间' }, { prop: 'status', label: '接种状态' }, { prop: 'remark', label: '反应记录' }]
const formFields = [{ prop: 'recordNo', label: '接种编号' }, { prop: 'recordName', label: '疫苗计划' }, { prop: 'category', label: '疫苗批次' }, { prop: 'ownerName', label: '接种护士' }, { prop: 'planTime', label: '接种时间' }, { prop: 'status', label: '接种状态', type: 'select' }, { prop: 'remark', label: '反应记录', type: 'textarea' }]
</script>
