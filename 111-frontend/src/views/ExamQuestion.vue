<template>
  <DataPage title="考试题目" description="题库题目、题型、分值和启用状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getExamQuestionPage, addExamQuestion, updateExamQuestion, deleteExamQuestion } from '../api'
const api = { page: getExamQuestionPage, add: addExamQuestion, update: updateExamQuestion, delete: deleteExamQuestion }
const columns = [{"prop": "questionTitle", "label": "题目"}, {"prop": "examName", "label": "考试"}, {"prop": "questionType", "label": "题型"}, {"prop": "answerKey", "label": "答案"}, {"prop": "scoreValue", "label": "分值"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "questionTitle", "label": "题目", "type": "textarea"}, {"prop": "examName", "label": "考试"}, {"prop": "questionType", "label": "题型", "type": "select", "options": [{"label": "SINGLE", "value": "SINGLE"}, {"label": "MULTIPLE", "value": "MULTIPLE"}, {"label": "JUDGE", "value": "JUDGE"}]}, {"prop": "answerKey", "label": "答案"}, {"prop": "scoreValue", "label": "分值", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = []
const defaults = {"questionType": "SINGLE", "status": "ACTIVE"}
const handleAction = () => {}
</script>
