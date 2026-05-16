import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3151, proxy: { '/api': { target: 'http://localhost:8151', changeOrigin: true } } }
})
