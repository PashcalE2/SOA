<template>
  <div>
    <h1>Secondary Service</h1>

    <div class="filter-sort-container">
      <div class="filter-container">
        <label for="id1">City ID 1:</label>
        <input type="number" v-model="cityIds.id1" required />
        <label for="id2">City ID 2:</label>
        <input type="number" v-model="cityIds.id2" required />
        <label for="id3">City ID 3:</label>
        <input type="number" v-model="cityIds.id3" required />
        <button @click="getTotalPopulation">Get Total Population</button>
      </div>
    </div>
    <p v-if="totalPopulation !== null">Total Population: {{ totalPopulation }}</p>

    <div class="filter-sort-container">
      <div class="filter-container">
        <label for="moveId">City ID to Move Population:</label>
        <input type="number" v-model="moveCityId" required />
        <button @click="movePopulation">Move to Poorest City</button>
      </div>
    </div>

    <p v-if="moveMessage">{{ moveMessage }}</p>

    <router-link to="/" class="router-link route">Back to Main Service</router-link>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cityIds: {
        id1: null,
        id2: null,
        id3: null,
      },
      moveCityId: null,
      totalPopulation: null,
      moveMessage: '',
    };
  },
  methods: {
    getTotalPopulation() {
      const { id1, id2, id3 } = this.cityIds;
      if (id1 && id2 && id3) {
        fetch(`https://localhost:22600/genocide/count/${id1}/${id2}/${id3}`)
            .then((response) => {
              if (!response.ok) {
                if (response.status === 404) {
                  throw new Error('Some cities were not found.');
                } else if (response.status === 400) {
                  throw new Error('Bad request.');
                } else {
                  throw new Error('An error occurred.');
                }
              }
              return response.text(); // Ожидается XML
            })
            .then((data) => {
              this.totalPopulation = parseInt(data, 10);
            })
            .catch((error) => {
              alert(error.message);
              console.error('Error fetching total population:', error);
            });
      } else {
        alert('Please enter valid city IDs.');
      }
    },
    movePopulation() {
      if (this.moveCityId) {
        fetch(`https://localhost:22600/genocide/move-to-poorest/${this.moveCityId}`, {
          method: 'POST',
        })
            .then((response) => {
              if (!response.ok) {
                if (response.status === 400) {
                  throw new Error('Bad request.');
                } else if (response.status === 409) {
                  throw new Error('City is already in the poorest city.');
                } else {
                  throw new Error('An error occurred.');
                }
              }
              return response.text();
            })
            .then(() => {
              this.moveMessage = 'The population of the city is successfully moved!';
            })
            .catch((error) => {
              alert(error.message);
              console.error('Error moving population:', error);
            });
      } else {
        alert('Please enter a valid city ID.');
      }
    },
  },
};
</script>