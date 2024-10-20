<template>
  <div>
    <h1>Cities</h1>

    <!-- Filter and Sort Section -->
    <div class="filter-sort-container">
      <div class="filter-container">
        <label>Filter Fields:</label>
        <input v-model="filterFields" placeholder="e.g., name,population" />
        <label>Filter Values:</label>
        <input v-model="filterValues" placeholder="e.g., New York,100000" />
        <button @click="applyFilter">Apply Filter</button>
      </div>

      <div class="sort-container">
        <label>Sort Fields:</label>
        <input v-model="sortFields" placeholder="e.g., id,name" />
        <label>Sort Order:</label>
        <select v-model="sortOrder">
          <option value="asc">Ascending</option>
          <option value="desc">Descending</option>
        </select>
        <button @click="sortCities">Sort</button>
      </div>
    </div>

    <!-- Governor ID Deletion Section -->
    <div class="governor-container">
      <label>Governor ID to Delete:</label>
      <input v-model="governorId" type="number" placeholder="Governor ID" />
      <button @click="deleteCitiesByGovernor">Delete Cities by Governor</button>
    </div>

    <!-- Group by ID Section -->
    <div class="group-container">
      <button @click="groupCitiesById">Group Cities by ID</button>
    </div>

    <!-- Count by Climate Section -->
    <div class="climate-container">
      <label>Climate to Filter By:</label>
      <input v-model="climate" type="text" placeholder="Climate (e.g., TROPICAL_SAVANNA)" />
      <button @click="countCitiesByClimate">Count Cities by Climate</button>
    </div>

    <!-- Table Section -->
    <div class="table-container">
      <table>
        <thead>
        <tr>
          <th @click="sort('id')" style="cursor: pointer;">ID</th>
          <th @click="sort('name')" style="cursor: pointer;">Name</th>
          <th @click="sort('population')" style="cursor: pointer;">Population</th>
          <th @click="sort('area')" style="cursor: pointer;">Area</th>
          <th @click="sort('establishmentDate')" style="cursor: pointer;">Establishment Date</th>
          <th @click="sort('metersAboveSeaLevel')" style="cursor: pointer;">Meters Above Sea Level</th>
          <th @click="sort('telephoneCode')" style="cursor: pointer;">Telephone Code</th>
          <th @click="sort('governor.name')" style="cursor: pointer;">Governor</th>
          <th @click="sort('climate')" style="cursor: pointer;">Climate</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="city in cities" :key="city.id" @click="editCity(city.id)" style="cursor: pointer;">
          <td>{{ city.id }}</td>
          <td>{{ city.name }}</td>
          <td>{{ city.population }}</td>
          <td>{{ city.area }}</td>
          <td>{{ city.establishmentDate }}</td>
          <td>{{ city.metersAboveSeaLevel }}</td>
          <td>{{ city.telephoneCode }}</td>
          <td>{{ city.governor ? city.governor.name : 'N/A' }}</td>
          <td>{{ city.climate }}</td>
          <td>
            <button @click.stop="deleteCity(city.id)">Delete</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- Pagination Section -->
    <div class="pagination">
      <button @click="previousPage" :disabled="page <= 1">Previous</button>
      <button @click="nextPage">Next</button>
    </div>

    <!-- Create City Link -->
    <router-link to="/create-city" class="router-link">Create New City</router-link>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cities: [],
      filterFields: '',
      filterValues: '',
      sortFields: '',
      sortOrder: 'asc',
      page: 1,
      size: 10,
      governorId: null, // Governor ID for deletion
      climate: '', // Climate for counting cities
    };
  },
  methods: {
    async getCities() {
      try {
        const response = await fetch(`/cities/${this.filterFields}/${this.filterValues}/${this.sortFields}/${this.sortOrder}/${this.page}/${this.size}`);
        if (!response.ok) {
          this.handleError(response.status);
          return;
        }
        const data = await response.json();
        this.cities = data;
      } catch (error) {
        alert("An unexpected error occurred: " + error.message);
      }
    },
    applyFilter() {
      this.page = 1;
      this.getCities();
    },
    sortCities() {
      this.page = 1;
      this.getCities();
    },
    async deleteCitiesByGovernor() {
      if (!this.governorId) {
        alert("Please enter a valid Governor ID.");
        return;
      }

      try {
        const response = await fetch(`/cities/delete-by-governor/${this.governorId}`, { method: 'DELETE' });
        if (!response.ok) {
          this.handleError(response.status);
          return;
        }
        alert("Cities successfully deleted.");
        this.getCities();
      } catch (error) {
        alert("An unexpected error occurred: " + error.message);
      }
    },
    async groupCitiesById() {
      try {
        const response = await fetch(`/cities/group-by-id`);
        if (!response.ok) {
          this.handleError(response.status);
          return;
        }
        const data = await response.json();
        alert(JSON.stringify(data, null, 2));
      } catch (error) {
        alert("An unexpected error occurred: " + error.message);
      }
    },
    async countCitiesByClimate() {
      if (!this.climate) {
        alert("Please enter a valid climate.");
        return;
      }

      try {
        const response = await fetch(`/cities/count-by-climate/${this.climate}`);
        if (!response.ok) {
          this.handleError(response.status);
          return;
        }
        const count = await response.json();
        alert(`Number of cities with climate greater than ${this.climate}: ${count}`);
      } catch (error) {
        alert("An unexpected error occurred: " + error.message);
      }
    },
    handleError(status) {
      switch (status) {
        case 400:
          alert("Bad Request: Please check your input.");
          break;
        case 404:
          alert("Resource not found.");
          break;
        case 406:
          alert("Invalid input.");
          break;
        case 409:
          alert("Conflict: Cities with such governor not found.");
          break;
        case 500:
          alert("Internal server error. Please try again later.");
          break;
        case 501:
          alert("Action not implemented.");
          break;
        default:
          alert("An unknown error occurred.");
      }
    },
    editCity(id) {
      this.$router.push(`/edit-city/${id}`);
    },
    nextPage() {
      this.page++;
      this.getCities();
    },
    previousPage() {
      this.page--;
      this.getCities();
    },
    sort(field) {
      this.sortFields = field;
      this.getCities();
    }
  }
};
</script>

<style scoped>
.success {
  background-color: #4CAF50;
  color: white;
}
.error {
  background-color: #f44336;
  color: white;
}
</style>