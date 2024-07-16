<script>
</script>

<template>
  <div class="column">
    <h3>Лабораторная работа #2</h3>

    <h2>Вариант: 123</h2>

    <p>
    Разработать спецификацию в формате OpenAPI для набора веб-сервисов, реализующего следующую функциональность:
    </p>

    <p>
      <b>Первый веб-сервис</b> должен осуществлять управление коллекцией объектов. В коллекции необходимо хранить объекты класса City, описание которого приведено ниже:
    </p>

    <pre><code>public class MusicBand {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long numberOfParticipants; //Значение поля должно быть больше 0
    private long singlesCount; //Значение поля должно быть больше 0
    private MusicGenre genre; //Поле не может быть null
    private Album bestAlbum; //Поле не может быть null
}
public class Coordinates {
    private int x;
    private Integer y; //Поле не может быть null
}
public class Album {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long tracks; //Поле не может быть null, Значение поля должно быть больше 0
    private Long length; //Поле может быть null, Значение поля должно быть больше 0
}
public enum MusicGenre {
    RAP,
    PSYCHEDELIC_CLOUD_RAP,
    POST_ROCK,
    POST_PUNK,
    BRIT_POP;
}
      </code>
    </pre>
  </div>
</template>

<style scoped>
body {
  margin: 0;
}

body, div {
  text-align: center;
  background-color: #ffffff;
  line-height: 1.5;
  color: black;
  font-family: "Segoe UI",serif;
  font-size: 1rem;
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

table, td, th {
  margin: 10px auto 10px auto;
  padding: 4px;
  border-style: solid;
  border-width: thin;
  border-collapse: collapse;
  text-align: center;
  vertical-align: middle;
  background-color: #ffffff;
}

td {
  width: 40px;
}

tbody {
  font-weight: normal;
}

thead {
  font-weight: bold;
}

td:hover {
  background: #afdfff;
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
