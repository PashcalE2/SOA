<template>
  <table>
    <thead>
    <tr>
      <th rowspan="2">Id</th>
      <th rowspan="2">Name</th>
      <th colspan="2">Coordinates</th>
      <th rowspan="2">Creation date</th>
      <th rowspan="2">Number of participants</th>
      <th rowspan="2">Singles count</th>
      <th rowspan="2">Genre</th>
      <th colspan="3">Best album</th>
    </tr>
    <tr>
      <th>X</th>
      <th>Y</th>

      <th>Name</th>
      <th>Tracks</th>
      <th>Length</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(band, i) in this.musicBands">
      <td>{{ band.id }}</td>
      <td>{{ band.name }}</td>
      <td>{{ band.coordinates.x }}</td>
      <td>{{ band.coordinates.y }}</td>
      <td>{{ band.creationDate }}</td>
      <td>{{ band.numberOfParticipants }}</td>
      <td>{{ band.singlesCount }}</td>
      <td>{{ band.genre }}</td>
      <td>{{ band.album.name }}</td>
      <td>{{ band.album.tracks }}</td>
      <td>{{ band.album.length }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>

export default {
  props: ["musicBands"],

  data() {
    return {
      musicBandsList: []
    }
  },

  mounted() {
  }
}

</script>

<style scoped>

table, td, th {
  border-style: solid;
  border-width: thin;
  border-collapse: collapse;
  text-align: center;
  vertical-align: middle;
  background-color: #ffffff;
  margin: 0;
}

table {
  width: 100%;
  padding: 4px;
}

td {
  text-align: right;
}

th {
  background-color: inherit;
}

tbody {
  font-weight: normal;
}

thead {
  font-weight: bold;
  text-align: center;
  background-color: lightgray;
}

td:hover {
  background: #afdfff;
}

</style>