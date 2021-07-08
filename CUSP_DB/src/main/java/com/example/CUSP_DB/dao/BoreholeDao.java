package com.example.CUSP_DB.dao;

import com.example.CUSP_DB.model.Borehole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BoreholeDao extends PagingAndSortingRepository<Borehole, Long> {
}
