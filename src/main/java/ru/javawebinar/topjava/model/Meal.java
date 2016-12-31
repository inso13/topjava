package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
      @NamedQuery(name = Meal.BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime>=:startDate AND m.dateTime<:endDate ORDER BY m.dateTime DESC "),
       @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC "),
        @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.id=:id")
})
@Entity
@Table(name = "meals")
public class Meal extends BaseEntity {

    public static final String DELETE = "Meal.delete";
   public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String BETWEEN = "Meal.getBetween";
   public static final String GET = "Meal.get";

    @Column(name = "date_time")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description")
    private String description;

    @Column(name = "calories", nullable = false)
    @Digits(fraction = 0, integer = 4)
    private int calories;


    @ManyToOne(fetch = FetchType.LAZY)
    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "id"))
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
