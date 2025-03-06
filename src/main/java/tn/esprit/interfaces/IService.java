
package tn.esprit.interfaces;

<<<<<<< HEAD
import tn.esprit.models.evenement;

import java.util.List;

public interface IService<T> {

    void add(evenement e);

    List<T> getAll();

//    void update(T t,int id);
//
//    void delete(int id);

}
=======
import java.util.List;

public interface IService<T> {
 HEAD
    void add(T t);
    List<T> getAll();
    void update(T t);
    void delete(T t);
}

>>>>>>> d3a237b0cedf5db29bbc82b99157c53cf558ac15
