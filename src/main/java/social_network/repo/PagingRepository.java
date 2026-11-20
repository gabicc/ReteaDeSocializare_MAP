package social_network.repo;

import social_network.domain.Entity;
import social_network.repo.util.Page;
import social_network.repo.util.Pageable;
import social_network.repo.util.Sort;



public interface PagingRepository<ID , E extends Entity<ID>> extends Repository<ID, E> {

    Page<E> findAllOnPage(Pageable pageable);

    Page<E> findAllOnPage(Pageable pageable, Sort sort);
}