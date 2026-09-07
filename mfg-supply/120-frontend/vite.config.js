import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3120, proxy: { '/api': { target: 'http://localhost:8120', changeOrigin: true } } }
})
