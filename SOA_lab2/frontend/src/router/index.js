import { createRouter, createWebHistory } from 'vue-router';
import CityList from "../components/CityList.vue";
import CityForm from "../components/CityForm.vue";

const routes = [
    { path: "/", component: CityList },
    { path: "/create-city", component: CityForm },
    { path: "/edit-city/:id", component: CityForm },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
