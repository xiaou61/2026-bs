import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3063,
    proxy: {
      '/api': {
        target: 'http://localhost:8063',
        changeOrigin: true
      }
    }
  }
})
