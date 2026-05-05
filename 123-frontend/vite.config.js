import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3123, proxy: { '/api': { target: 'http://localhost:8123', changeOrigin: true } } }
})
