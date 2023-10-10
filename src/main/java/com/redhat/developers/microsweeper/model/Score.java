package com.redhat.developers.microsweeper.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Score extends PanacheEntity {

    public long scoreId;
    public String name;
    public String level;
    public int time;
    public boolean success;

    @Override
    public String toString() {
        return name + "/" + level + "/" + time + "/" + success + "/" + scoreId;
    }
}
