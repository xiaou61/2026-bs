<template><DataPage title="慢病档案" description="档案编号、患者姓名、疾病类型、管理医生、建档时间和管理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDiseaseArchive, deleteDiseaseArchive, finishDiseaseArchive, getDiseaseArchivePage, processDiseaseArchive, updateDiseaseArchive } from '../api'
const api = { page: getDiseaseArchivePage, add: addDiseaseArchive, update: updateDiseaseArchive, delete: deleteDiseaseArchive, process: processDiseaseArchive, finish: finishDiseaseArchive }
const columns = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '患者姓名' }, { prop: 'category', label: '疾病类型' }, { prop: 'ownerName', label: '管理医生' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '管理状态' }, { prop: 'remark', label: '管理说明' }]
const formFields = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '患者姓名' }, { prop: 'category', label: '疾病类型' }, { prop: 'ownerName', label: '管理医生' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '管理状态', type: 'select' }, { prop: 'remark', label: '管理说明', type: 'textarea' }]
</script>
