package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Inso on 10.02.2017.
 */
public class MealsTo implements Serializable { //TODO: add correct constraints
    private static final long serialVersionUID = 1L;
    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @NotBlank(message = " must not be empty")
    private String description;

   @Range(min = 10, max = 5000, message = " must between 10 and 5000 calories")
    private int calories /*= MealsUtil.DEFAULT_CALORIES*/;

    public MealsTo() {}

    public MealsTo(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "MealsTo{" +
                "id=" + id +
                ", dateTime=" + dateTime + '\'' +
                ", description='" + description + '\'' +
                ", calories=" + calories + '\'' +
                '}';
    }
}
