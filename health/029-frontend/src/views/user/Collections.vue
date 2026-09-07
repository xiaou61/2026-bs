<template>
  <section class="page-shell">
    <div class="page-head">
      <div>
        <p class="eyebrow">Favorites</p>
        <h1>我的收藏夹</h1>
      </div>
      <el-select v-model="filterType" @change="loadCollections" style="width: 180px">
        <el-option label="全部收藏" value="" />
        <el-option label="食谱收藏" value="0" />
        <el-option label="话题收藏" value="1" />
      </el-select>
    </div>

    <div v-loading="loading" class="card-list">
      <el-card v-for="item in collections" :key="item.id" class="item-card" shadow="never">
        <div class="item-head">
          <div>
            <el-tag size="small" :type="item.targetType === 0 ? 'success' : 'warning'">
              {{ item.targetType === 0 ? '食谱' : '话题' }}
            </el-tag>
            <h3>{{ item.title }}</h3>
            <p>{{ item.description }}</p>
          </div>
          <div class="item-actions">
            <el-button @click="openDetail(item)">查看详情</el-button>
            <el-button text type="danger" @click="remove(item.id)">移除收藏</el-button>
          </div>
        </div>
      </el-card>
      <el-empty v-if="!loading && !collections.length" description="你还没有收藏任何内容" />
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getCollections, removeCollection } from '../../api/user'
import { getRecipeDetail } from '../../api/recipe'
import { getTopicDetail } from '../../api/topic'

const router = useRouter()
const loading = ref(false)
const filterType = ref('')
const collections = ref([])

const loadCollections = async () => {
  loading.value = true
  try {
    const response = await getCollections(filterType.value === '' ? {} : { targetType: Number(filterType.value) })
    const rawCollections = response.data || []
    const detailList = await Promise.all(
      rawCollections.map(async (item) => {
        if (item.targetType === 0) {
          const detail = await getRecipeDetail(item.targetId)
          return {
            ...item,
            title: detail.data?.title || `食谱 #${item.targetId}`,
            description: detail.data?.description || '暂无简介'
          }
        }

        const detail = await getTopicDetail(item.targetId)
        return {
          ...item,
          title: detail.data?.title || `话题 #${item.targetId}`,
          description: detail.data?.content || '暂无内容'
        }
      })
    )
    collections.value = detailList
  } catch (error) {
    ElMessage.error(error?.message || '加载收藏失败')
  } finally {
    loading.value = false
  }
}

const openDetail = (item) => {
  router.push({
    name: item.targetType === 0 ? 'recipe-detail' : 'topic-detail',
    params: { id: item.targetId }
  })
}

const remove = async (id) => {
  try {
    await removeCollection(id)
    ElMessage.success('已移除收藏')
    await loadCollections()
  } catch (error) {
    ElMessage.error(error?.message || '移除失败')
  }
}

onMounted(loadCollections)
</script>

<style scoped>
.page-shell {
  max-width: 980px;
  margin: 0 auto;
}

.page-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-end;
  padding: 28px 30px;
  margin-bottom: 20px;
  border-radius: 24px;
  background: linear-gradient(135deg, #edf1e2 0%, #d8e3cb 100%);
}

.eyebrow {
  color: #7b806f;
  font-size: 12px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  margin-bottom: 8px;
}

.card-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.item-card {
  border-radius: 24px;
}

.item-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.item-head h3 {
  margin: 10px 0 8px;
  color: #183c2f;
}

.item-head p {
  color: #55655c;
  line-height: 1.7;
}

.item-actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

@media (max-width: 768px) {
  .page-head,
  .item-head {
    flex-direction: column;
  }

  .item-actions {
    flex-direction: row;
  }
}
</style>
