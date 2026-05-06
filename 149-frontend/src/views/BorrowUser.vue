<template>
  <DataPage title="论文投稿" description="投稿编号、论文题目、作者姓名、提交版本、附件数量和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getBorrowUserPage, addBorrowUser, updateBorrowUser, deleteBorrowUser, processBorrowUser, finishBorrowUser } from '../api'
const api = { page: getBorrowUserPage, add: addBorrowUser, update: updateBorrowUser, delete: deleteBorrowUser }
const columns = [{"prop": "stockNo", "label": "投稿编号"}, {"prop": "consumableName", "label": "论文题目"}, {"prop": "labName", "label": "作者姓名"}, {"prop": "currentQty", "label": "提交版本"}, {"prop": "lockedQty", "label": "附件数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "stockNo", "label": "投稿编号"}, {"prop": "consumableName", "label": "论文题目"}, {"prop": "labName", "label": "作者姓名"}, {"prop": "currentQty", "label": "提交版本", "type": "number"}, {"prop": "lockedQty", "label": "附件数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processBorrowUser(row.id)
  if (command === 'finish') await finishBorrowUser(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>









