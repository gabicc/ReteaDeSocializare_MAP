package social_network.repo;


import social_network.domain.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * CRUD operations repository interface
 *
 * @param <ID> - type E must have an attribute of type ID
 * @param <E>  -  type of entities saved in repository
 */

public interface Repository<ID, E extends Entity<ID>> {

    /**
     *
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException if id is null.
     */
    E findOne(ID id);

    /**
     *
     * @return all entities
     */
    Iterable<E> findAll();


    default List<E> findAll(Predicate<E> filter) {
        if(filter == null) {
            throw new IllegalArgumentException("Filter-ul nu e bun");
        }
        List<E> list = new ArrayList<>();
//        for(E e: findAll()) {
//            if(filter.test(e)) {
//                list.add(e);
//            }
//        }

//        Stream<E> filtered = findAll().stream().filter(e -> {
//            return filter.test(e);
//        });

        list = StreamSupport.stream(findAll().spliterator(), false)
                .filter(filter)
                .collect(Collectors.toCollection(ArrayList::new));
        return list;

    }

    /**
     *
     * @param entity entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws IllegalArgumentException if the given entity is null.     *
     */
    E save(E entity);

    /**
     * removes the entity with the specified id
     *
     * @param id id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException if the given id is null.
     */
    E delete(ID id);

    /**
     *
     * @param entity entity must not be null
     * @return null - if the entity is updated,
     * otherwise  returns the entity  - (e.g id does not exist).
     * @throws IllegalArgumentException if the given entity is null.
     */
    E update(E entity);
}

