import { createRouter, createWebHistory } from 'vue-router';
import CityList from "../components/CityList.vue";
import CityForm from "../components/CityForm.vue";
import SecondService from '@/components/SecondService.vue'; // второй веб-сервис

const routes = [
    { path: "/", component: CityList },
    { path: "/create-city", component: CityForm },
    { path: "/edit-city/:id", component: CityForm },
    { path: "/second-service", component: SecondService },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
