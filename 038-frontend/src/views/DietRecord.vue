<template>
  <div class="diet-record">
    <el-card>
      <template #header>
        <div class="header">
          <span>饮食记录</span>
          <el-button type="primary" @click="showAddDialog = true">
            <el-icon><Plus /></el-icon>添加记录
          </el-button>
        </div>
      </template>
      
      <el-table :data="recordList" stripe>
        <el-table-column prop="eatDate" label="日期" width="120" />
        <el-table-column prop="mealType" label="餐次" width="100" />
        <el-table-column prop="foodName" label="食物名称" />
        <el-table-column prop="weight" label="食用量(g)" width="100" />
        <el-table-column prop="calorie" label="热量(kcal)" width="110" />
        <el-table-column prop="protein" label="蛋白质(g)" width="100" />
        <el-table-column prop="fat" label="脂肪(g)" width="90" />
        <el-table-column prop="carbohydrate" label="碳水(g)" width="90" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" link>编辑</el-button>
            <el-button type="danger" size="small" link>删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 添加记录对话框 -->
    <el-dialog v-model="showAddDialog" title="添加饮食记录" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="日期">
          <el-date-picker v-model="form.eatDate" type="date" style="width: 100%" />
        </el-form-item>
        <el-form-item label="餐次">
          <el-select v-model="form.mealType" placeholder="请选择" style="width: 100%">
            <el-option label="早餐" value="BREAKFAST" />
            <el-option label="午餐" value="LUNCH" />
            <el-option label="晚餐" value="DINNER" />
            <el-option label="加餐" value="SNACK" />
          </el-select>
        </el-form-item>
        <el-form-item label="食物">
          <el-input v-model="form.foodName" placeholder="输入食物名称搜索" />
        </el-form-item>
        <el-form-item label="食用量(g)">
          <el-input-number v-model="form.weight" :min="1" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const showAddDialog = ref(false)
const recordList = ref([
  {
    eatDate: '2026-01-04',
    mealType: '早餐',
    foodName: '米饭',
    weight: 150,
    calorie: 174,
    protein: 3.9,
    fat: 0.45,
    carbohydrate: 38.85
  }
])

const form = ref({
  eatDate: new Date(),
  mealType: '',
  foodName: '',
  weight: 100
})

const handleAdd = () => {
  ElMessage.success('添加成功')
  showAddDialog.value = false
}
</script>

<style scoped>
.diet-record {
  width: 100%;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
