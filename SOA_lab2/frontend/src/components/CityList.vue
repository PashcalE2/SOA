<template>
  <div>
    <h1>Cities</h1>
    <div class="filter-sort-container">
      <div class="filter-container">
        <label>Filter Fields:</label>
        <input v-model="filterFields" placeholder="e.g., name,population" />
        <label>Filter Values:</label>
        <input v-model="filterValues" placeholder="e.g., New York,100000" />
        <button @click="getCities">Apply Filter</button>
      </div>

      <div class="sort-container">
        <label>Sort Fields:</label>
        <input v-model="sortFields" placeholder="e.g., id,name" />
        <label>Sort Order:</label>
        <select v-model="sortOrder">
          <option value="asc">Ascending</option>
          <option value="desc">Descending</option>
        </select>
        <button @click="getCities">Sort</button>
      </div>
    </div>

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

    <div class="pagination">
      <button @click="previousPage" :disabled="page <= 1">Previous</button>
      <button @click="nextPage">Next</button>
    </div>

    <router-link to="/create-city" class="router-link">Create New City</router-link>
  </div>
</template>


<script>
import axios from "axios";

export default {
  data() {
    return {
      cities: [],
      filterFields: "", // Строка с полями фильтрации, разделенными запятыми
      filterValues: "", // Строка со значениями фильтрации, разделенными запятыми
      sortFields: "", // Строка с полями сортировки, разделенными запятыми
      sortOrder: "asc", // Порядок сортировки
      page: 1,
      size: 10,
    };
  },
  methods: {
    getCities() {
      // Формирование URL с фильтрами и сортировками
      const filterFieldsArray = this.filterFields.split(/,\s*/); // Разделение по "," или ", "
      const filterValuesArray = this.filterValues.split(/,\s*/); // Разделение по "," или ", "
      const sortFieldsArray = this.sortFields.split(/,\s*/); // Разделение по "," или ", "

      // Убедитесь, что количество полей фильтрации совпадает с количеством значений
      if (filterFieldsArray.length !== filterValuesArray.length) {
        alert("Number of filter fields must match number of filter values.");
        return;
      }

      let url = `/api/cities/${filterFieldsArray.join(",")}/${filterValuesArray.join(",")}/${sortFieldsArray.join(",")}/${this.sortOrder}/${this.page}/${this.size}`;

      axios
          .get(url)
          .then((response) => {
            this.cities = response.data;
          })
          .catch((error) => {
            console.error("Error fetching cities:", error);
          });
    },
    deleteCity(id) {
      axios
          .delete(`/api/cities/${id}`)
          .then(() => {
            this.getCities(); // Перезагружаем список после удаления
          })
          .catch((error) => {
            console.error("Error deleting city:", error);
          });
    },
    previousPage() {
      if (this.page > 1) {
        this.page--;
        this.getCities();
      }
    },
    nextPage() {
      this.page++;
      this.getCities();
    },
    editCity(id) {
      this.$router.push(`/edit-city/${id}`);
    },
  },
  mounted() {
    this.getCities();
  },
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #007bff;
  color: white;
  cursor: pointer; /* Добавляем указатель для заголовков */
}

tr:hover {
  background-color: #f1f1f1;
}
</style>
