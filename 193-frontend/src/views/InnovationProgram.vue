<template><DataPage title="实验班项目" description="项目编号、项目名称、培养方向、项目负责人、建档时间和项目状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInnovationProgram, deleteInnovationProgram, finishInnovationProgram, getInnovationProgramPage, processInnovationProgram, updateInnovationProgram } from '../api'
const api = { page: getInnovationProgramPage, add: addInnovationProgram, update: updateInnovationProgram, delete: deleteInnovationProgram, process: processInnovationProgram, finish: finishInnovationProgram }
const columns = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '培养方向' }, { prop: 'ownerName', label: '项目负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '项目状态' }, { prop: 'remark', label: '项目说明' }]
const formFields = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '培养方向' }, { prop: 'ownerName', label: '项目负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '项目状态', type: 'select' }, { prop: 'remark', label: '项目说明', type: 'textarea' }]
</script>
