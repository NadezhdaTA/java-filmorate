package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "mpaId")
@NoArgsConstructor
@AllArgsConstructor
public class MpaRating {
  @JsonProperty("id")
  private Integer mpaId;
  @JsonProperty("name")
  private String mpaRate;
}
