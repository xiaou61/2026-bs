import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3053,
    proxy: {
      '/api': {
        target: 'http://localhost:8053',
        changeOrigin: true
      }
    }
  }
})
