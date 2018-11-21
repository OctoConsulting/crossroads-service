package gov.fbi.elabs.crossroads.repository;

import org.springframework.stereotype.Component;

@Component
public interface GenericRepository<T> {
	 
	 T create(T t);
     T update(T t, boolean flush);
     void delete(T t);
}
