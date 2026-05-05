import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3116, proxy: { '/api': { target: 'http://localhost:8116', changeOrigin: true } } }
})
