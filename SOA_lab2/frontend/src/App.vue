<template>
  <header id="header">
    Header
  </header>

  <div class="column" style="padding: 2%">
    <div>
      <Table
          v-bind:music-bands="this.musicBands"
      />
    </div>

    <div>

    </div>
  </div>
</template>

<script>
import Table from "@/components/Table.vue";

export default {
  components: {Table},

  data() {
    return {
      musicBands: []
    }
  },

  mounted() {
    for (let i = 0; i < 10; i++) {
      this.putBand(
          i,
          "Str" + i,
          { x: Math.random(), y: Math.random() },
          Date.now(),
          i,
          i,
          i,
          { name: "Str" + i, tracks: i, length: i}
      )
    }
  },

  methods: {
    putBand(id, name, coordinates, creationDate, numberOfParticipants, singlesCount, genre, album) {
      this.musicBands.push({
        id: id,
        name: name,
        coordinates: coordinates,
        creationDate: creationDate,
        numberOfParticipants: numberOfParticipants,
        singlesCount: singlesCount,
        genre: genre,
        album: album
      })
    },
  }
}
</script>

<style>
body {
  margin: 0;
}

body, div {
  text-align: center;
  background-color: #ffffff;
  line-height: 1.5;
  color: black;
  font-family: "Segoe UI", serif;
  font-size: 18px;
}

pre {
  box-sizing: border-box;
  overflow: auto;
  white-space: pre;
}

h2 {
  font-size: 1.5rem;
  line-height: 1.2;
  font-weight: 500;
}

h3 {
  font-size: 1.75rem;
  line-height: 1.2;
  font-weight: 500;
}

p, pre, h1, h2, h3 {
  text-align: left;
  margin-top: 0;
  margin-bottom: 1rem;
}

code {
  font-family: SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace;;
  font-size: 16px;
  box-sizing: border-box;
  color: black;
  word-break: normal;
}

div.column, .centered_column {
  display: flex;
  flex-direction: column;
}

div.centered_column {
  align-items: center
}

div.row {
  display: flex;
  flex-direction: row;
}

div.error {
  height: 16px;
  padding: 4px;
  text-align: left;
  font-size: 12px;
}

header {
  background-color: #002040;
  position: sticky;
  left: 0;
  right: 0;
  top: 0;
  margin: 0;
  padding: 1em;
  font-size: 1.5rem;
  color: #f2f2f2;
}

input {
  font-size: 16px;
  box-sizing: border-box;
  margin: auto 0 auto 0;
  border: #c0c0c0 2px solid;
  border-radius: 5px;
  outline: none;
  padding: 8px;
}

input.error {
  border-color: #ff0000;
}

input:focus {
  border-color: #0060f0;
}

label {
  font-size: 16px;
}

label.error {
  color: #ff7f00;
}

span.error {
  color: #ff0000;
  font-weight: bold;
}
</style>
