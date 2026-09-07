<template>
  <div class="route-detail" v-if="route">
    <el-card>
      <el-image :src="route.coverImage" class="cover-img" fit="cover" />
      <h1>{{ route.name }}</h1>
      <div class="meta">
        <el-tag><el-icon><Clock /></el-icon>{{ route.days }}天行程</el-tag>
        <el-tag type="info"><el-icon><Odometer /></el-icon>总里程 {{ route.totalDistance }}km</el-tag>
      </div>
      <el-divider />
      <h3>路线介绍</h3>
      <p class="desc">{{ route.description }}</p>
      <el-divider />
      <h3>途经景点</h3>
      <el-timeline>
        <el-timeline-item v-for="(spot, index) in spots" :key="spot.id" :timestamp="`第${spot.dayNum}天 - 第${spot.orderNum}站`" placement="top">
          <el-card shadow="hover" class="spot-card" @click="$router.push(`/spots/${spot.spotId}`)">
            <div class="spot-content">
              <el-image :src="spot.coverImage" class="spot-img" fit="cover" />
              <div class="spot-info">
                <h4>{{ spot.spotName }}</h4>
                <p class="address"><el-icon><Location /></el-icon>{{ spot.address }}</p>
                <p class="stay">建议游玩：{{ spot.stayMinutes }}分钟</p>
              </div>
            </div>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getRouteDetail, getRouteSpots } from '../api'

const routeInfo = useRoute()
const route = ref(null)
const spots = ref([])

onMounted(async () => {
  const id = routeInfo.params.id
  const res = await getRouteDetail(id)
  route.value = res.data
  const spotRes = await getRouteSpots(id)
  spots.value = spotRes.data || []
})
</script>

<style scoped>
.cover-img { width: 100%; height: 350px; border-radius: 8px; }
h1 { margin: 20px 0 15px; }
.meta { display: flex; gap: 15px; }
.desc { color: #666; line-height: 1.8; }
.spot-card { cursor: pointer; }
.spot-content { display: flex; gap: 15px; }
.spot-img { width: 150px; height: 100px; border-radius: 4px; flex-shrink: 0; }
.spot-info h4 { margin: 0 0 10px; }
.spot-info .address { color: #999; font-size: 13px; display: flex; align-items: center; gap: 4px; }
.spot-info .stay { color: #409eff; font-size: 13px; margin-top: 8px; }
</style>
