import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5020,
    proxy: {
      '/api': {
        target: 'http://localhost:8020',
        changeOrigin: true
      }
    }
  }
})

