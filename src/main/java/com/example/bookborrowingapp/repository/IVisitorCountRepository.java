package com.example.bookborrowingapp.repository;

import com.example.bookborrowingapp.model.VisitorCount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVisitorCountRepository extends CrudRepository<VisitorCount, Long> {
}
