<template>
  <div>
    <h1>{{ isEditMode ? 'Edit City' : 'Add City' }}</h1>
    <form @submit.prevent="submitForm">
      <div>
        <label>Name*:</label>
        <input v-model="city.name" required />
        <span v-if="errors.name" class="error">{{ errors.name }}</span>
      </div>
      <div>
        <label>Coordinates X*:</label>
        <input v-model.number="city.coordinates.x" required />
        <span v-if="errors.coordinatesX" class="error">{{ errors.coordinatesX }}</span>
      </div>
      <div>
        <label>Coordinates Y*:</label>
        <input v-model.number="city.coordinates.y" required />
        <span v-if="errors.coordinatesY" class="error">{{ errors.coordinatesY }}</span>
      </div>
      <div>
        <label>Area*:</label>
        <input v-model.number="city.area" required />
        <span v-if="errors.area" class="error">{{ errors.area }}</span>
      </div>
      <div>
        <label>Population*:</label>
        <input v-model.number="city.population" required />
        <span v-if="errors.population" class="error">{{ errors.population }}</span>
      </div>
      <div>
        <label>Meters Above Sea Level:</label>
        <input v-model.number="city.metersAboveSeaLevel" />
      </div>
      <div>
        <label>Establishment Date:</label>
        <input type="date" v-model="city.establishmentDate" />
      </div>
      <div>
        <label>Telephone Code*:</label>
        <input v-model.number="city.telephoneCode" required />
        <span v-if="errors.telephoneCode" class="error">{{ errors.telephoneCode }}</span>
      </div>
      <div>
        <label>Climate:</label>
        <select v-model="city.climate">
          <option v-for="climate in climates" :key="climate" :value="climate">{{ climate }}</option>
        </select>
      </div>
      <div>
        <label>Governor Name*:</label>
        <input v-model="city.governor.name" required />
        <span v-if="errors.governorName" class="error">{{ errors.governorName }}</span>
      </div>
      <div>
        <label>Governor Age*:</label>
        <input v-model.number="city.governor.age" required />
        <span v-if="errors.governorAge" class="error">{{ errors.governorAge }}</span>
      </div>
      <div>
        <label>Governor Height*:</label>
        <input v-model.number="city.governor.height" required />
        <span v-if="errors.governorHeight" class="error">{{ errors.governorHeight }}</span>
      </div>
      <button type="submit">{{ isEditMode ? 'Update City' : 'Create City' }}</button>
      <router-link to="/">Cancel</router-link>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      city: {
        id: null,
        name: "",
        coordinates: { x: null, y: null },
        area: null,
        population: null,
        metersAboveSeaLevel: null,
        establishmentDate: null,
        telephoneCode: null,
        climate: null,
        governor: {
          name: "",
          age: null,
          height: null,
        },
      },
      climates: [
        "TROPICAL_SAVANNA",
        "HUMIDCONTINENTAL",
        "SUBARCTIC",
        "POLAR_ICECAP",
        "DESERT",
      ],
      isEditMode: false,
      errors: {},
    };
  },
  methods: {
    validateFields() {
      this.errors = {}; // Сброс ошибок
      let isValid = true;

      if (!this.city.name) {
        this.errors.name = "Name is required.";
        isValid = false;
      }
      if (this.city.coordinates.x == null) {
        this.errors.coordinatesX = "Coordinates X is required.";
        isValid = false;
      }
      if (this.city.coordinates.y == null) {
        this.errors.coordinatesY = "Coordinates Y is required.";
        isValid = false;
      }
      if (this.city.area <= 0) {
        this.errors.area = "Area must be greater than 0.";
        isValid = false;
      }
      if (this.city.population <= 0) {
        this.errors.population = "Population must be greater than 0.";
        isValid = false;
      }
      if (this.city.telephoneCode <= 0 || this.city.telephoneCode > 100000) {
        this.errors.telephoneCode = "Telephone Code must be greater than 0 and less than or equal to 100000.";
        isValid = false;
      }
      if (!this.city.governor.name) {
        this.errors.governorName = "Governor Name is required.";
        isValid = false;
      }
      if (this.city.governor.age <= 0) {
        this.errors.governorAge = "Governor Age must be greater than 0.";
        isValid = false;
      }
      if (this.city.governor.height <= 0) {
        this.errors.governorHeight = "Governor Height must be greater than 0.";
        isValid = false;
      }

      return isValid;
    },
    handleError(status) {
      switch (status) {
        case 400:
          alert("Bad Request: Please check your input.");
          break;
        case 404:
          alert("City not found.");
          break;
        case 406:
          alert("City ID was not given.");
          break;
        case 500:
          alert("Internal server error. Please try again later.");
          break;
        default:
          alert("An unknown error occurred.");
      }
    },
    submitForm() {
      if (!this.validateFields()) {
        return; // Если поля не валидны, не продолжаем
      }

      const url = this.isEditMode
          ? `/api/cities/${this.city.id}`
          : `/api/cities`;
      const method = this.isEditMode ? 'put' : 'post';

      axios[method](url, this.city)
          .then(() => {
            alert("City saved successfully!"); // Успешное сохранение
            this.$router.push("/"); // Перенаправление на список городов
          })
          .catch((error) => {
            console.error("Error saving city:", error);
            this.handleError(error.response?.status); // Вызов метода обработки ошибок
          });
    },
    loadCity(id) {
      axios.get(`/api/cities/${id}`)
          .then(response => {
            this.city = response.data;
          })
          .catch(error => {
            console.error("Error fetching city:", error);
            this.handleError(error.response?.status); // Вызов метода обработки ошибок
          });
    },
  },
  mounted() {
    const cityId = this.$route.params.id;
    if (cityId) {
      this.isEditMode = true;
      this.loadCity(cityId);
    }
  },
};
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  max-width: 400px;
}

label {
  margin-top: 10px;
}

.error {
  color: red;
  font-size: 0.9em;
}
</style>
