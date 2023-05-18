package com.eightjo.carrotclone.map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class NearAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Address parent;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Address child;

    public NearAddress(Address parent, Address child) {
        this.parent = parent;
        this.child = child;
        parent.getNearChildAddress().add(this);
        child.getNearParentAddress().add(this);
    }

    public void update(){
        if (!parent.getNearChildAddress().contains(this)){
            parent.getNearChildAddress().add(this);
        }
        if (!child.getNearParentAddress().contains(this)) {
            child.getNearParentAddress().add(this);
        }
    }

    public void remove(){
        parent.getNearChildAddress().remove(this);
        child.getNearParentAddress().remove(this);
    }
}
