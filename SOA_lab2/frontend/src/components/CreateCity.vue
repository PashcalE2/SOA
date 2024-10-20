<template>
  <div>
    <h1>Create City</h1>
    <form @submit.prevent="createCity">
      <div>
        <label>Name:</label>
        <input v-model="city.name" required>
      </div>
      <div>
        <label>Population:</label>
        <input v-model="city.population" type="number" required>
      </div>
      <div>
        <label>Area:</label>
        <input v-model="city.area" type="number" required>
      </div>
      <button type="submit">Create</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      city: {
        name: "",
        population: 0,
        area: 0,
      },
    };
  },
  methods: {
    createCity() {
      axios
          .post("/api/cities", this.city)
          .then(() => {
            this.$router.push("/"); // Redirect to city list after creation
          })
          .catch((error) => {
            console.error("Error creating city:", error);
          });
    },
  },
};
</script>
