<template><DataPage title="病例学习" description="病例编号、病例名称、病例类型、带教老师、发布时间和学习状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCaseLibrary, deleteCaseLibrary, finishCaseLibrary, getCaseLibraryPage, processCaseLibrary, updateCaseLibrary } from '../api'
const api = { page: getCaseLibraryPage, add: addCaseLibrary, update: updateCaseLibrary, delete: deleteCaseLibrary, process: processCaseLibrary, finish: finishCaseLibrary }
const columns = [{ prop: 'recordNo', label: '病例编号' }, { prop: 'recordName', label: '病例名称' }, { prop: 'category', label: '病例类型' }, { prop: 'ownerName', label: '带教老师' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '学习状态' }, { prop: 'remark', label: '病例说明' }]
const formFields = [{ prop: 'recordNo', label: '病例编号' }, { prop: 'recordName', label: '病例名称' }, { prop: 'category', label: '病例类型' }, { prop: 'ownerName', label: '带教老师' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '学习状态', type: 'select' }, { prop: 'remark', label: '病例说明', type: 'textarea' }]
</script>
