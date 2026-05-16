<template><DataPage title="学习进度" description="进度编号、学习课程、学习阶段、跟进讲师、更新时间和学习状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLearningProgress, deleteLearningProgress, finishLearningProgress, getLearningProgressPage, processLearningProgress, updateLearningProgress } from '../api'
const api = { page: getLearningProgressPage, add: addLearningProgress, update: updateLearningProgress, delete: deleteLearningProgress, process: processLearningProgress, finish: finishLearningProgress }
const columns = [{ prop: 'recordNo', label: '进度编号' }, { prop: 'recordName', label: '学习课程' }, { prop: 'category', label: '学习阶段' }, { prop: 'ownerName', label: '跟进讲师' }, { prop: 'planTime', label: '更新时间' }, { prop: 'status', label: '学习状态' }, { prop: 'remark', label: '进度说明' }]
const formFields = [{ prop: 'recordNo', label: '进度编号' }, { prop: 'recordName', label: '学习课程' }, { prop: 'category', label: '学习阶段' }, { prop: 'ownerName', label: '跟进讲师' }, { prop: 'planTime', label: '更新时间' }, { prop: 'status', label: '学习状态', type: 'select' }, { prop: 'remark', label: '进度说明', type: 'textarea' }]
</script>
