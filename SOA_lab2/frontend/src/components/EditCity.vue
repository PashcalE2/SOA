<template>
  <div>
    <h1>Edit City</h1>
    <form @submit.prevent="updateCity">
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
      <button type="submit">Update</button>
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
    updateCity() {
      axios
          .put(`/api/cities/${this.$route.params.id}`, this.city)
          .then(() => {
            this.$router.push("/"); // Redirect to city list after update
          })
          .catch((error) => {
            console.error("Error updating city:", error);
          });
    },
  },
  mounted() {
    axios
        .get(`/api/cities/${this.$route.params.id}`)
        .then((response) => {
          this.city = response.data;
        })
        .catch((error) => {
          console.error("Error fetching city:", error);
        });
  },
};
</script>
