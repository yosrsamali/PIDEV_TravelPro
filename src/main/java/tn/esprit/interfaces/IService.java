
package tn.esprit.interfaces;

import tn.esprit.models.evenement;

import java.util.List;

public interface IService<T> {

    void add(evenement e);

    List<T> getAll();

//    void update(T t,int id);
//
//    void delete(int id);

}
