<template><DataPage title="成果档案" description="成果编号、培养学生、成果类型、指导人员、归档时间和成果状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'MENTOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAchievementArchive, deleteAchievementArchive, finishAchievementArchive, getAchievementArchivePage, processAchievementArchive, updateAchievementArchive } from '../api'
const api = { page: getAchievementArchivePage, add: addAchievementArchive, update: updateAchievementArchive, delete: deleteAchievementArchive, process: processAchievementArchive, finish: finishAchievementArchive }
const columns = [{ prop: 'recordNo', label: '成果编号' }, { prop: 'recordName', label: '培养学生' }, { prop: 'category', label: '成果类型' }, { prop: 'ownerName', label: '指导人员' }, { prop: 'planTime', label: '归档时间' }, { prop: 'status', label: '成果状态' }, { prop: 'remark', label: '成果说明' }]
const formFields = [{ prop: 'recordNo', label: '成果编号' }, { prop: 'recordName', label: '培养学生' }, { prop: 'category', label: '成果类型' }, { prop: 'ownerName', label: '指导人员' }, { prop: 'planTime', label: '归档时间' }, { prop: 'status', label: '成果状态', type: 'select' }, { prop: 'remark', label: '成果说明', type: 'textarea' }]
</script>
