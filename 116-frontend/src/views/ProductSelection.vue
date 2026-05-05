<template>
  <DataPage title="选品池" description="候选商品、供应商、类目、供货价、佣金比例和审核状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getProductSelectionPage, addProductSelection, updateProductSelection, deleteProductSelection, shortlistProductSelection, approveProductSelection, rejectProductSelection } from '../api'
const api = { page: getProductSelectionPage, add: addProductSelection, update: updateProductSelection, delete: deleteProductSelection }
const columns = [{"prop": "selectionNo", "label": "选品编号"}, {"prop": "productName", "label": "商品名称"}, {"prop": "brandName", "label": "品牌"}, {"prop": "categoryName", "label": "类目"}, {"prop": "supplyPrice", "label": "供货价"}, {"prop": "commissionRate", "label": "佣金比例"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "selectionNo", "label": "选品编号"}, {"prop": "productName", "label": "商品名称"}, {"prop": "brandName", "label": "品牌"}, {"prop": "categoryName", "label": "类目", "type": "select", "options": [{"label": "美妆护肤", "value": "美妆护肤"}, {"label": "食品饮料", "value": "食品饮料"}, {"label": "家居百货", "value": "家居百货"}, {"label": "数码配件", "value": "数码配件"}]}, {"prop": "supplyPrice", "label": "供货价", "type": "number"}, {"prop": "commissionRate", "label": "佣金比例", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "SHORTLISTED", "value": "SHORTLISTED"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "shortlist", "label": "入围", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}, {"command": "reject", "label": "驳回", "type": "danger"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'shortlist') await shortlistProductSelection(row.id)
  if (command === 'approve') await approveProductSelection(row.id)
  if (command === 'reject') await rejectProductSelection(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
