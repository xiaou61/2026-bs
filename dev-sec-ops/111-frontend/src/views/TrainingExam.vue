<template>
  <DataPage title="培训考试" description="安全考试、及格分数和考试状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTrainingExamPage, addTrainingExam, updateTrainingExam, deleteTrainingExam, publishTrainingExam, closeTrainingExam } from '../api'
const api = { page: getTrainingExamPage, add: addTrainingExam, update: updateTrainingExam, delete: deleteTrainingExam }
const columns = [{"prop": "examName", "label": "考试名称"}, {"prop": "examCode", "label": "考试编码"}, {"prop": "courseName", "label": "关联课程"}, {"prop": "passScore", "label": "及格分"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "examName", "label": "考试名称"}, {"prop": "examCode", "label": "考试编码"}, {"prop": "courseName", "label": "关联课程"}, {"prop": "passScore", "label": "及格分", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishTrainingExam(row.id)
  if (command === 'close') await closeTrainingExam(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
