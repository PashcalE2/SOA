<script setup>

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

</style>