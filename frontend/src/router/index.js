import { createWebHistory, createRouter } from "vue-router";
import Home from "@/views/Main/Home.vue";
import NaverCallback from "@/views/Main/NaverCallback.vue";
import GameView from "@/views/GameMain/GameView.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: '/navercallback',
    name: 'NaverCallback',
    component: NaverCallback
  },
  {
    path: '/game',
    name: 'GameView',
    component: GameView
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;