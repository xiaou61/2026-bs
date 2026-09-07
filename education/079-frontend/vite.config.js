import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3079,
    proxy: {
      '/api': {
        target: process.env.VITE_API_TARGET || 'http://localhost:8079',
        changeOrigin: true
      },
      '/upload': {
        target: process.env.VITE_API_TARGET || 'http://localhost:8079',
        changeOrigin: true
      }
    }
  }
})
