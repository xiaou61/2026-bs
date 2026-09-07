import { defineStore } from 'pinia'
import { getStudyRooms, getRoomDetail } from '@/api/studyRoom'
import { getSeats } from '@/api/seat'

export const useRoomStore = defineStore('room', {
  state: () => ({
    roomList: [],
    currentRoom: null,
    currentSeats: [],
    selectedSeat: null
  }),

  getters: {
    availableRooms: (state) => state.roomList.filter(room => room.status === 1),
    availableSeatsCount: (state) => state.currentSeats.filter(seat => seat.seatStatus === 1).length,
    occupiedSeatsCount: (state) => state.currentSeats.filter(seat => seat.seatStatus === 2).length
  },

  actions: {
    async fetchRooms() {
      try {
        const res = await getStudyRooms()
        this.roomList = res.data
        return res
      } catch (error) {
        throw error
      }
    },

    async fetchRoomDetail(roomId) {
      try {
        const res = await getRoomDetail(roomId)
        this.currentRoom = res.data
        return res
      } catch (error) {
        throw error
      }
    },

    async fetchSeats(roomId) {
      try {
        const res = await getSeats({ roomId })
        this.currentSeats = res.data
        return res
      } catch (error) {
        throw error
      }
    },

    selectSeat(seat) {
      this.selectedSeat = seat
    },

    clearSelection() {
      this.selectedSeat = null
    },

    updateSeatStatus(seatId, status) {
      const seat = this.currentSeats.find(s => s.id === seatId)
      if (seat) {
        seat.seatStatus = status
      }
    }
  }
})