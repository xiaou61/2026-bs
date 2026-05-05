<template>
  <DataPage title="主播档案" description="主播资料、擅长品类、等级、佣金比例和签约状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAnchorProfilePage, addAnchorProfile, updateAnchorProfile, deleteAnchorProfile, certifyAnchorProfile, freezeAnchorProfile } from '../api'
const api = { page: getAnchorProfilePage, add: addAnchorProfile, update: updateAnchorProfile, delete: deleteAnchorProfile }
const columns = [{"prop": "anchorName", "label": "主播姓名"}, {"prop": "anchorNo", "label": "主播编号"}, {"prop": "specialCategory", "label": "擅长品类"}, {"prop": "levelName", "label": "等级"}, {"prop": "commissionRate", "label": "佣金比例"}, {"prop": "phone", "label": "手机"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "anchorName", "label": "主播姓名"}, {"prop": "anchorNo", "label": "主播编号"}, {"prop": "specialCategory", "label": "擅长品类"}, {"prop": "levelName", "label": "等级", "type": "select", "options": [{"label": "新锐主播", "value": "新锐主播"}, {"label": "金牌主播", "value": "金牌主播"}, {"label": "头部主播", "value": "头部主播"}]}, {"prop": "commissionRate", "label": "佣金比例", "type": "number"}, {"prop": "phone", "label": "手机"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "CERTIFIED", "value": "CERTIFIED"}, {"label": "FROZEN", "value": "FROZEN"}]}]
const rowActions = [{"command": "certify", "label": "认证", "type": "success"}, {"command": "freeze", "label": "冻结", "type": "danger"}]
const defaults = {"status": "PENDING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'certify') await certifyAnchorProfile(row.id)
  if (command === 'freeze') await freezeAnchorProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
