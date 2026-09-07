import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3073,
    proxy: {
      '/api': {
        target: process.env.VITE_API_TARGET || 'http://localhost:8073',
        changeOrigin: true
      }
    }
  }
})
