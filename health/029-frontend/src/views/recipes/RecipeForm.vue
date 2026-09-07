<template>
  <section class="page-shell">
    <div class="page-head">
      <div>
        <p class="eyebrow">Creator Studio</p>
        <h1>发布新的食疗食谱</h1>
        <p>填写基础信息、调养亮点和食材组合，提交后会进入后台审核。</p>
      </div>
    </div>

    <el-card class="form-card" shadow="never">
      <el-form label-position="top" :model="form" class="recipe-form">
        <el-row :gutter="20">
          <el-col :md="12" :xs="24">
            <el-form-item label="食谱标题">
              <el-input v-model="form.title" maxlength="100" show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="封面图片">
              <el-input v-model="form.coverImage" placeholder="可填写图片链接" />
            </el-form-item>
          </el-col>
          <el-col :xs="24">
            <el-form-item label="食谱简介">
              <el-input v-model="form.description" type="textarea" :rows="4" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="调养功效">
              <el-input v-model="form.efficacy" type="textarea" :rows="3" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="营养特点">
              <el-input v-model="form.nutrition" type="textarea" :rows="3" />
            </el-form-item>
          </el-col>
          <el-col :md="8" :xs="24">
            <el-form-item label="难度">
              <el-input-number v-model="form.difficulty" :min="1" :max="5" />
            </el-form-item>
          </el-col>
          <el-col :md="8" :xs="24">
            <el-form-item label="烹饪时间（分钟）">
              <el-input-number v-model="form.cookTime" :min="5" :max="240" />
            </el-form-item>
          </el-col>
          <el-col :md="8" :xs="24">
            <el-form-item label="份量">
              <el-input-number v-model="form.servings" :min="1" :max="12" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="适用人群">
              <el-input v-model="form.applicablePeople" placeholder="例如：白领、学生、气虚体质人群" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="适用季节">
              <el-input v-model="form.seasons" placeholder="例如：春秋 / 四季 / 夏季" />
            </el-form-item>
          </el-col>
        </el-row>

        <div class="ingredient-head">
          <div>
            <h2>食材清单</h2>
            <p>可选填，至少推荐关联 1 种平台食材，方便详情页联动。</p>
          </div>
          <el-button @click="addIngredient">添加食材</el-button>
        </div>

        <div class="ingredient-list">
          <div v-for="(item, index) in form.ingredients" :key="index" class="ingredient-row">
            <el-select v-model="item.ingredientId" placeholder="选择食材" filterable>
              <el-option
                v-for="ingredient in ingredientOptions"
                :key="ingredient.id"
                :label="ingredient.name"
                :value="ingredient.id"
              />
            </el-select>
            <el-input v-model="item.quantity" placeholder="数量" />
            <el-input v-model="item.unit" placeholder="单位" />
            <el-button text type="danger" @click="removeIngredient(index)">删除</el-button>
          </div>
        </div>

        <div class="actions">
          <el-button @click="router.push({ name: 'my-recipes' })">取消</el-button>
          <el-button type="primary" :loading="submitting" @click="submitRecipe">提交审核</el-button>
        </div>
      </el-form>
    </el-card>
  </section>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createRecipe } from '../../api/recipe'
import { searchIngredients } from '../../api/ingredient'

const router = useRouter()
const submitting = ref(false)
const ingredientOptions = ref([])
const form = reactive({
  title: '',
  description: '',
  coverImage: '',
  efficacy: '',
  nutrition: '',
  difficulty: 2,
  cookTime: 30,
  servings: 2,
  applicablePeople: '',
  seasons: '',
  ingredients: []
})

const addIngredient = () => {
  form.ingredients.push({
    ingredientId: undefined,
    quantity: '',
    unit: ''
  })
}

const removeIngredient = (index) => {
  form.ingredients.splice(index, 1)
}

const loadIngredients = async () => {
  try {
    const response = await searchIngredients('')
    ingredientOptions.value = response.data || []
  } catch (error) {
    ElMessage.warning(error?.message || '食材选项加载失败')
  }
}

const submitRecipe = async () => {
  if (!form.title.trim() || !form.description.trim()) {
    ElMessage.warning('请先补全标题和简介')
    return
  }

  submitting.value = true
  try {
    await createRecipe({
      ...form,
      ingredients: form.ingredients.filter((item) => item.ingredientId && item.quantity && item.unit)
    })
    ElMessage.success('食谱已提交，等待审核')
    router.push({ name: 'my-recipes' })
  } catch (error) {
    ElMessage.error(error?.message || '提交失败')
  } finally {
    submitting.value = false
  }
}

onMounted(loadIngredients)
</script>

<style scoped>
.page-shell {
  max-width: 1100px;
  margin: 0 auto;
}

.page-head {
  padding: 30px 32px;
  border-radius: 28px;
  background: linear-gradient(135deg, #204733 0%, #3b6f52 55%, #c7813e 100%);
  color: #f7f4ed;
  margin-bottom: 20px;
}

.eyebrow {
  font-size: 12px;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  margin-bottom: 8px;
  opacity: 0.8;
}

.form-card {
  border-radius: 24px;
}

.ingredient-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
  margin: 8px 0 16px;
}

.ingredient-head h2 {
  color: #183c2f;
}

.ingredient-head p {
  color: #687567;
}

.ingredient-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.ingredient-row {
  display: grid;
  grid-template-columns: 1.2fr 0.8fr 0.8fr auto;
  gap: 12px;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 28px;
}

@media (max-width: 768px) {
  .ingredient-head,
  .actions {
    flex-direction: column;
    align-items: stretch;
  }

  .ingredient-row {
    grid-template-columns: 1fr;
  }
}
</style>
