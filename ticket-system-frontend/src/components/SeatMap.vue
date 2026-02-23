<template>
  <div class="seat-map-component">
    <div class="screen">银幕中央</div>
    <div class="seat-grid">
      <div v-for="row in rows" :key="row" class="seat-row">
        <span class="row-label">{{ row }}排</span>
        <div
          v-for="col in cols"
          :key="`${row}-${col}`"
          :class="['seat', getSeatStatus(row, col)]"
          @click="handleSeatClick(row, col)"
        >
          {{ col }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  rows: {
    type: Number,
    default: 10
  },
  cols: {
    type: Number,
    default: 15
  },
  seats: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['select-seat'])

const getSeatStatus = (row, col) => {
  const seat = props.seats.find(s => s.rowNum === row && s.colNum === col)
  if (!seat) return 'empty'
  if (seat.selected) return 'selected'
  if (seat.status === 'sold') return 'sold'
  return 'available'
}

const handleSeatClick = (row, col) => {
  emit('select-seat', { row, col })
}
</script>

<style scoped>
.seat-map-component {
  width: 100%;
}

.screen {
  text-align: center;
  background: linear-gradient(to bottom, #666, #333);
  color: #fff;
  padding: 10px;
  margin-bottom: 30px;
  border-radius: 5px;
}

.seat-grid {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.seat-row {
  display: flex;
  align-items: center;
  gap: 8px;
}

.row-label {
  width: 40px;
  text-align: right;
  font-size: 12px;
  color: #666;
}

.seat {
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #ddd;
  border-radius: 3px;
  cursor: pointer;
  font-size: 10px;
  transition: all 0.2s;
}

.seat.available {
  background-color: #67c23a;
  color: #fff;
}

.seat.selected {
  background-color: #409eff;
  color: #fff;
}

.seat.sold {
  background-color: #ddd;
  color: #999;
  cursor: not-allowed;
}

.seat.empty {
  opacity: 0;
  cursor: default;
}
</style>
