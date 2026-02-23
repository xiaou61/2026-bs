<template>
  <div class="movie-card" @click="handleClick">
    <div class="movie-poster">
      <img :src="movie.poster || '/default-poster.jpg'" :alt="movie.title" />
      <div class="movie-rating" v-if="movie.rating">
        <el-icon><Star /></el-icon>
        <span>{{ movie.rating }}</span>
      </div>
    </div>
    <div class="movie-info">
      <h4 class="movie-title">{{ movie.title }}</h4>
      <p class="movie-meta">{{ movie.category }} | {{ movie.duration }}分钟</p>
      <p class="movie-director">导演: {{ movie.director }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  movie: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['click'])

const handleClick = () => {
  emit('click', props.movie)
}
</script>

<style scoped>
.movie-card {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s;
  background: #fff;
}

.movie-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

.movie-poster {
  position: relative;
  width: 100%;
  height: 300px;
  overflow: hidden;
}

.movie-poster img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.movie-rating {
  position: absolute;
  top: 10px;
  right: 10px;
  background: rgba(0, 0, 0, 0.7);
  color: #ffd700;
  padding: 5px 10px;
  border-radius: 15px;
  display: flex;
  align-items: center;
  gap: 5px;
  font-weight: bold;
}

.movie-info {
  padding: 15px;
}

.movie-title {
  font-size: 16px;
  font-weight: bold;
  margin: 0 0 10px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.movie-meta {
  font-size: 14px;
  color: #666;
  margin: 5px 0;
}

.movie-director {
  font-size: 13px;
  color: #999;
  margin: 5px 0 0 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
