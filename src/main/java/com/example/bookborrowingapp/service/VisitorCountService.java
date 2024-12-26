package com.example.bookborrowingapp.service;

import com.example.bookborrowingapp.model.VisitorCount;
import com.example.bookborrowingapp.repository.IVisitorCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VisitorCountService implements IVisitorCountService {
    @Autowired
    private IVisitorCountRepository visitorCountRepository;

    @Override
    public Iterable<VisitorCount> findAll() {
        return visitorCountRepository.findAll();
    }

    @Override
    public Optional<VisitorCount> findById(long id) {
        return visitorCountRepository.findById(id);
    }

    @Override
    public void save(VisitorCount object) {
        visitorCountRepository.save(object);
    }

    @Override
    public void deleteById(long id) {
        visitorCountRepository.deleteById(id);
    }
}
