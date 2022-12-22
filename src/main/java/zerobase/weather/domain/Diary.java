package zerobase.weather.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Diary {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String weather;
    private String icon;
    private double temperature;
    private String text;
    private LocalDate date;
}
